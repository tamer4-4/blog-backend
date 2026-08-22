package com.blog.blogWeb.Controllers;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.blog.blogWeb.exception.FileSotreException;
import com.blog.blogWeb.exception.PoatNotFoundException;
import com.blog.blogWeb.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(AccessDeniedException.class)
	public ProblemDetail handleAccessDeniedException(AccessDeniedException ex ) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, 
				"Access Failed");
		
		Map<String, Object> errMap = new HashMap<>();
		errMap.put("erorr", ex.getMessage());
		errMap.put("timestamp", LocalDateTime.now());
		problemDetail.setProperty("errors", errMap);
		
		return problemDetail;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handelValideException(MethodArgumentNotValidException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, 
				"Validation Failed");
		
		Map<String, Object> errMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(err ->{
			errMap.put(err.getField() , err.getDefaultMessage());
		});
		problemDetail.setProperty("errors", errMap);
		
		return problemDetail;
		
	}
	
	
	@ExceptionHandler(PoatNotFoundException.class)
	public ProblemDetail handelPostException(PoatNotFoundException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, 
				"Post Failed");
		
		Map<String, Object> errMap = new HashMap<>();
		errMap.put("erorr", ex.getMessage());
		errMap.put("timestamp", LocalDateTime.now());
		problemDetail.setProperty("errors", errMap);
		
		return problemDetail;
		
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ProblemDetail handelUserException(UserNotFoundException ex ) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, 
				"User Failed");
		
		Map<String, Object> errMap = new HashMap<>();
		errMap.put("erorr", ex.getMessage());
		errMap.put("timestamp", LocalDateTime.now());
		problemDetail.setProperty("errors", errMap);
		
		return problemDetail;
		
	}
	@ExceptionHandler(FileSotreException.class)
	public ProblemDetail handelValideException(FileSotreException ex ) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, 
				"FileStore Failed");
		
		Map<String, Object> errMap = new HashMap<>();
		errMap.put("erorr", ex.getMessage());
		errMap.put("timestamp", LocalDateTime.now());
		problemDetail.setProperty("errors", errMap);
		
		return problemDetail;
		
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ProblemDetail handleUsernameFailedLoginException(UsernameNotFoundException ex, WebRequest request) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, 
				"User Failed Login");
		
		Map<String, Object> errMap = new HashMap<>();
		errMap.put("erorr", ex.getMessage());
		errMap.put("timestamp", LocalDateTime.now());
		problemDetail.setProperty("errors", errMap);
		
		return problemDetail;
	}
}
