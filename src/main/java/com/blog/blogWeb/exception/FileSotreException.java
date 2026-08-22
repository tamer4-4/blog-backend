package com.blog.blogWeb.exception;

public class FileSotreException extends Exception {

	public FileSotreException(String msg) {
		super("Failed to store file" + msg);
	}
   
}
