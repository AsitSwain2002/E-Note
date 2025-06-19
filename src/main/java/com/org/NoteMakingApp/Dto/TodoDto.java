package com.org.NoteMakingApp.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
	private Integer id;
	private String title;
	private String description;
	private StatusDto status;
	private int createdBy;
	private Date createdOn;
	private Date updateOn;

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StatusDto {

		private Integer id;
		private String name;
	}
}
