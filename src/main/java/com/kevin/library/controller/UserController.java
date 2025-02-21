package com.kevin.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevin.library.dto.UserRequestDTO;
import com.kevin.library.dto.UserResultDTO;
import com.kevin.library.service.UserService;
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/createUser")
	public ResponseEntity<UserResultDTO> createUser(@RequestBody UserRequestDTO request){
		try {
			 UserResultDTO result = userService.insertNewUser(
	                    request.getPhoneNumber(),
	                    request.getPassword(),
	                    request.getUserName());
	            return ResponseEntity.status(HttpStatus.CREATED).body(result);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResultDTO(false, "發生錯誤", null));
		}
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<UserResultDTO> userLogin(@RequestBody UserRequestDTO request){
//		UserResultDTO result = userService.checkPassword( request.getPhoneNumber(), null)
//	}
}
