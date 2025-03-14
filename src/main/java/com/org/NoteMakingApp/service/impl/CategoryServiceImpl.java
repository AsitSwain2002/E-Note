package com.org.NoteMakingApp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.CategoryDto;
import com.org.NoteMakingApp.Repo.CategoryRepo;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.model.CategoryResponse;
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
		if (ObjectUtils.isEmpty(category.getId())) {
			category.setDeleted(false);
			category.setCreated_on(new Date());
			category.setCreated_by(1);
		} else {
			updateCatgory(category);
		}
		Category categoryObj = categoryRepo.save(category);
		if (!ObjectUtils.isEmpty(categoryObj)) {
			return true;
		}
		return false;
	}

	private void updateCatgory(Category category) {

		Optional<Category> findById = categoryRepo.findById(category.getId());
		if (findById.isPresent()) {
			Category existCategory = findById.get();
			category.setActive(existCategory.isActive());
			category.setUpdate_by(1);
			category.setUpdate_on(new Date());
//			category.setDescription(existCategory.getDescription());
//			category.setName(existCategory.getName());
			category.setCreated_on(existCategory.getCreated_on());
			category.setDeleted(existCategory.isDeleted());
		}
	}

	@Override
	public List<CategoryDto> findAllCategories() {
		List<Category> findAll = categoryRepo.findByActiveTrueAndIsDeletedFalse();
		return findAll.stream().map((ele) -> mapper.map(ele, CategoryDto.class)).collect(Collectors.toList());
	}

//	@Override
//	public boolean updateCategory(CategoryDto categoryDto, int id) {
//
//		return false;
//	}

	@Override
	public List<CategoryResponse> allActiveCategory() {

		List<Category> findByActiveTrue = categoryRepo.findByActiveTrue();
		return findByActiveTrue.stream().map(e -> mapper.map(e, CategoryResponse.class)).collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategoryById(int id) {
		Category category = categoryRepo.findByIdAndIsDeletedFalse(id);
		if (!ObjectUtils.isEmpty(category)) {
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public boolean deleteCategoryById(int id) {
		Optional<Category> category = categoryRepo.findById(id);
		if (category.isPresent()) {
			Category category2 = category.get();
			category2.setDeleted(true);
			categoryRepo.save(category2);
			return true;
		}
		return false;
	}

}
