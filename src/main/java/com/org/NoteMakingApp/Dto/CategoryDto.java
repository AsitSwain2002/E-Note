package com.org.NoteMakingApp.Dto;

import java.util.Date;

import lombok.Data;

@Data
public class CategoryDto {

	private String name;
	private String description;
	private boolean active;
	private int created_by;
	private Date created_on;
	private int update_by;
	private Date update_on;
}
