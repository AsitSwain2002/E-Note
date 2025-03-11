package com.org.NoteMakingApp.model;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
	private boolean active;
	private boolean isDeleted;
	private int created_by;
	private Date created_on;
	private int update_by;
	private Date update_on;
}
