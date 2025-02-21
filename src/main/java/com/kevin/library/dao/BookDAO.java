package com.kevin.library.dao;

import java.util.List;

import com.kevin.library.dto.BorrowableBooksDTO;
import com.kevin.library.dto.CurrentBorrowBooksDTO;

public interface BookDAO {
	public List<BorrowableBooksDTO> getBorrowableBooks();
	
	public List<CurrentBorrowBooksDTO> showCurrentBorrowBooks(Integer userId);
}
