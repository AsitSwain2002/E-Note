package com.org.NoteMakingApp.service;

import java.util.List;

import com.org.NoteMakingApp.Dto.NotesDto;

public interface NoteService {

	public boolean saveNotes(NotesDto notesDto);

	public List<NotesDto> getAllNotes();
}
