package com.blog.blogWeb.Services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.blog.blogWeb.Repositories.CommentRepository;
import com.blog.blogWeb.Repositories.PostRepository;
import com.blog.blogWeb.Repositories.UserRepositories;
import com.blog.blogWeb.dto.CommentReqDto;
import com.blog.blogWeb.dto.CommentResponseDto;
import com.blog.blogWeb.entity.Comment;
import com.blog.blogWeb.entity.Post;
import com.blog.blogWeb.entity.User;
import com.blog.blogWeb.exception.PoatNotFoundException;
import com.blog.blogWeb.exception.UserNotFoundException;
import com.blog.blogWeb.mapper.DtoMapper;
import com.blog.blogWeb.util.UserDetails;

@Service
public class CommentService {
	@Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepositories userRepository;
    
    @Autowired
    private UserDetails userDetalis;

    public Comment addComment(Long postId, CommentReqDto content) throws UserNotFoundException
    , PoatNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PoatNotFoundException(postId));
        
        User user = userRepository.findById(userDetalis.getUserDeatils().getId())
                .orElseThrow(() -> new UserNotFoundException("This User is Not Found"));

      Comment commentEntity =  DtoMapper.toEntity(content, user, post);
        
        return commentRepository.save(commentEntity);
    }

    public Map<String, Object> getCommentsByPostId(Long postId , Pageable pageable) {
        Page<Comment> Comments =  commentRepository.findByPostId(postId , pageable);
     
        List<CommentResponseDto> commentsRsepons =   Comments.getContent().stream()
        .map(DtoMapper::toCommentDto).toList();
        
        Map<String, Object> response = new HashMap<>();

        response.put("tasks", commentsRsepons);
        response.put("currentPage", Comments.getNumber());
        response.put("totalItems", Comments.getTotalElements());
        response.put("totalPages", Comments.getTotalPages());
        response.put("hasNext", Comments.hasNext());
        response.put("hasPrevious", Comments.hasPrevious());
      
      return response;
    }
    
    public boolean isOwner(Long CommenttId, String username) {
        Comment comment = commentRepository.findById(CommenttId).orElse(null);
        if (comment == null) return false;
        
        return comment.getUser().getUsername().equals(username);
    }
    
    
    public void deleteComment(Long id) {
    	Comment comment = commentRepository.findById(id)
    			.orElseThrow(() -> new RuntimeException("Comment is not exist"));
    	commentRepository.delete(comment);;
    }
    
    
    public Comment updateComment(Long id , CommentReqDto contentReq ) throws RuntimeException
    {
     Comment comment = commentRepository.findById(id)
    		 .orElseThrow(()->new RuntimeException("Not found comment " + id));
     
     comment.setContent(contentReq.content());
       
     return commentRepository.save(comment);

    }
    
}