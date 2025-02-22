package com.kevin.library.dto;
public record BorrowableBooksDTO( // 每一筆可以借的書
		String isbn,
		String name, 
		String author, 
		String introduction,
		Integer borrowable) {

}
