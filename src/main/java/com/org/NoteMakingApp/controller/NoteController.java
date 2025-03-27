package com.org.NoteMakingApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.service.NoteService;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

@RestController
@RequestMapping("/api/v1/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping("/save-note")
	public ResponseEntity<?> saveNote(@RequestBody NotesDto notesDto) throws ResourceNotFoundException, AlreadyExists {

		boolean saveNotes = noteService.saveNotes(notesDto);
		if (saveNotes) {
			return GenericResponceBuilder.withOutData("Saved Successfully", HttpStatus.CREATED);
		} else {
			return GenericResponceBuilder.withOutData("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all-notes")
	public ResponseEntity<?> getAllNote() {
		List<NotesDto> allNotes = noteService.getAllNotes();
		if (CollectionUtils.isEmpty(allNotes)) {
			return GenericResponceBuilder.withOutData("No data Found", HttpStatus.OK);
		} else {
			return GenericResponceBuilder.withData("Fetched", allNotes, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> fetchNoteById(@PathVariable Integer id) throws ResourceNotFoundException {
		NotesDto findNoteById = noteService.findNoteById(id);
		if (ObjectUtils.isEmpty(findNoteById)) {
			return GenericResponceBuilder.withOutData("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return GenericResponceBuilder.withData("Fetched", findNoteById, HttpStatus.OK);
		}
	}

	@DeleteMapping("/deleteNote/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable Integer id) throws ResourceNotFoundException {
		noteService.deleteNoteById(id);
		return GenericResponceBuilder.withOutData("Deleted Successfully", HttpStatus.NO_CONTENT);
	}
}
