package com.kevin.library.dao.impl;

import java.util.List;

import com.kevin.library.dao.BookDAO;
import com.kevin.library.dto.BorrowableBooksDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

public class BookDAOImpl implements BookDAO {
	@PersistenceContext
	private EntityManager entityManager;
	@Override //取得所有可以借的書
	public List<BorrowableBooksDTO> getBorrowableBooks() {
		 StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GetBorrowableBooks");
		    List<BorrowableBooksDTO> results = query.getResultList();
		    System.out.println(results.size());
		return results;
	}

}
