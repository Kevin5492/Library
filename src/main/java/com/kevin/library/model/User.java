package com.kevin.library.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="[user]")
public class User {
	
	@Id
	@Column(name="user_id")
    private Integer userId;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "last_login_time")
	private Date lastLoginTime;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "[user]", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<BorrowingRecord> BorrowingRecord = new HashSet<>();
}
