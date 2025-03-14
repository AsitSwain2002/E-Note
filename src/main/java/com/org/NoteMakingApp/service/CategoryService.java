package com.org.NoteMakingApp.service;

import java.util.List;

import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.model.CategoryResponse;

public interface CategoryService {

	public boolean saveCategory(CategoryDto categoryDto) throws AlreadyExists, ResourceNotFoundException;

	public List<CategoryDto> findAllCategories();
	
//	public boolean updateCategory(CategoryDto categoryDto,int id);

	public CategoryDto getCategoryById(int id) throws ResourceNotFoundException;

	public boolean deleteCategoryById(int id) throws ResourceNotFoundException;

	public List<CategoryResponse> allActiveCategory();
}
