package com.org.NoteMakingApp.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.NoteValidationException;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.Repo.CategoryRepo;
import com.org.NoteMakingApp.Repo.FileRepo;
import com.org.NoteMakingApp.Repo.NoteRepo;
import com.org.NoteMakingApp.Validation.NoteValidation;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.model.Filedetails;
import com.org.NoteMakingApp.model.Notes;
import com.org.NoteMakingApp.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private NoteRepo noteRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private NoteValidation noteValidation;
	@Autowired
	private FileRepo fileRepo;
	@Value("${file.upload.path}")
	private String uploadPath;

	@Override
	public boolean saveNotes(String notes, MultipartFile file)
			throws ResourceNotFoundException, AlreadyExists, IOException {

		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);

		// Note Validation
		noteValidation.noteValidation(notesDto);

		// Check Category Present or not
		categoryExists(notesDto.getCategory().getId());

//        noteExist(notesDto.getTitle());
		Notes note = mapper.map(notesDto, Notes.class);

		// File Upload Logic
		Filedetails filedetails = saveFile(file);
		if (!ObjectUtils.isEmpty(filedetails)) {
			note.setFiledetails(filedetails);
		} else {
			note.setFiledetails(null);
		}
		if (notesDto.getId() != null) {
			updateNote(note);
		}
		Notes save = noteRepo.save(note);
		if (ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}

	private Filedetails saveFile(MultipartFile file) throws IOException {

		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

			String originalFilename = file.getOriginalFilename();
			
			//  Extension check logic
			List<String> fileAllow = Arrays.asList("pdf", "jpg", "png");
			if (!fileAllow.contains(FilenameUtils.getExtension(originalFilename))) {
				throw new IllegalArgumentException("File name contain pdf , jpg ,png does not support");
			}
			//Random name generate
			String randomString = UUID.randomUUID().toString();
			String extension = FilenameUtils.getExtension(originalFilename);
			String uploadFileName = randomString + "." + extension;

			File saveFile = new File(uploadPath);
			if (!saveFile.exists()) { 
				saveFile.mkdir();
			}

			String storePath = uploadPath.concat(uploadFileName);

			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload != 0) {
				Filedetails filedetails = new Filedetails();
				filedetails.setOriginalFileName(originalFilename);
				filedetails.setDisplayFileName(getdisplayFilename(originalFilename));
				filedetails.setUploadFileName(uploadFileName);
				filedetails.setFileSize(file.getSize());
				filedetails.setPath(storePath);

				return fileRepo.save(filedetails);
			}

		}
		return null;
	}

	private String getdisplayFilename(String originalFilename) {
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);
		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}

	private void updateNote(Notes note) throws ResourceNotFoundException {
		int id = note.getId();
		Notes dbNote = noteRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note with id '" + id + "' not found"));
		note.setCategory(dbNote.getCategory());
	}

//	private void noteExist(String title) throws AlreadyExists {
//		boolean existsByTitle = noteRepo.existsByTitle(title);
//		if(existsByTitle) {
//			throw new AlreadyExists(title+" Already Present");
//		}
//	}

	private void categoryExists(Integer id) throws ResourceNotFoundException {

		Category orElseThrow = categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
	}

	@Override
	public List<NotesDto> getAllNotes() {
		List<Notes> allNotes = noteRepo.findByIsDeletedFalse();
		return allNotes.stream().map(ele -> mapper.map(ele, NotesDto.class)).collect(Collectors.toList());
	}

	@Override
	public NotesDto findNoteById(Integer id) throws ResourceNotFoundException {
		Notes note = noteRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note with id '" + id + "' not found"));
		if (note.isDeleted()) {
			throw new ResourceNotFoundException("Note with id '" + id + "' not found");
		}
		return mapper.map(note, NotesDto.class);
	}

	@Override
	public void deleteNoteById(Integer id) throws ResourceNotFoundException {
		Notes note = noteRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note with id '" + id + "' not found"));
		note.setDeleted(true);
		noteRepo.save(note);
	}

}
