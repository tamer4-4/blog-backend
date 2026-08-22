
package com.blog.blogWeb.Controllers;

import com.blog.blogWeb.Services.PostService;
import com.blog.blogWeb.dto.PostResponseDto;
import com.blog.blogWeb.dto.PostResqDto;
import com.blog.blogWeb.entity.Post;
import com.blog.blogWeb.exception.FileSotreException;
import com.blog.blogWeb.exception.PoatNotFoundException;
import com.blog.blogWeb.exception.UserNotFoundException;
import com.blog.blogWeb.mapper.DtoMapper;
import com.blog.blogWeb.security.CustomUserDetailsService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	@Autowired
    private PostService postService;

//    @PostMapping("/user/{userId}")
//    public ResponseEntity<PostResponseDto> createPost(@PathVariable Long userId, @Valid @RequestBody PostResqDto post) throws UserNotFoundException {
//        Post createdPost = postService.createPost(userId, post);
//        return ResponseEntity.ok(DtoMapper.toPostDto(createdPost));
//    }
    
	@PostMapping(value = "/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostResponseDto> createPost(
	        @RequestParam("title") String title,
	        @RequestParam("content") String content,
	        @RequestParam(value = "file", required = false) MultipartFile file) throws UserNotFoundException, FileSotreException {

		PostResqDto requestDto = new PostResqDto(title, content);
	      
	    Post savedPost = postService.createPostWithMedia(requestDto, file);
	    return ResponseEntity.ok(DtoMapper.toPostDto(savedPost));
	}

    @GetMapping
    public ResponseEntity<Map<String , Object>> getAllPosts(
    	      @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "createdAt") String sortBy,
	            @RequestParam(defaultValue = "DESC") String sortDir
    		) {
    	Sort sort = sortDir.equalsIgnoreCase("asc")?
    			Sort.by(sortBy).ascending():
        	    Sort.by(sortBy).descending();

    	Pageable pageable = PageRequest.of(page, size , sort);
    	Page<Post> posts =  postService.getAllPosts(pageable);
    List<PostResponseDto> postRespons =	posts.getContent().
    	stream().map(DtoMapper::toPostDto)
    	.toList();
    Map<String, Object> response = new HashMap<>();

    response.put("tasks", postRespons);
    response.put("currentPage", posts.getNumber());
    response.put("totalItems", posts.getTotalElements());
    response.put("totalPages", posts.getTotalPages());
    response.put("hasNext", posts.hasNext());
    response.put("hasPrevious", posts.hasPrevious());
    
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    
    
    @DeleteMapping("/delete/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @postService.isOwner(#postId , principal.username)")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) throws PoatNotFoundException, AccessDeniedException {
    	 postService.deletePost(postId);
    	 System.out.println("test Delete");
    	return ResponseEntity.ok("Post deleted successfully.");

    }
    
    @PutMapping("/update/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @postService.isOwner(#postId , principal.username)")
    public ResponseEntity<PostResponseDto> updatePost(
    		@PathVariable Long postId 
    		,@RequestParam("title") String title
    		,@RequestParam("contetn") String content
    		,@RequestParam(value = "file", required = false) MultipartFile file)
    				throws PoatNotFoundException, FileSotreException {
    	
    	PostResqDto postReq = new PostResqDto(title , content);
    	Post updeatedPost = postService.updatePost(postId, postReq, file);
    	
    	return  new ResponseEntity<>(DtoMapper.toPostDto(updeatedPost) , HttpStatus.OK);
    }
    
}


