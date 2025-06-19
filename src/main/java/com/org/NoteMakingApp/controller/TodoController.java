package com.org.NoteMakingApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.Dto.TodoDto;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.service.TodoService;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

@RestController
@RequestMapping("api/v1/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@PostMapping("/saveTodo")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws ResourceNotFoundException { 
		boolean createTodo = todoService.createTodo(todoDto);
		if (createTodo) {
			return GenericResponceBuilder.withOutData("Saved Successfullly", HttpStatus.OK);
		}
		return GenericResponceBuilder.withOutData("Something  went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/all-Todo-task")
	public ResponseEntity<?> allTodoByUser() {
		List<TodoDto> allTodoTask = todoService.allTodoTask();
		if (!CollectionUtils.isEmpty(allTodoTask)) {
			return GenericResponceBuilder.withData("fetched Successfullly", allTodoTask, HttpStatus.OK);
		}
		return GenericResponceBuilder.withOutData("Something  went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/all-Todo-task/{id}")
	public ResponseEntity<?> todoByUser(@PathVariable int id) throws ResourceNotFoundException {
		TodoDto todoById = todoService.todoById(id);
		if (!ObjectUtils.isEmpty(todoById)) {
			return GenericResponceBuilder.withData("fetched Successfullly", todoById, HttpStatus.OK);
		}
		return GenericResponceBuilder.withOutData("Something  went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
