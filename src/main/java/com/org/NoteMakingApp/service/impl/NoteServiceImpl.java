package com.org.NoteMakingApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.NotesDto;
import com.org.NoteMakingApp.Repo.NoteRepo;
import com.org.NoteMakingApp.model.Notes;
import com.org.NoteMakingApp.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private NoteRepo noteRepo;

	@Override
	public boolean saveNotes(NotesDto notesDto) {

		Notes note = mapper.map(notesDto, Notes.class);
		Notes save = noteRepo.save(note);
		if (ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}

	@Override
	public List<NotesDto> getAllNotes() {
		List<Notes> allNotes = noteRepo.findAll();
		return allNotes.stream().map(ele -> mapper.map(ele, NotesDto.class)).collect(Collectors.toList());
	}

}
