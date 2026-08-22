package com.blog.blogWeb.dto;

import java.time.LocalDateTime;

import com.blog.blogWeb.entity.MediaType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostResponseDto(
		Long id,
		String title,
        String content,
        String mediaUrl,
        MediaType mediaType,
        LocalDateTime createdAt,
        Long user_id,
        int countComment

		) {

}
