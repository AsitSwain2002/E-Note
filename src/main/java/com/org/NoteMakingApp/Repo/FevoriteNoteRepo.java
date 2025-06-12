package com.org.NoteMakingApp.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.NoteMakingApp.model.FevoriteNote;

@Repository
public interface FevoriteNoteRepo extends JpaRepository<FevoriteNote, Integer> {

	List<FevoriteNote> findAllByUserId(int userId);

}
