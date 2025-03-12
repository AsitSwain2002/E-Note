package com.org.NoteMakingApp.service;

import java.util.List;

import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.model.Category;

public interface CategoryService {

	public boolean saveCategory(CategoryDto categoryDto);

	public List<CategoryDto> findAllCategories();
	
	public boolean updateCategory(CategoryDto categoryDto,int id);
}
