package com.blog.blogWeb.dto;

import com.blog.blogWeb.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserResponseDto(
		Long id,
        String username,
        String email,
        Role role
        ) {

}
