package com.org.NoteMakingApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.Repo.CategoryRepo;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public boolean saveCategory(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		category.setDeleted(false);
		Category categoryObj = categoryRepo.save(category);
		if (!ObjectUtils.isEmpty(categoryObj)) {
			return true;
		}
		return false;
	}

	@Override
	public List<CategoryDto> findAllCategories() {
		List<Category> findAll = categoryRepo.findAll();
		return findAll.stream().map((ele) -> mapper.map(ele, CategoryDto.class)).collect(Collectors.toList());
	}

	@Override
	public boolean updateCategory(CategoryDto categoryDto, int id) {
		
		return false;
	}

}
