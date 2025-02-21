package com.kevin.library.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookResponseDTO(
		Boolean success,
		String mssg,
		List<BorrowableBooksDTO> borrowableBookList,
		List<CurrentBorrowBooksDTO> currentBorrowedBookList
		) {

}
