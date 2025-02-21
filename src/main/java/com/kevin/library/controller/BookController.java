package com.kevin.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevin.library.dto.BookResponseDTO;
import com.kevin.library.dto.BorrowableBooksDTO;
import com.kevin.library.dto.CurrentBorrowBooksDTO;
import com.kevin.library.service.BookService;
import com.kevin.library.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/showAllBooks")
	public ResponseEntity<BookResponseDTO>showAllBorrowableBooks() {
		System.out.println("有呼叫");
		
			List<BorrowableBooksDTO> bookList = bookService.getBorrowableBooks();
			if(bookList!=null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponseDTO(true,"查詢成功",bookList,null));
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BookResponseDTO(false, "發生錯誤", null,null));
	}
	@GetMapping("/showBorrowedBook")
	public ResponseEntity<BookResponseDTO>showBorrowedBook(HttpServletRequest request){
		  String authHeader = request.getHeader("Authorization");
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BookResponseDTO(false, "Missing or invalid token", null,null));
	        }
	        String token = authHeader.substring(7);
	        Integer userId = jwtService.getUserIdFromToken(token);
	        	List<CurrentBorrowBooksDTO> borrowedBookList = bookService.getUserCurrentBorrowredBooks(userId);
	        	if (borrowedBookList!=null) {
	        		return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponseDTO(true,"查詢成功",null,borrowedBookList));
	        	}
	        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new BookResponseDTO(false, "發生錯誤", null,null));
	  
	}
}
