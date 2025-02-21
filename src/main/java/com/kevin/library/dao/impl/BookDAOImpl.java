package com.kevin.library.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.kevin.library.dao.BookDAO;
import com.kevin.library.dto.BorrowableBooksDTO;
import com.kevin.library.dto.CurrentBorrowBooksDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

public class BookDAOImpl implements BookDAO {
	@PersistenceContext
	private EntityManager entityManager;
	@Override //取得所有可以借的書
	public List<BorrowableBooksDTO> getBorrowableBooks() {
		try {
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GetBorrowableBooks");
		    query.execute();
		    
		    @SuppressWarnings("unchecked")
		    List<Object[]> resultList = query.getResultList();
		    
		    List<BorrowableBooksDTO> dtos = resultList.stream().map(row -> new BorrowableBooksDTO(
		            (String) row[0],  // isbn
		            (String) row[1],  // name
		            (String) row[2],  // author
		            (String) row[3],  // introduction
		            ((Number) row[4]).intValue()  // borrowable
		    )).collect(Collectors.toList());
		    
		    System.out.println("Size:" + dtos.size());
		    return dtos;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	    
	}
	@Override //取得所有已經借的書
	public List<CurrentBorrowBooksDTO> showCurrentBorrowBooks(Integer userId){
		try {
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ShowCurrentBorrowBooks");
			query.registerStoredProcedureParameter("userId", Integer.class, ParameterMode.IN);
			query.setParameter("userId", userId); 
			query.execute();
		    
		    @SuppressWarnings("unchecked")
		    List<Object[]> resultList = query.getResultList();
		    List<CurrentBorrowBooksDTO> dtos = resultList.stream().map(row -> new CurrentBorrowBooksDTO(
		            (String) row[0], // isbn
		            (Integer) row[1],// inventoryId
		            (String) row[2], // name
		            (String) row[3], // author
		            (String) row[4], // introduction
		            (Date) row[5],   // borrowingTime
		            (Integer)row[6]  // borrowingRecordId
		    )).collect(Collectors.toList());
		    return dtos;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
