package com.blog.blogWeb.Controllers;
import com.blog.blogWeb.Services.UserService;
import com.blog.blogWeb.dto.UserResponseDto;
import com.blog.blogWeb.dto.UserResqDto;
import com.blog.blogWeb.entity.User;
import com.blog.blogWeb.exception.UserNotFoundException;
import com.blog.blogWeb.mapper.DtoMapper;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    private UserService userService;
    
	
//    @PostMapping("/register")
//    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserResqDto user) throws UserNotFoundException {
//        User savedUser = userService.registerUser(user);
//        return ResponseEntity.ok(DtoMapper.toUserDto(savedUser));
//    }

    //page
//    @GetMapping
//    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
//    	List<UserResponseDto> userRes =  userService.getAllUsers()
//    			.stream()
//    			.map(user->DtoMapper.toUserDto(user))
//    			.toList();
//    	
//        return ResponseEntity.ok(userRes);
//    }
    
	
    @GetMapping
    public ResponseEntity<Map<String , Object>> getAllUser(
    		@RequestParam(defaultValue =  "0") int page,
    		@RequestParam(defaultValue = "10") int size,
    		@RequestParam(defaultValue = "id") String sortBy,
    		@RequestParam(defaultValue = "DESC") String sortDir
    		){
    	Sort sort = sortDir.equalsIgnoreCase("ASC") ?
    			Sort.by(sortBy).ascending():
    			Sort.by(sortBy).descending();
    	
    	Pageable pageable = PageRequest.of(page, size , sort);
    	
    
    
	       
	       return userService.getAllUsers(pageable);
    }
    
    
    @GetMapping("/search/{username}")
    public ResponseEntity<Map<String, Object>> getUsersByUserName(
    		@PathVariable(required = false) String username,
    		@RequestParam(defaultValue =  "0") int page,
    		@RequestParam(defaultValue = "10") int size,
    		@RequestParam(defaultValue = "id") String sortBy,
    		@RequestParam(defaultValue = "DESC") String sortDir
    		){
    	
     	Sort sort = sortDir.equalsIgnoreCase("ACS")?
    			Sort.by(sortBy).ascending():
    			Sort.by(sortBy).descending();
    	
    	Pageable pageable = PageRequest.of(page, size , sort );
    	 ResponseEntity<Map<String, Object>> respons =  userService.getUserByUsernameContainingIgnoreCase(username, pageable);
    	 
    	 return respons;
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) throws UserNotFoundException{
    	User user = userService.getUserById(id);
    	return ResponseEntity.ok(DtoMapper.toUserDto(user));
    }
}
