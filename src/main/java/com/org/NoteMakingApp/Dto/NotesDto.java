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
	private int createdBy;
	private Date createdOn;
	private int updateBy;
	private Date updateOn;
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
