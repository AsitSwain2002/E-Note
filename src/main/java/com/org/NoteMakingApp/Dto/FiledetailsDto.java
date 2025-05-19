package com.org.NoteMakingApp.Dto;

import lombok.Data;

@Data
public class FiledetailsDto {
	private Integer id;
	private String uploadFileName;
	private String originalFileName;
	private String displayFileName;
	private String path;
	private Long fileSize;
}
