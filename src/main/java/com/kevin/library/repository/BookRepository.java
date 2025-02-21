package com.kevin.library.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.kevin.library.dao.BookDAO;
import com.kevin.library.model.Book;

public interface BookRepository extends JpaRepository<Book, String>,BookDAO {
	@Procedure(name="BorrowABook")
public Integer borrowABook(String isbn,Integer userId);
	@Procedure(name="CheckIfBookIsEnough")
public Integer checkIfBookIsEnough(String isbn);
	
	@Procedure(name="ReturnABook")
	public Integer returnABook(Integer borrowRecordId,Date currentTime);
	
	@Procedure(name="GetUserIdFromBorrowRecord")
	public Integer getUserIdFromBorrowRecord(Integer borrowRecordId);
}
