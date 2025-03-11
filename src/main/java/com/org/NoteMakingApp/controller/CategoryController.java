package com.org.NoteMakingApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/saveCategory")
	public ResponseEntity<?> saveCategory(@RequestBody Category category) {
		boolean saveCategory = categoryService.saveCategory(category);
		if (saveCategory) {
			return new ResponseEntity<>("saved Successfully", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllCategory")
	
	public ResponseEntity<?> getAllCategory(@RequestBody Category category) {
		List<Category> findAllCategories = categoryService.findAllCategories();
		if (!CollectionUtils.isEmpty(findAllCategories)) {
			return new ResponseEntity<>(findAllCategories, HttpStatus.OK);
		} else {
			return  ResponseEntity.noContent().build(); 
		}
	}
}
