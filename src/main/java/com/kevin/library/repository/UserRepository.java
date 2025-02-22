package com.kevin.library.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.kevin.library.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	//新增使用者
	@Procedure(name = "InsertUser")
	public Integer insertUser(   
			@Param("phoneNumber") String phoneNumber,
            @Param("password") String password,
            @Param("userName") String userName); 
	//檢查手機有沒有重複
	@Procedure(name = "CheckIfPhoneIsValid")
	public Boolean  checkIfPhoneIsValid(@Param("userName")String phoneNumber);
	//找到密碼
	@Procedure(name = "GetPassword")
	public String getPassword(@Param("phoneNumber") String phoneNumber);
	//插入登入時間
	@Procedure(name = "GetUserIdAndSetLoginTime")
	public Integer getUserIdAndSetLoginTime(@Param("phoneNumber") String phoneNumber,@Param("currentTime")Date currentTime);
}
