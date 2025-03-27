package com.org.NoteMakingApp.Validation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.ExceptionHandler.NoteValidationException;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;

@Component
public class NoteValidation {

	public void noteValidation(NotesDto notesDto) throws ResourceNotFoundException {
		Map<String, Object> error = new LinkedHashMap<String, Object>();
		if (ObjectUtils.isEmpty(notesDto)) {
			throw new ResourceNotFoundException("Note/Json Can not be Null");
		} else {
			if (notesDto.getTitle().length() < 10) {
				error.put("note", "Note length must be greater than 10");
			}
			if (notesDto.getTitle().length() >= 400) {
				error.put("note", "Note length too big must be lower than 400");
			}
			if (notesDto.getDescription().length() < 10) {
				error.put("description", "description length must be greater than 10");
			}
			if (notesDto.getDescription().length() >= 400) {
				error.put("description", "description length too big must be lower than 400");
			}

		}

		if (!ObjectUtils.isEmpty(error)) {
			throw new NoteValidationException(error);
		}
	}
}
