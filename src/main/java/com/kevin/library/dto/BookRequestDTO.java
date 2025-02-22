package com.kevin.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDTO {
	private String searchInput;
	private Integer borrowingRecordId;
	private String isbn;
}
