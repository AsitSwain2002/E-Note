package com.org.NoteMakingApp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.NoteMakingApp.model.Filedetails;

@Repository
public interface FileRepo extends JpaRepository<Filedetails, Integer> {

}
