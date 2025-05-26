package com.org.NoteMakingApp.Dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteResponse {

	private List<NotesDto> notes;
	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean isLastPage;
	private boolean isFirstPage;

}
