package com.blog.blogWeb.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.blog.blogWeb.Services.PostService;
import com.blog.blogWeb.entity.Post;
import com.blog.blogWeb.exception.PoatNotFoundException;
import com.blog.blogWeb.security.JwtProvider;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
public final class PostControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	PostService servicePost;
	
	@MockitoBean
	private JwtProvider jwtProvider;
	
	@Test
	public void shouldBeReturnAllPosts() throws Exception {
		List<Post> posts = Arrays.asList( 
				new Post(),
				new Post()); 
	
		Page<Post> pageabl = new PageImpl<>(posts);
		
		when(servicePost.getAllPosts(any())).thenReturn(pageabl);
		
		
		mockMvc.perform(get("/api/posts"))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.tasks.size()").value(2));
	}
	
	@Test
    public void shouldCreatePostSuccessfully() throws Exception {
        mockMvc.perform(multipart("/api/posts/user")
               .contentType(MediaType.MULTIPART_FORM_DATA)
               .param("title", "test ")
               .param("content", "Body Content")
               .param("mediaType", "IMAGE")
               //.param("file", null)
               )
               .andExpect(status().isOk()); 
    }
	@Test
    public void shouldDeletePostSuccessfully() throws Exception {
	  Long id = 1L;
	  
		doNothing().when(servicePost).deletePost(id);;

		
		mockMvc.perform(delete("/api/posts/delete/{postId}" , id))
		.andExpect(status().isOk());
	}
	
	@Test
    public void shouldReturnNotFoundWhenDeletingNonExistingPost() throws Exception {
	  Long id = 99L;
	  
		doThrow(new PoatNotFoundException(id)).when(servicePost).deletePost(id);

		
		mockMvc.perform(delete("/api/posts/delete/{postId}" , id))
		.andExpect(status().isNotFound());
	}

}
