package com.kevin.library.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.library.dto.UserReponseDTO;
import com.kevin.library.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Transactional/*新增使用者*/
	public UserReponseDTO insertNewUser(
			String phoneNumber,
            String password,
            String userName) {
		if(!userRepo.checkIfPhoneIsValid(phoneNumber)) {
			try {
				String encodedPwd = pwdEncoder.encode(password);
				return new UserReponseDTO(true,"新增成功",userRepo.insertUser(phoneNumber,encodedPwd,userName));
			}catch(Exception e) {
				e.printStackTrace();
				return new UserReponseDTO(false,"新增失敗",null);
			}
		}
		return new UserReponseDTO(false,"手機號碼已存在",null);
	}
	/*比對密碼*/
	
	public UserReponseDTO checkPassword(String phoneNumber,String password) {
		try {
			
			if(pwdEncoder.matches(password,userRepo.getPassword(phoneNumber))) {
				Date currentTime = new Date();
				return new UserReponseDTO(true,"密碼正確",userRepo.getUserIdAndSetLoginTime(phoneNumber,currentTime));
			}
			return new UserReponseDTO(true,"密碼錯誤",null);
		}catch(Exception e) {
			e.printStackTrace();
			return new UserReponseDTO(false,"登入失敗",null);
		}
	}
}
