package com.blog.blogWeb.Services;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blogWeb.Repositories.PostRepository;
import com.blog.blogWeb.Repositories.UserRepositories;
import com.blog.blogWeb.dto.PostResqDto;
import com.blog.blogWeb.entity.MediaType;
import com.blog.blogWeb.entity.Post;
import com.blog.blogWeb.entity.User;
import com.blog.blogWeb.exception.FileSotreException;
import com.blog.blogWeb.exception.PoatNotFoundException;
import com.blog.blogWeb.exception.UserNotFoundException;
import com.blog.blogWeb.mapper.DtoMapper;
import com.blog.blogWeb.security.CustomUserDetails;
import com.blog.blogWeb.util.FileStorageService;
import com.blog.blogWeb.util.UserDetails;

@Service

public class PostService {
	@Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepositories userRepository;
    
    @Autowired 
    private FileStorageService fileStorge;


    @Autowired 
    private  UserDetails userDetails;

    
//  public Post createPost(Long userId, PostResqDto postRequest) throws UserNotFoundException {
//	User user = userRepository.findById(userId)
//			.orElseThrow(() -> new UserNotFoundException(userId));
//    Post postEntity =   DtoMapper.toEntity(postRequest , user);
//    return postRepository.save(postEntity);
//} 

    public boolean isOwner(Long id , String username) {
        Post post = postRepository.findById(id).orElse(null);
        System.out.println("test IsOwner");
        if (post == null) return false;
        
        return post.getUser().getUsername().equals(username);
    }
    
    
    public Post createPostWithMedia(PostResqDto postRequest , MultipartFile file) throws UserNotFoundException, FileSotreException {
	    		  
    	User user = userRepository.findById(userDetails.getUserDeatils().getId())
    			.orElseThrow(() -> new UserNotFoundException("This User is Not Found"));
    	
    	
    	MediaType mediaType = null;

    if(file != null && !file.isEmpty()) {
    	String contentType = file.getContentType();
	    System.out.println("media1 =>" + mediaType );

    	if (contentType != null && contentType.startsWith("image")) {
    	    mediaType = MediaType.IMAGE;

    	} else if (contentType != null && contentType.startsWith("video")) {
    	    mediaType = MediaType.VIDEO;

    	} else {
    	    throw new FileSotreException("عفواً، مسموح بررفع الصور والفيديوهات فقط!");
    	}
    	
    }
 
        	 String mediaUrl = fileStorge.saveFile(file);    	 
    	 
    	Post post = Post.builder()
                .title(postRequest.title())
                .content(postRequest.content())
                .mediaUrl(mediaUrl)
                .mediaType(mediaType)
                .user(user)
                .build();
    	
    	return postRepository.save(post);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPostById(Long id) throws PoatNotFoundException {
        return postRepository.findById(id)
        		.orElseThrow(() -> new PoatNotFoundException(id));
    }

    public void deletePost(Long postId) throws PoatNotFoundException {
//    	Long id  = userDetails.getUserDeatils().getId();
    	
//     Post post =  postRepository.findById(postId)
//             .orElseThrow(() -> new PoatNotFoundException(postId));
//     
//     if(!(post.getUser().getId().equals(id))) {
//    	 throw new  AccessDeniedException("Not Allowed");
//     }
        postRepository.deleteById(postId);
    }
    
    public Post updatePost(Long id , PostResqDto updatePost , MultipartFile file) throws PoatNotFoundException, FileSotreException {
    	
    	  Post post =	postRepository.findById(id)
    	        	.orElseThrow(() -> new PoatNotFoundException(id));
    	        
    	        post.setContent(updatePost.content());
    	        post.setTitle(updatePost.title() );
    	         
    	    	MediaType mediaType = null;
    	    	if(file != null && !file.isEmpty()) {
        	     	String contentType = file.getContentType();
        	        
    	    	if (contentType.startsWith("image")) {
    	    	    mediaType = MediaType.IMAGE;
    	    	} else if (contentType != null && contentType.startsWith("video")) {
    	    	    mediaType = MediaType.VIDEO;
    	    	} else {
    	    	    throw new FileSotreException("عفواً، مسموح بررفع الصور والفيديوهات فقط!");
    	    	}
    	    	}
    	    	
    	        post.setMediaUrl(fileStorge.saveFile(file));  
    	    	post.setMediaType(mediaType);
    	        
    	        	return postRepository.save(post);
    	        }
    }
    
    	
 
