package com.org.NoteMakingApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.TodoDto;
import com.org.NoteMakingApp.Dto.TodoDto.StatusDto;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.Repo.TodoRepo;
import com.org.NoteMakingApp.Validation.TodoValidation;
import com.org.NoteMakingApp.enums.TodoStatus;
import com.org.NoteMakingApp.model.Todo;
import com.org.NoteMakingApp.service.TodoService;
import com.org.NoteMakingApp.util.CommonUtil;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepo todoRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private TodoValidation validation;

	@Override
	public boolean createTodo(TodoDto todoDto) throws ResourceNotFoundException {

		// Validate Todo
		validation.todoStatusValidation(todoDto);
		Todo todo = mapper.map(todoDto, Todo.class);
		if (todoDto.getId() != null) {
			updateTodo(todoDto);
		}
		Todo save = todoRepo.save(todo);
		if (ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}

	private void updateTodo(TodoDto todoDto) throws ResourceNotFoundException {
		Todo todo = todoRepo.findById(todoDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Task With ID " + todoDto.getId() + " Not Found"));
		todo.setStatusId(todoDto.getStatus().getId());
	}

	@Override
	public List<TodoDto> allTodoTask() {

		Integer userId = CommonUtil.getLoggedInUser().getId();
		List<Todo> findAllByCreatedBy = todoRepo.findAllByCreatedBy(userId);
		List<TodoDto> collect = findAllByCreatedBy.stream().map(e -> mapper.map(e, TodoDto.class))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public TodoDto todoById(Integer id) throws ResourceNotFoundException {
		Todo todo = todoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task With ID " + id + " Not Found"));
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		setSatatus(todoDto, todo);
		return todoDto;
	}

	private void setSatatus(TodoDto todoDto, Todo todo) {
		for (TodoStatus st : TodoStatus.values()) {
			if (st.getId().equals(todo.getStatusId())) {
				StatusDto statusDto = StatusDto.builder().id(st.getId()).name(st.getName()).build();
				todoDto.setStatus(statusDto);
			}

		}

	}

	@Override
	public void deleteTodo(Integer id) throws ResourceNotFoundException {
		Todo orElseThrow = todoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task With ID " + id + " Not Found"));
	}

}
