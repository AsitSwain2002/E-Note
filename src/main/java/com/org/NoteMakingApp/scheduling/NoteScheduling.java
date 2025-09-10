package com.org.NoteMakingApp.scheduling;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.org.NoteMakingApp.Repo.NoteRepo;
import com.org.NoteMakingApp.model.Notes;

@Component
public class NoteScheduling {

	@Autowired
	private NoteRepo noteRepo;

	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNoteScheduler() {
		// get the day from the delete date
		LocalDateTime autoDeleteDays = LocalDateTime.now().minusDays(7);
		List<Notes> deleteNotes = noteRepo.findAllByIsDeletedAndDeletedOnBefore(true, autoDeleteDays);
		noteRepo.deleteAll(deleteNotes);
	}
}
