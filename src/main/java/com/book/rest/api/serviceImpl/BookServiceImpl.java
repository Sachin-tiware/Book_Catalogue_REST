package com.book.rest.api.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.book.rest.api.entity.Book;
import com.book.rest.api.repository.BookRepository;
import com.book.rest.api.service.BookService;
import com.book.rest.api.util.Utils;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	private Utils utils;

	@Autowired
	public BookServiceImpl(Utils utils) {
		this.utils = utils;
	}

	@Override
	public void saveBook(Book book) {
		if (book.getIsbn() == null && book.getPublicationDate() == null) {
			book.setIsbn(utils.generateISBN());
			book.setPublicationDate(new Date());
		}
		bookRepository.save(book);
	}

	@Override
	public List<Book> findAllBook() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> findBookById(Long theId) {
		return bookRepository.findById(theId);
	}

	@Override
	public void deleteBook(Long theId) {
		bookRepository.deleteById(theId);
	}

	@Override
	public List<Book> findSearchedBook(String keyword) {
		return bookRepository.getSearchedBooks(keyword);
	}

}
