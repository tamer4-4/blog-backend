package com.blog.blogWeb.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blog.blogWeb.Repositories.UserRepositories;
import com.blog.blogWeb.Services.PostService;
import com.blog.blogWeb.Services.UserService;
import com.blog.blogWeb.dto.CommentReqDto;
import com.blog.blogWeb.dto.CommentResponseDto;
import com.blog.blogWeb.dto.PostResponseDto;
import com.blog.blogWeb.dto.PostResqDto;
import com.blog.blogWeb.dto.UserResponseDto;
import com.blog.blogWeb.dto.UserResqDto;
import com.blog.blogWeb.entity.Comment;
import com.blog.blogWeb.entity.Post;
import com.blog.blogWeb.entity.User;

@Component
public class DtoMapper {

    public static UserResponseDto toUserDto(User user) {
        if (user == null) return null;
        
        return  UserResponseDto.builder()
        		.id(user.getId())
        		.username(user.getUsername())
        		.email(user.getEmail())
        		.role(user.getRole())
        		.build();
                   
    }
    
    public static User toEntity(UserResqDto userReq)  {
     
    	
   	 return User.builder()
   			 .username(userReq.username())
   			 .email(userReq.email())
   			 .password(userReq.password())
   			 .role(userReq.role())
   			 .build();
   	 
   	 
    }
    
    public static Post toEntity(PostResqDto  postReq , User user)  {
 
      	 return Post.builder()
      			 .title(postReq.title())
      			 .content(postReq.content())
      			 .user(user)
      			 .build();
      	 
      	 
       }
    
    public static Comment toEntity(CommentReqDto  commentReq , User user , Post post)  {
  
      	 return Comment.builder()
      			 .content(commentReq.content())
      			 .post(post)
      			 .user(user)
      			 .build();
                 
      	 
      	 
       }

    public static PostResponseDto toPostDto(Post post) {
        if (post == null) return null;
        int count = (post.getComments() != null) ? post.getComments().size() : 0;

        
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMediaUrl(),
                post.getMediaType(),
                post.getCreatedAt(),
                post.getUser() != null ? post.getUser().getId() : null ,
                count
        );
    }

    public static CommentResponseDto toCommentDto(Comment comment) {
        if (comment == null) return null;
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                toUserDto(comment.getUser()),
                toPostDto(comment.getPost())
        
        );
    }
}
