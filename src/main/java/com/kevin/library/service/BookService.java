package com.kevin.library.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.library.dto.BorrowableBooksDTO;
import com.kevin.library.dto.CurrentBorrowBooksDTO;
import com.kevin.library.dto.ReturnRecordDTO;
import com.kevin.library.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepo;
	
	@Transactional //找到所有可以借的書
	public List<BorrowableBooksDTO> getBorrowableBooks(String searchInput){
		try {
			return bookRepo.getBorrowableBooks(searchInput);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//找到使用者已經借的書
	public List<CurrentBorrowBooksDTO>getUserCurrentBorrowredBooks(Integer userId){
		try {
			return bookRepo.showCurrentBorrowBooks(userId); 
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//查詢還書紀錄
	public List<ReturnRecordDTO>getUserReturnRecord(Integer userId){
		try {
			System.out.println("getUserReturnRecord user id "+ userId);
			return bookRepo.getReturnRecord(userId); 
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Transactional //借一本書
	public Integer borrowABook(String isbn,Integer userId) {
		try {
			Date currentTime = new Date();
			return bookRepo.borrowABook(isbn,userId,currentTime); //回傳 inventoryId
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@Transactional //還一本書
	public Integer returnABook(Integer borrowingRecordId,Integer userId) {
		if(userId.equals(bookRepo.getUserIdFromBorrowRecord(borrowingRecordId))) {//檢查是不是借書的人來還
			try {
				return bookRepo.returnABook(borrowingRecordId,new Date()); //回傳 0 是成功
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
