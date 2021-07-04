package com.book.rest.api.service;

import java.util.List;
import java.util.Optional;

import com.book.rest.api.entity.Book;

public interface BookService {

	void saveBook(Book book);
	public List<Book> findAllBook();
	public Optional<Book> findBookById(Long theId);
	public void deleteBook(Long theId);
	public List<Book> findSearchedBook(String keyword);
}
