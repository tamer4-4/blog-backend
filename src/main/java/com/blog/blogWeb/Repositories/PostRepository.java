package com.blog.blogWeb.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogWeb.entity.Comment;
import com.blog.blogWeb.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUserId(Long userId);

}
