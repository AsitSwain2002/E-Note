package com.org.NoteMakingApp.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.NoteMakingApp.model.Todo;

@Repository
public interface TodoRepo extends JpaRepository<Todo, Integer> {

	List<Todo> findAllByCreatedBy(int userId);

}
