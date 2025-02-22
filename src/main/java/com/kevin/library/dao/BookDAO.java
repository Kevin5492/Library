package com.kevin.library.dao;

import java.util.List;

import com.kevin.library.dto.BorrowableBooksDTO;
import com.kevin.library.dto.CurrentBorrowBooksDTO;

public interface BookDAO {
	//查看所有書
	public List<BorrowableBooksDTO> getBorrowableBooks(String searchInput);
	//查看已借閱的書
	public List<CurrentBorrowBooksDTO> showCurrentBorrowBooks(Integer userId);
}
