package com.blog.blogWeb.Repositories;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogWeb.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public interface UserRepositories extends JpaRepository<User, Long> {
     public Optional<User> findByUsername(String userName);
     public User findByEmail(String email);
     public Boolean existsByUsername(String username);
	 public Boolean existsByEmail( String email);
     public Page<User> findByUsernameContainingIgnoreCase(String userName , Pageable pageable);

}
