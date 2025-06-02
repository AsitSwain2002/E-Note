package com.org.NoteMakingApp.Dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.org.NoteMakingApp.model.Category;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesDto {

	private Integer id;
	private String title;
	private String description;
	private CategoryDto category;
	private int created_by;
	private Date created_on;
	private int update_by;
	private Date update_on;
	private boolean isDeleted;
	private FiledetailsDto filedetails;
	private LocalDateTime deletedOn;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class FiledetailsDto {
		private Integer id;
		private String originalFileName;
		private String displayFileName;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class CategoryDto {
		private Integer id;
		private String name;

	}
}
