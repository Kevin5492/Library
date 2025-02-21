package com.kevin.library.dao;

import java.util.List;

import com.kevin.library.dto.BorrowableBooksDTO;

public interface BookDAO {
	public List<BorrowableBooksDTO> getBorrowableBooks();
}
