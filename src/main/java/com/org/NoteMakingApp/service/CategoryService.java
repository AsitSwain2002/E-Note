package com.org.NoteMakingApp.service;

import java.util.List;

import com.org.NoteMakingApp.model.Category;

public interface CategoryService {

	public boolean saveCategory(Category category);

	public List<Category> findAllCategories();
}
