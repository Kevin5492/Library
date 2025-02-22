package com.kevin.library.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ReturnRecordDTO(String isbn, 
		Integer inventoryId,
		String name, 
		String author, 
		String introduction,
		 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Taipei")
		Date returnTime,
		Integer borrowingRecordId) {

}
