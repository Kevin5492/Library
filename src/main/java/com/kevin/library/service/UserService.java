package com.kevin.library.service;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
		String phonePattern = "^09\\d{8}$"; // 驗證手機格式
		
		if(!Pattern.matches(phonePattern, phoneNumber)) {
			return new UserReponseDTO(false,"手機號碼格式錯誤",null);
		}
		
        //驗證密碼（8-20 字元，包含大小寫、數字、特殊符號）
		String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$";
	    if (!Pattern.matches(passwordPattern, password)) {
	        return new UserReponseDTO(false, "密碼須包含大小寫字母、數字、特殊符號，長度 8-20", null);
	    }

	    // 驗證名稱（僅限中英文、數字，長度 2-20）
	    String namePattern = "^[\\p{L}\\p{N}]{2,20}$";
	    if (!Pattern.matches(namePattern, userName)) {
	        return new UserReponseDTO(false, "名稱只能包含中英文、數字，長度 2-20", null);
	    }
		if(!userRepo.checkIfPhoneIsValid(phoneNumber)) {
			try {
				String encodedPwd = pwdEncoder.encode(password);
				return new UserReponseDTO(true,"新增成功",userRepo.insertUser(phoneNumber,encodedPwd,userName));
			}catch (DataIntegrityViolationException e) {
	            return new UserReponseDTO(false, "手機號碼已存在（請稍後重試）", null); //用來檢查資料庫插入會不會因為競爭出錯
	        } catch(Exception e) {
				e.printStackTrace();
				return new UserReponseDTO(false,"新增失敗",null);
			}
		}
		return new UserReponseDTO(false,"手機號碼已存在",null);
	}
	
	//比對密碼
	public UserReponseDTO checkPassword(String phoneNumber,String password) {
		try {
			
			if(pwdEncoder.matches(password,userRepo.getPassword(phoneNumber))) {
				System.out.println("密碼正確");
				Date currentTime = new Date();
				return new UserReponseDTO(true,"密碼正確",userRepo.getUserIdAndSetLoginTime(phoneNumber,currentTime));
			}
			System.out.println("密碼不正確");
			return new UserReponseDTO(false,"密碼錯誤",null);
		}catch(Exception e) {
			e.printStackTrace();
			return new UserReponseDTO(false,"登入失敗",null);
		}
	}
}
