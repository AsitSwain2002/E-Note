package com.org.NoteMakingApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.GenericExceptionHandler;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.service.CategoryService;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/save-category")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto)
			throws AlreadyExists, ResourceNotFoundException {
		boolean saveCategory = categoryService.saveCategory(categoryDto);
		if (saveCategory) {
			return GenericResponceBuilder.withOutData("saved Successfully", HttpStatus.CREATED);
		} else {
			return GenericResponceBuilder.withOutData("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping("/getAllCategory")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> findAllCategories = categoryService.findAllCategories();
		if (!CollectionUtils.isEmpty(findAllCategories)) {
			return GenericResponceBuilder.withData("Fetched", findAllCategories, HttpStatus.OK);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/getAllCategory/active")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllActiveCategory() {

		List<CategoryDto> allCategory = categoryService.allActiveCategory();
		return GenericResponceBuilder.withData("Fetched", allCategory, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> CategoryById(@PathVariable int id) throws ResourceNotFoundException {
		CategoryDto category = categoryService.getCategoryById(id);
		if (ObjectUtils.isEmpty(category)) {
			return GenericResponceBuilder.withOutData("Something Went Wrong In Server " + id,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return GenericResponceBuilder.withData("Success", category, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteCategoryById(@PathVariable int id) throws ResourceNotFoundException {
		boolean isDeleted = categoryService.deleteCategoryById(id);
		if (isDeleted) {
			return GenericResponceBuilder.withOutData("Deleted", HttpStatus.NO_CONTENT);
		} else {
			return GenericResponceBuilder.withOutData("Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 

}
