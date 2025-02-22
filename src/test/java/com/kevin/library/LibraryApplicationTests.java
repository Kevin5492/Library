package com.kevin.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kevin.library.repository.BookRepository;
import com.kevin.library.repository.UserRepository;
import com.kevin.library.service.BookService;
import com.kevin.library.service.UserService;

@SpringBootTest
class LibraryApplicationTests {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private BookService bookService;
	@Test
	void contextLoads() {
		System.out.println("有呼叫0");
//		userService.insertNewUser("0987654321", "123", "123");
		System.out.println(userRepo.getPassword("0987654321"));
		System.out.println(userService.checkPassword("0987654321","123"));
		System.out.println("有呼叫1");
		System.out.println(bookService.getBorrowableBooks(""));
		System.out.println(bookRepo.checkIfBookIsEnough("978-0-13-235088-4"));
		;
	}

}
