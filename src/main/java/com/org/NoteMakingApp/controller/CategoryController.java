package com.org.NoteMakingApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.service.CategoryService;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

import jakarta.websocket.Session;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/saveCategory")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
		boolean saveCategory = categoryService.saveCategory(categoryDto);
		if (saveCategory) {
			return GenericResponceBuilder.builder("saved Successfully", HttpStatus.CREATED);
		} else {
			return GenericResponceBuilder.builder("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllCategory")
	public ResponseEntity<?> getAllCategory(@RequestBody CategoryDto categoryDto) {
		List<CategoryDto> findAllCategories = categoryService.findAllCategories();
		if (!CollectionUtils.isEmpty(findAllCategories)) {
			return GenericResponceBuilder.builder("Fetched", findAllCategories, HttpStatus.OK);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

//	@PutMapping("/update-category")
//	public ResponseEntity<?> updateCatagory(@RequestBody CategoryDto categoryDto, int id) {
//
//	}

}
