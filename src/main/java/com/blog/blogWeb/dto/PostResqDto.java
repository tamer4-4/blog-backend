package com.blog.blogWeb.dto;


import com.blog.blogWeb.entity.MediaType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostResqDto(
		@NotBlank(message = "Title cannot be blank")
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")    
		String title,
		
		@NotBlank(message = "Content cannot be blank")
        String content        
        ) {


}
