package com.blog.blogWeb.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentResponseDto(
		Long id,
		String content,    
        LocalDateTime createdAt,
        UserResponseDto user,
        PostResponseDto post
) {

}
