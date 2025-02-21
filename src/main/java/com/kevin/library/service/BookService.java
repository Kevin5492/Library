package com.kevin.library.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.library.dto.BorrowableBooksDTO;
import com.kevin.library.dto.CurrentBorrowBooksDTO;
import com.kevin.library.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepo;
	
	@Transactional //找到所有可以借的書
	public List<BorrowableBooksDTO> getBorrowableBooks(){
		try {
			return bookRepo.getBorrowableBooks();
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
	@Transactional //借一本書
	public Integer borrowABook(String isbn,Integer userId) {
		try {
			return bookRepo.borrowABook(isbn,userId);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@Transactional //還一本書
	public Integer returnABook(Integer borrowRecordId,Integer userId) {
		if(userId.equals(bookRepo.getUserIdFromBorrowRecord(borrowRecordId))) {
			try {
				
				return bookRepo.returnABook(borrowRecordId,new Date());
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
