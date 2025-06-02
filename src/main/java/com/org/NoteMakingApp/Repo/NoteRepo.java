package com.org.NoteMakingApp.Repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.NoteMakingApp.model.Notes;

@Repository
public interface NoteRepo extends JpaRepository<Notes, Integer> {

	boolean existsByTitle(String title);

	List<Notes> findByIsDeletedFalse();

	Page<Notes> findByCreatedBy(int id, Pageable page);

	Page<Notes> findByCategoryId(int categoryId, Pageable page);

	List<Notes> findByCreatedByAndIsDeletedTrue(int userId);

	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime autoDeleteDays);

}
 