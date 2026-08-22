package com.blog.blogWeb.security;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {
	
	
	private final Long id;
	
	
	public CustomUserDetails(
			 Long id 
			,String username
			, @Nullable String password
			,Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
 	}


	public Long getId() {
		return id;
	}

}
