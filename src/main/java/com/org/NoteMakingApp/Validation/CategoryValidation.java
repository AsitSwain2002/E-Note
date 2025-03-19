package com.org.NoteMakingApp.Validation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.ExceptionHandler.CategoryValidationEcxception;
import com.org.NoteMakingApp.model.Category;

@Component
public class CategoryValidation {

	public void validateCategory(CategoryDto category) {

		Map<String, Object> error = new LinkedHashMap<String, Object>();
		if (ObjectUtils.isEmpty(category)) {
			throw new IllegalArgumentException("Object/Json Can Not be Null");
		} else {

			// Name Validation
			if (ObjectUtils.isEmpty(category.getName())) {
				if (category.getName().length() < 10) {
					error.put("name", "Name Must Greater Than 10");
				}
				if (category.getName().length() > 40) {
					error.put("name", "Name Must Less Than 40");
				}
			}

			// Description Validation
			if (ObjectUtils.isEmpty(category.getDescription())) {
				error.put("description", "description Can Not Be Null");
			}

			if (category.isActive() != Boolean.TRUE.booleanValue()
					&& category.isActive() != Boolean.FALSE.booleanValue()) {
				error.put("active", "Invalid Data");
			}

		}

		if (!error.isEmpty()) {

			throw new CategoryValidationEcxception(error);
		}
	}
}
