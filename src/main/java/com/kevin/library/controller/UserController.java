package com.kevin.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevin.library.dto.UserReponseDTO;
import com.kevin.library.dto.UserRequestDTO;
import com.kevin.library.service.JwtService;
import com.kevin.library.service.UserService;
import com.kevin.library.util.XSSFilter;

import jakarta.servlet.http.HttpServletRequest;
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/createUser") //建立使用者
	public ResponseEntity<UserReponseDTO> createUser(@RequestBody UserRequestDTO request){
		try {
			
			String sanitizedPhone = XSSFilter.sanitize(request.getPhoneNumber());//避免XSS
	        String sanitizedPassword = XSSFilter.sanitize(request.getPassword());
	        String sanitizedUserName = XSSFilter.sanitize(request.getUserName());

	        if (sanitizedPhone == null || sanitizedPassword == null || sanitizedUserName == null) { //避免有空值
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new UserReponseDTO(false, "請填寫完整資料", null));
	        }
	        UserReponseDTO result = userService.insertNewUser(sanitizedPhone, sanitizedPassword, sanitizedUserName);
	            return ResponseEntity.status(HttpStatus.CREATED).body(result);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserReponseDTO(false, "發生錯誤", null));
		}
	}
	
	@PostMapping("/login") //登入帳號
	public ResponseEntity<UserReponseDTO> userLogin(@RequestBody UserRequestDTO request){
		System.out.print("有被呼叫"); //測試有沒有被呼叫
		try {
			UserReponseDTO result = userService.checkPassword( request.getPhoneNumber(), request.getPassword());
			System.out.print("user id "+result.userId());
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
	
    @PostMapping("/logout")
    public ResponseEntity<UserReponseDTO> userLogout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) { //抓到JWT 並放到黑名單中
            String token = authHeader.substring(7);
            jwtService.blacklistToken(token); 
        }
        return ResponseEntity.ok().body(new UserReponseDTO(true, "登出成功", null));
    }
}
