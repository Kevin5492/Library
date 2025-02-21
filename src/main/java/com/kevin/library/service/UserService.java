package com.kevin.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.library.dto.UserResultDTO;
import com.kevin.library.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Transactional/*新增使用者*/
	public UserResultDTO insertNewUser(
			String phoneNumber,
            String password,
            String userName) {
		if(!userRepo.checkIfPhoneIsValid(phoneNumber)) {
			try {
				String encodedPwd = pwdEncoder.encode(password);
				return new UserResultDTO(true,"新增成功",userRepo.insertUser(phoneNumber,encodedPwd,userName));
			}catch(Exception e) {
				e.printStackTrace();
				return new UserResultDTO(false,"新增失敗",null);
			}
		}
		return new UserResultDTO(false,"手機號碼已存在",null);
	}
	/*比對密碼*/
	public UserResultDTO checkPassword(String phoneNumber,String password) {
		try {
			if(pwdEncoder.matches(password, userRepo.getPassword(phoneNumber))) {
				return new UserResultDTO(true,"密碼正確",null);
			}
			return new UserResultDTO(true,"密碼錯誤",null);
		}catch(Exception e) {
			e.printStackTrace();
			return new UserResultDTO(false,"登入失敗",null);
		}
	}
}
