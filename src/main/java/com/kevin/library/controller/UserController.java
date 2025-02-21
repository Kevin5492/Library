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
import com.kevin.library.dto.UserReponseDTO;
import com.kevin.library.service.JwtService;
import com.kevin.library.service.UserService;
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/createUser")
	public ResponseEntity<UserReponseDTO> createUser(@RequestBody UserRequestDTO request){
		try {
			 UserReponseDTO result = userService.insertNewUser(
	                    request.getPhoneNumber(),
	                    request.getPassword(),
	                    request.getUserName());
	            return ResponseEntity.status(HttpStatus.CREATED).body(result);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserReponseDTO(false, "發生錯誤", null));
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserReponseDTO> userLogin(@RequestBody UserRequestDTO request){
		
		try {
			UserReponseDTO result = userService.checkPassword( request.getPhoneNumber(), request.getPassword());
			if (result.success()) {
				String token = jwtService.generateToken(result.userId());

	            // 將 token 放入 HTTP Header
	            return ResponseEntity.ok()
	                    .header("Authorization", "Bearer " + token)
	                    .body(result); 
			}
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new UserReponseDTO(false, "發生錯誤", null));
		}
	}
}
