package com.org.NoteMakingApp.Dto;

import com.org.NoteMakingApp.model.Notes;

import lombok.Data;

@Data
public class FevoriteNoteDto {
	private int id;
	private Notes note;
	private Integer userId;
}
