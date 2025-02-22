package com.kevin.library.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookResponseDTO( //有關書的請求的回覆
		Boolean success,
		String mssg,
		List<BorrowableBooksDTO> borrowableBookList, // 所有可以借的書
		List<CurrentBorrowBooksDTO> currentBorrowedBookList // 所有已經借的書
		) {

}
