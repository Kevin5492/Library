package com.kevin.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDTO { //跟書有關的request
	private String searchInput;
	private Integer borrowingRecordId;
	private String isbn;
}
