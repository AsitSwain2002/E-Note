package com.org.NoteMakingApp.util;

import org.apache.commons.io.FilenameUtils;

import jakarta.servlet.http.HttpServletRequest;

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

	public static String getUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String requestURI = request.getRequestURI();

		String baseUrl = url.replace(requestURI, "");
		return baseUrl;
	}

}
