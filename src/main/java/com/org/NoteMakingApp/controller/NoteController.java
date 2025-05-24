package com.org.NoteMakingApp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.model.Filedetails;
import com.org.NoteMakingApp.service.NoteService;
import com.org.NoteMakingApp.util.CommonUtil;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

@RestController
@RequestMapping("/api/v1/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping("/save-note")
	public ResponseEntity<?> saveNote(@RequestParam String notesDto, @RequestParam(required = false) MultipartFile file)
			throws ResourceNotFoundException, AlreadyExists, IOException {

		boolean saveNotes = noteService.saveNotes(notesDto, file);
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

	@GetMapping("/downloadFile/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable int id) throws ResourceNotFoundException, IOException {
		Filedetails fileDetails = noteService.getFileDetails(id);
		byte[] downloadFile = noteService.downloadFile(fileDetails);
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(downloadFile);
	}
}
