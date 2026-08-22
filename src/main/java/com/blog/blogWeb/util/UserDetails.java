package com.blog.blogWeb.util;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Comments;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.blog.blogWeb.security.CustomUserDetails;

@Component
public class UserDetails {
     
	public CustomUserDetails getUserDeatils() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails)auth.getPrincipal();
		
		return userDetails;
	}
}
