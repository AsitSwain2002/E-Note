package com.org.NoteMakingApp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.org.NoteMakingApp.Dto.FevoriteNoteDto;
import com.org.NoteMakingApp.Dto.NoteResponse;
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
	@PreAuthorize("hasRole('USER')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllNote() {
		List<NotesDto> allNotes = noteService.getAllNotes();
		if (CollectionUtils.isEmpty(allNotes)) {
			return GenericResponceBuilder.withOutData("No data Found", HttpStatus.OK);
		} else {
			return GenericResponceBuilder.withData("Fetched", allNotes, HttpStatus.OK);
		}
	}

	@GetMapping("user/all-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserAllNote(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
		NoteResponse userAllNotes = noteService.getUserAllNotes(pageNum, pageSize);
		if (userAllNotes.getNotes().isEmpty()) {
			return GenericResponceBuilder.withOutData("No data Found", HttpStatus.OK);
		} else {
			return GenericResponceBuilder.withData("Fetched", userAllNotes, HttpStatus.OK);
		}
	}

	// Download File
	@GetMapping("/downloadFile/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> downloadFile(@PathVariable int id) throws ResourceNotFoundException, IOException {
		Filedetails fileDetails = noteService.getFileDetails(id);
		byte[] downloadFile = noteService.downloadFile(fileDetails);
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(downloadFile);
	}

	// Fetch All Note By Category
	@GetMapping("user/category/all-notes/{categoryId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserAllNoteByCategory(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
			@RequestParam(name = "pageSize", defaultValue = "5") int pageSize, @PathVariable int categoryId) {
		NoteResponse userAllNotes = noteService.getUserAllNotesByCategory(categoryId, pageNum, pageSize);
		if (userAllNotes.getNotes().isEmpty()) {
			return GenericResponceBuilder.withOutData("No data Found", HttpStatus.OK);
		} else {
			return GenericResponceBuilder.withData("Fetched", userAllNotes, HttpStatus.OK);
		}
	}

	// Fetch Note By Id
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> fetchNoteById(@PathVariable Integer id) throws ResourceNotFoundException {
		NotesDto findNoteById = noteService.findNoteById(id);
		if (ObjectUtils.isEmpty(findNoteById)) {
			return GenericResponceBuilder.withOutData("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return GenericResponceBuilder.withData("Fetched", findNoteById, HttpStatus.OK);
		}
	}

	// Delete Note
	@GetMapping("/deleteNote/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteNote(@PathVariable Integer id) throws ResourceNotFoundException {
		noteService.deleteNoteById(id);
		return GenericResponceBuilder.withOutData("Deleted Successfully", HttpStatus.NO_CONTENT);
	}

	// Restore Note
	@GetMapping("/restoreNote/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> restoreNote(@PathVariable Integer id) throws ResourceNotFoundException {
		noteService.restoreNoteById(id);
		return GenericResponceBuilder.withOutData("Note Restore Successfully", HttpStatus.OK);
	}

	// Recycle Bin
	@GetMapping("/recycle-note")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> restoreNote() throws ResourceNotFoundException {

		List<NotesDto> recycleNote = noteService.recycleNote();
		if (ObjectUtils.isEmpty(recycleNote)) {
			return GenericResponceBuilder.withOutData("No Note found", HttpStatus.OK);
		}
		return GenericResponceBuilder.withData("Note Restore Successfully", recycleNote, HttpStatus.OK);
	}

	// Favorite Note Module
	@PostMapping("/fevorite/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> fevoriteNote(@PathVariable int noteId) throws ResourceNotFoundException {
		noteService.addToFevorite(noteId);
		return GenericResponceBuilder.withOutData("Note Added  Fevorite  Successfully", HttpStatus.OK);
	}

	// Un-Favorite Note Module
	@DeleteMapping("/un-fevorite/{favNoteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> unFevoriteNote(@PathVariable int favNoteId) throws ResourceNotFoundException {
		noteService.removeFevorite(favNoteId);
		return GenericResponceBuilder.withOutData("Note Removed  From Fevorite  Successfully", HttpStatus.OK);
	}

	// All Favorite Note
	@GetMapping("/fevoriteNotes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> allFevoriteNote() throws ResourceNotFoundException {
		List<FevoriteNoteDto> allFavNote = noteService.allFavNote();
		if (CollectionUtils.isEmpty(allFavNote)) {
			return GenericResponceBuilder.withOutData("No Fevorite Note", HttpStatus.OK);
		}
		return GenericResponceBuilder.withData("Note Fetched  Successfully", allFavNote, HttpStatus.OK);
	}

	// Fully Delete
	@DeleteMapping("/force-delete-note/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> forceDeleteNote(@PathVariable int noteId) throws ResourceNotFoundException {
		noteService.hardDeleteNote(noteId);
		return GenericResponceBuilder.withOutData("Note Deleted Successfully", HttpStatus.OK);
	}

	// Delete all file In recycle Bin
	@DeleteMapping("/delete-allnote-recyclebin")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteAllNoteFromRecycle() throws ResourceNotFoundException {
		noteService.deleteAllNoteFromRecycle();
		return GenericResponceBuilder.withOutData("All Note Deleted Successfully", HttpStatus.OK);
	}

	// Copy Note
	@GetMapping("/copy-note/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> copyNote(@PathVariable int id) throws ResourceNotFoundException {
		boolean copyNote = noteService.copyNote(id);
		if (copyNote) {
			return GenericResponceBuilder.withOutData("Note Copied", HttpStatus.OK);
		}
		return GenericResponceBuilder.withOutData("Something wnt wrong ! Please try again",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchNote(@RequestParam String key, @RequestParam(defaultValue = "0") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize) throws ResourceNotFoundException {
		 NoteResponse searchNote = noteService.searchNote(key,pageNum,pageSize);
		if (!ObjectUtils.isEmpty(searchNote)) {
			return GenericResponceBuilder.withData("Note Fetched", searchNote, HttpStatus.OK);
		}
		return GenericResponceBuilder.withOutData("No Note Found", HttpStatus.NO_CONTENT);
	}
}
