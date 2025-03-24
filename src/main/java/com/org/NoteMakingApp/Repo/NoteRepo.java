package com.org.NoteMakingApp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.NoteMakingApp.model.Notes;

@Repository
public interface NoteRepo extends JpaRepository<Notes, Integer> {

}
