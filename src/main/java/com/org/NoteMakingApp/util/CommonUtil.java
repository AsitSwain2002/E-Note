package com.org.NoteMakingApp.util;

import org.apache.commons.io.FilenameUtils;

public class CommonUtil {

	public static String getContentType(String originalFileName) {

		String extension = FilenameUtils.getExtension(originalFileName);
		switch (extension) {
		case "pdf":
			return "application/pdf";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		case "txt":
			return "text/plain";
		case "png":
			return "image/png";
		case "jpeg":
			return "image/jpeg";
		default:
			return "application/octet-stream";
		}
	}

}
