package com.blog.blogWeb.Services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.blogWeb.Repositories.UserRepositories;
import com.blog.blogWeb.dto.UserResponseDto;
import com.blog.blogWeb.dto.UserResqDto;
import com.blog.blogWeb.entity.User;
import com.blog.blogWeb.exception.UserNotFoundException;
import com.blog.blogWeb.mapper.DtoMapper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Service
public class UserService {
	@Autowired
    private UserRepositories userRepository;

	  @Autowired
	    private PasswordEncoder passwordEncoder;

    public User registerUser(UserResqDto user) throws UserNotFoundException {
    	Boolean userExist = userRepository.existsByUsername(user.username());
    	Boolean userEmail = userRepository.existsByEmail(user.email());

    	if(userExist || userEmail) {
    		throw new UserNotFoundException("User Is alredy exist");
    	}
       User userEntity = DtoMapper.toEntity(user);
       userEntity.setPassword(passwordEncoder.encode(user.password()));
        return userRepository.save(userEntity);
    }

    public ResponseEntity<Map<String, Object>> getAllUsers(Pageable pageable) {  
    	 
    		Page<User> users = userRepository.findAll(pageable);
    		
        	List<UserResponseDto> userRespons = users.getContent()
        			.stream()
        			.map(user -> DtoMapper.toUserDto(user))
        			.toList();
        	
        	
            	Map<String, Object> response = new HashMap<>();
        	   response.put("users", userRespons);
        	   response.put("currentPage", users.getNumber());
    	       response.put("totalItems", users.getTotalElements());
    	       response.put("totalPages", users.getTotalPages());
    	       response.put("hasNext", users.hasNext());
    	       response.put("hasPrevious", users.hasPrevious());
    	       
    	 return new ResponseEntity<Map<String,Object>>(response  , HttpStatus.OK);
    }
    
    public ResponseEntity<Map<String, Object>> getUserByUsernameContainingIgnoreCase(String userName , Pageable pageable ){
    	Page<User> users; 
    	if(!userName.isEmpty() && userName != null) {
    		users = userRepository.findByUsernameContainingIgnoreCase(userName, pageable);
    	}else {
    		return getAllUsers(pageable);
    	}
    	
    	List<UserResponseDto> userRespons = users.getContent().stream().map(DtoMapper::toUserDto).toList();
    	
        	Map<String, Object> response = new HashMap<>();
   	       response.put("users", userRespons);
 	       response.put("currentPage", users.getNumber());
	       response.put("totalItems", users.getTotalElements());
	       response.put("totalPages", users.getTotalPages());
	       response.put("hasNext", users.hasNext());
	       response.put("hasPrevious", users.hasPrevious());
    	
    	return new ResponseEntity<>(response ,  HttpStatus.OK);
    }
   
    
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
        		.orElseThrow(() -> new UserNotFoundException("This User Is Not Found"));
    }

    
	public User getUserByUsername(String username){
		return userRepository.findByUsername(username).
				orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));        		
	}

}

