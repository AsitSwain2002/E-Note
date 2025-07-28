package com.org.NoteMakingApp.Validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.TodoDto;
import com.org.NoteMakingApp.Dto.TodoDto.StatusDto;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.ExceptionHandler.TodoValidationException;
import com.org.NoteMakingApp.enums.TodoStatus;

@Component
public class TodoValidation {

	public void todoStatusValidation(TodoDto todoDto) throws ResourceNotFoundException {
		Map<String, Object> error = new HashMap<String, Object>();

		if (ObjectUtils.isEmpty(todoDto)) {
			error.put("Json Error", "Json/Data Can't be NULL");
		} else {
			if (todoDto.getTitle().length() <= 3) {
				error.put("Title", "Title Length should not less than 3");
			}
			if (todoDto.getTitle().length() > 130) {
				error.put("Title", "Title Length should not greater than 130");
			}
			if (todoDto.getDescription().length() < 10) {
				error.put("Description", "Description Length should not less than 10");
			}
			if (todoDto.getDescription().length() > 150) {
				error.put("Description", "Description Length should not greater than 150");
			}
			StatusDto status = todoDto.getStatus();
			Boolean statusFound = false;
			for (TodoStatus st : TodoStatus.values()) {
				if (st.getId().equals(status.getId())) {
					statusFound = true;
				}
			}
			if (!statusFound) {
				error.put("Status", "Invalid Status");
			}
		}

		if (!ObjectUtils.isEmpty(error)) {
			throw new TodoValidationException(error);
		}

	}
}
