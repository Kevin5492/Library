package com.kevin.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevin.library.dto.BookRequestDTO;
import com.kevin.library.dto.BookResponseDTO;
import com.kevin.library.dto.BorrowableBooksDTO;
import com.kevin.library.dto.CurrentBorrowBooksDTO;
import com.kevin.library.service.BookService;
import com.kevin.library.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/showAllBooks") //顯示所有可以借閱的書
	public ResponseEntity<BookResponseDTO>showAllBorrowableBooks(@RequestBody BookRequestDTO requestDTO) {
		String searchInput = requestDTO.getSearchInput() ==null ? "" : requestDTO.getSearchInput(); // 拿到搜尋內容 並確保不是空值
		
			List<BorrowableBooksDTO> bookList = bookService.getBorrowableBooks(searchInput);
			if(bookList!=null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponseDTO(true,"查詢成功",bookList,null));
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BookResponseDTO(false, "發生錯誤", null,null));
	}
	@GetMapping("/showBorrowedBook") // 顯示已經借閱的書
	public ResponseEntity<BookResponseDTO>showBorrowedBook(HttpServletRequest request){
		Integer userId = (Integer) request.getAttribute("userId");
	        System.out.println("userId: "+userId);
	        	List<CurrentBorrowBooksDTO> borrowedBookList = bookService.getUserCurrentBorrowredBooks(userId);
	        	if (borrowedBookList!=null) {
	        		return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponseDTO(true,"查詢成功",null,borrowedBookList));
	        	}
	        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new BookResponseDTO(false, "發生錯誤", null,null));
	  
	}
	@PostMapping("/borrowABook") // 借一本書
	public ResponseEntity<BookResponseDTO> borrowABook(HttpServletRequest request,@RequestBody BookRequestDTO requestDTO){
		
		Integer userId = (Integer) request.getAttribute("userId");
	        String isbn = requestDTO.getIsbn();
	        System.out.println("isbn"+isbn);
	        Integer inventoryId = bookService.borrowABook(isbn,userId);
	        if(inventoryId!=null) {
	        	return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponseDTO(true,"借閱成功，書籍編號: "+inventoryId,null,null));
	        }
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BookResponseDTO(false, "發生錯誤", null,null));
	}
	@PostMapping("/returnABook") // 借一本書
	public ResponseEntity<BookResponseDTO> returnABook(HttpServletRequest request,@RequestBody BookRequestDTO requestDTO){
		
	        Integer userId = (Integer) request.getAttribute("userId");
	        Integer borrowingRecordId = requestDTO.getBorrowingRecordId();
	        if(borrowingRecordId == null ) {
	        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new BookResponseDTO(false, "您歸還的書籍並不正確", null,null));
	        }
	        Integer result = bookService.returnABook(borrowingRecordId,userId);
	        if(result == null ) {
	        	System.out.println("是null");
	        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(new BookResponseDTO(false, "發生錯誤", null,null));
	        }
	        System.out.println("result: "+result);
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponseDTO(true,"還書成功",null,null));
	        
	}
		
}
