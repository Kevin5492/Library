package com.kevin.library.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CurrentBorrowBooksDTO( // 每一筆已經借的書
		String isbn, 
		Integer inventoryId,
		String name, 
		String author, 
		String introduction,
		 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Taipei")
		Date borrowingTime,
		Integer borrowingRecordId) {}
 