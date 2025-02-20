package com.kevin.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevin.library.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
