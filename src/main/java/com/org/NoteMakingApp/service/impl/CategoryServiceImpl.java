package com.org.NoteMakingApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Repo.CategoryRepo;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public boolean saveCategory(Category category) {
		category.setDeleted(false);
		Category categoryObj = categoryRepo.save(category);
		if (!ObjectUtils.isEmpty(categoryObj)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Category> findAllCategories() {
		return categoryRepo.findAll();
	}

}
