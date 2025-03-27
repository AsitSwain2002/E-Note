package com.org.NoteMakingApp.service;

import java.util.List;

import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;

public interface NoteService {

	public boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException, AlreadyExists;

	public List<NotesDto> getAllNotes();

	public NotesDto findNoteById(Integer id) throws ResourceNotFoundException;

	public void deleteNoteById(Integer id) throws ResourceNotFoundException;
}
