package com.blog.blogWeb.Controllers;

import com.blog.blogWeb.Services.CommentService;
import com.blog.blogWeb.dto.CommentReqDto;
import com.blog.blogWeb.dto.CommentResponseDto;
import com.blog.blogWeb.entity.Comment;
import com.blog.blogWeb.exception.PoatNotFoundException;
import com.blog.blogWeb.exception.UserNotFoundException;
import com.blog.blogWeb.mapper.DtoMapper;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	@Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long postId,
           @Valid @RequestBody CommentReqDto content) throws UserNotFoundException, PoatNotFoundException {
        Comment comment = commentService.addComment(postId,content);
   
        return ResponseEntity.ok(DtoMapper.toCommentDto(comment));
    }
    
    @PutMapping("/update/post/{id}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.isOwner(#id , principal.username)")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentReqDto content ,
    		@PathVariable Long id) throws RuntimeException{
    	Comment  commentUpdate = commentService.updateComment(id, content);
    	return ResponseEntity.ok(DtoMapper.toCommentDto(commentUpdate));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Map<String, Object>> getCommentsByPostId(@PathVariable Long postId, 
    		   @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "createdAt") String sortBy,
	            @RequestParam(defaultValue = "DESC") String sortDir
    		) {
    	
    	      Sort sort = sortDir.equalsIgnoreCase("ASC")?
    			 Sort.by(sortBy).ascending()
    			 : Sort.by(sortBy).descending();
    	      
    	Pageable pageable = PageRequest.of(page, size, sort);
    	
       Map<String , Object> commentsMap = commentService.getCommentsByPostId(postId , pageable);
       
        return new ResponseEntity<>(commentsMap , HttpStatus.OK);
    }
    
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.isOwner(#id , principal.username)")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
    	commentService.deleteComment(id);
    	return ResponseEntity.noContent().build();
    }
}
