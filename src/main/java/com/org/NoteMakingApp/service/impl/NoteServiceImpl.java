package com.org.NoteMakingApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.Repo.CategoryRepo;
import com.org.NoteMakingApp.Repo.NoteRepo;
import com.org.NoteMakingApp.model.Category;
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

	@Override
	public boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException, AlreadyExists {

		// Check Category Present or not
		categoryExists(notesDto.getCategory().getId());
//        noteExist(notesDto.getTitle());
		Notes note = mapper.map(notesDto, Notes.class);
		if (notesDto.getId() != null) {
			updateNote(note);
		}
		Notes save = noteRepo.save(note);
		if (ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
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
