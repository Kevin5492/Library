package com.kevin.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevin.library.model.BorrowingRecord;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {

}
