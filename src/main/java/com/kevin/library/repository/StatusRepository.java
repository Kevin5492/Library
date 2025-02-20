package com.kevin.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevin.library.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
