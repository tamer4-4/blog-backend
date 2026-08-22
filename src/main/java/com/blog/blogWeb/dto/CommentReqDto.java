package com.blog.blogWeb.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentReqDto(		
		@NotBlank(message = "content cannot be blank")   
		@Size(min = 2, max = 500, message = "content must be between 2 and 500 characters")
		String content,       
        UserResponseDto user,
        PostResponseDto post

		) {

}
