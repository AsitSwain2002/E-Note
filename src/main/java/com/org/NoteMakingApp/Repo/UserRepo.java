package com.org.NoteMakingApp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.NoteMakingApp.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

	boolean existsByEmail(String email);

}
