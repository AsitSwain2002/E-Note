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
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.Repo.CategoryRepo;
import com.org.NoteMakingApp.Validation.CategoryValidation;
import com.org.NoteMakingApp.model.Category;
import com.org.NoteMakingApp.model.CategoryResponse;
import com.org.NoteMakingApp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CategoryValidation categoryValidation;

	@Override
	public boolean saveCategory(CategoryDto categoryDto) throws AlreadyExists, ResourceNotFoundException {
		categoryValidation.validateCategory(categoryDto);
		Category category = mapper.map(categoryDto, Category.class);
//		System.out.println();
//		System.out.println();
//		System.out.println(category.getId());
//		System.out.println();
		if (ObjectUtils.isEmpty(category.getId()) || category.getId() == 0) {
		System.out.println();
		System.out.println(category.getId());
		System.out.println();
			category.setDeleted(false);
			category.setCreated_on(new Date());
			category.setCreated_by(1);
			Category findByName = categoryRepo.findByName(category.getName());
			if (!ObjectUtils.isEmpty(findByName)) {
				throw new AlreadyExists("Category Already Present");
			}
		} else {
			updateCatgory(category);
		}
		Category categoryObj = categoryRepo.save(category);
		if (!ObjectUtils.isEmpty(categoryObj)) {
			return true;
		} else {
			return false;
		}
	}

	private void updateCatgory(Category category) throws ResourceNotFoundException {

		Category existCategory = categoryRepo.findById(category.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Category With id '" + category.getId() + "' Not Found"));
		if (!ObjectUtils.isEmpty(category)) {
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


	@Override
	public List<CategoryResponse> allActiveCategory() {

		List<Category> findByActiveTrue = categoryRepo.findByActiveTrue();
		return findByActiveTrue.stream().map(e -> mapper.map(e, CategoryResponse.class)).collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategoryById(int id) throws ResourceNotFoundException {
		Category category = categoryRepo.findByIdAndIsDeletedFalse(id);
		if (ObjectUtils.isEmpty(category)) {
			throw new ResourceNotFoundException("Category With id '" + id + "' Not Found");
		}
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public boolean deleteCategoryById(int id) throws ResourceNotFoundException {
		Optional<Category> category = categoryRepo.findById(id);
		if (category.isPresent()) {
			Category category2 = category.get();
			category2.setDeleted(true);
			categoryRepo.save(category2);
			return true;
		}
		throw new ResourceNotFoundException("Category With id '" + id + "' Not Found");
	}

}
