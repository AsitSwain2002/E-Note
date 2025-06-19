package com.org.NoteMakingApp.service;

import java.util.List;

import com.org.NoteMakingApp.Dto.TodoDto;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;

public interface TodoService {

	public boolean createTodo(TodoDto todoDto) throws ResourceNotFoundException;

	public List<TodoDto> allTodoTask();

	public TodoDto todoById(Integer id) throws ResourceNotFoundException;

	public void deleteTodo(Integer id) throws ResourceNotFoundException;

}
