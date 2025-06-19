package com.org.NoteMakingApp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.NoteMakingApp.Dto.FevoriteNoteDto;
import com.org.NoteMakingApp.Dto.NoteResponse;
import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.Repo.CategoryRepo;
import com.org.NoteMakingApp.Repo.FevoriteNoteRepo;
import com.org.NoteMakingApp.Repo.FileRepo;
import com.org.NoteMakingApp.Repo.NoteRepo;
import com.org.NoteMakingApp.Validation.NoteValidation;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.model.FevoriteNote;
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
	private FevoriteNoteRepo fevoriteNoteRepo;
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

		// update Note
		if (notesDto.getId() != null) {
			updateNote(note, file);
		}
		// File Upload Logic
		Filedetails filedetails = saveFile(file);
		if (!ObjectUtils.isEmpty(filedetails)) {
			note.setFiledetails(filedetails);
		} else {
			if (note.getId() == null) {
				note.setFiledetails(null);
			}
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
			String extension = FilenameUtils.getExtension(originalFilename);

			// Extension check logic
			List<String> fileAllow = Arrays.asList("pdf", "jpg", "png", "PDF", "JPG", "PNG");
			if (!fileAllow.contains(extension)) {
				throw new IllegalArgumentException("File name contain pdf , jpg ,png are support");
			}
			// Random name generate
			String randomString = UUID.randomUUID().toString();

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

	// Display file Name
	private String getdisplayFilename(String originalFilename) {
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);
		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}

	// Update Note
	private void updateNote(Notes note, MultipartFile file) throws ResourceNotFoundException {
		Notes dbNote = noteRepo.findById(note.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Note With ID '" + note.getId() + "' not found"));
		if (ObjectUtils.isEmpty(file)) {
			note.setFiledetails(dbNote.getFiledetails());
		}
	}

//	private void noteExist(String title) throws AlreadyExists {
//		boolean existsByTitle = noteRepo.existsByTitle(title);
//		if(existsByTitle) {
//			throw new AlreadyExists(title+" Already Present");
//		}
//	}

	// Category Exist Check
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
				.orElseThrow(() -> new ResourceNotFoundException("Note With ID '" + id + "' not found"));
		if (note.isDeleted()) {
			throw new ResourceNotFoundException("Note with id '" + id + "' not found");
		}
		return mapper.map(note, NotesDto.class);
	}

	// Delete Note
	@Override
	public void deleteNoteById(Integer id) throws ResourceNotFoundException {
		Notes note = noteRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note With ID '" + id + "' not found"));
		note.setDeleted(true);
		note.setDeletedOn(LocalDateTime.now());
		noteRepo.save(note);
	}

	// Restore Delete Note
	@Override
	public void restoreNoteById(Integer id) throws ResourceNotFoundException {
		Notes note = noteRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note With ID '" + id + "' not found"));
		note.setDeleted(false);
		note.setDeletedOn(null);
		noteRepo.save(note);
	}

	// Recycle Notes
	@Override
	public List<NotesDto> recycleNote(int userId) {

		List<Notes> dbfetchedNote = noteRepo.findByCreatedByAndIsDeletedTrue(userId);
		return dbfetchedNote.stream().map(note -> mapper.map(note, NotesDto.class)).collect(Collectors.toList());
	}

	@Override
	public void deleteAllNoteFromRecycle(int userId) {

		List<Notes> findByCreatedByAndIsDeletedTrue = noteRepo.findByCreatedByAndIsDeletedTrue(userId);
		noteRepo.deleteAll(findByCreatedByAndIsDeletedTrue);
	}

	@Override
	public void hardDeleteNote(int noteId) throws ResourceNotFoundException {
		Notes note = noteRepo.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note With ID '" + noteId + "' not found"));

		if (note.isDeleted()) {
			noteRepo.delete(note);
		} else {
			throw new IllegalArgumentException("Sorry You Can't Delete It Directly");
		}
	}

	@Override
	public byte[] downloadFile(Filedetails fileDetails) throws IOException {
		InputStream input = new FileInputStream(fileDetails.getPath());
		return StreamUtils.copyToByteArray(input);
	}

	@Override
	public Filedetails getFileDetails(int id) throws ResourceNotFoundException {
		return fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("File  Doesn't found"));
	}

	@Override
	public NoteResponse getUserAllNotes(int id, int pageNum, int pageSize) {
		Pageable page = PageRequest.of(pageNum, pageSize);
		Page<Notes> allNotes = noteRepo.findByCreatedBy(id, page);
		List<NotesDto> collect = allNotes.stream().map(note -> mapper.map(note, NotesDto.class))
				.collect(Collectors.toList());
		NoteResponse notes = NoteResponse.builder().notes(collect).totalElement(allNotes.getTotalElements())
				.totalPages(allNotes.getTotalPages()).pageNumber(allNotes.getNumber()).pageSize(allNotes.getSize())
				.isFirstPage(allNotes.isFirst()).isLastPage(allNotes.isLast()).build();
		return notes;
	}

	@Override
	public NoteResponse getUserAllNotesByCategory(int categoryId, int pageNum, int pageSize) {
		Pageable page = PageRequest.of(pageNum, pageSize);
		Page<Notes> category = noteRepo.findByCategoryId(categoryId, page);

		List<NotesDto> allNotes = category.stream().map((note) -> mapper.map(note, NotesDto.class))
				.collect(Collectors.toList());

		NoteResponse notes = NoteResponse.builder().notes(allNotes).totalElement(category.getTotalElements())
				.totalPages(category.getTotalPages()).pageNumber(category.getNumber()).pageSize(category.getSize())
				.isFirstPage(category.isFirst()).isLastPage(category.isLast()).build();
		return notes;
	}

	@Override
	public void addToFevorite(int noteId) throws ResourceNotFoundException {
		int userId = 1;
		Notes notes = noteRepo.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note With ID " + noteId + " Not Found"));

		FevoriteNote fevoriteNote = FevoriteNote.builder().userId(userId).note(notes).build();
		fevoriteNoteRepo.save(fevoriteNote);
	}

	@Override
	public void removeFevorite(int favNoteId) throws ResourceNotFoundException {
		FevoriteNote favNotes = fevoriteNoteRepo.findById(favNoteId)
				.orElseThrow(() -> new ResourceNotFoundException("Fav Note With ID  " + favNoteId + " Not Found"));
		fevoriteNoteRepo.delete(favNotes);
	}

	@Override
	public List<FevoriteNoteDto> allFavNote() {
		int userId = 1;
		List<FevoriteNote> findAllByUserId = fevoriteNoteRepo.findAllByUserId(userId);
		return findAllByUserId.stream().map(e -> mapper.map(e, FevoriteNoteDto.class)).collect(Collectors.toList());
	}

	// Copy Note logic
	@Override
	public boolean copyNote(int id) throws ResourceNotFoundException {
		int userId = 1;
		Notes notes = noteRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Note With ID " + id + " Not Found"));
		Notes copyNote = Notes.builder().title(notes.getTitle()).category(notes.getCategory()).filedetails(null)
				.isDeleted(false).description(notes.getDescription()).build();

		// check the copy user and the main user is same or not
		if (userId == notes.getCreatedBy()) {
			Notes save = noteRepo.save(copyNote);
			if (!ObjectUtils.isEmpty(save)) {
				return true;
			}
		}
		return false;
	}

}
