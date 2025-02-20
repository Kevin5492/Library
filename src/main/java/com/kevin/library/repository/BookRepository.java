package com.kevin.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kevin.library.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {

}
