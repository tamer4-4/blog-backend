package com.blog.blogWeb.exception;

public class PoatNotFoundException extends Exception {

	public PoatNotFoundException(Long id) {
		super("not Found Post wthi " + id);
	}

}
