package com.org.NoteMakingApp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.NoteMakingApp.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
