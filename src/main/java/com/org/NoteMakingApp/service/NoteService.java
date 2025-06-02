package com.org.NoteMakingApp.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.org.NoteMakingApp.Dto.NoteResponse;
import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.model.Filedetails;

public interface NoteService {

	public boolean saveNotes(String notesDto, MultipartFile file)
			throws ResourceNotFoundException, AlreadyExists, JsonMappingException, JsonProcessingException, IOException;

	public List<NotesDto> getAllNotes();

	public NoteResponse getUserAllNotes(int id, int pageNum, int pageSize);

	public NotesDto findNoteById(Integer id) throws ResourceNotFoundException;

	public void deleteNoteById(Integer id) throws ResourceNotFoundException;

	public byte[] downloadFile(Filedetails fileDetails) throws IOException;

	public Filedetails getFileDetails(int id) throws ResourceNotFoundException;

	public NoteResponse getUserAllNotesByCategory(int categoryId, int pageNum, int pageSize);

	public void restoreNoteById(Integer id) throws ResourceNotFoundException;

	public List<NotesDto> recycleNote(int userId);

	public void hardDeleteNote(int userId) throws ResourceNotFoundException;

	public void deleteAllNoteFromRecycle(int userId);

}
