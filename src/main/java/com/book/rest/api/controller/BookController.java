package com.book.rest.api.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.book.rest.api.entity.Book;
import com.book.rest.api.exception.BookServiceException;
import com.book.rest.api.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/book")
public class BookController {

	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	  
	  @Autowired
		private KafkaTemplate<String, String> kafkaTemplate;
		
		String Topic = "book";

	    

	@GetMapping(value = {"","/home"})
	public ResponseEntity<?> homePage() {
		log.info("Welcome to Book Catalog Rest Api");
		return new ResponseEntity<>("Welcome to Book Catalog Rest Api", HttpStatus.OK);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createBook(@Valid @RequestBody Book book) {
		
		log.info(book+" "+book.getTitle()+ " "+ book.getAuthor());
		if(book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
			throw new BookServiceException("Fields cannot be empty.");
		}
		kafkaTemplate.send(Topic, "Book saved under book-topic  by Kafka-Zookeeper!!!");
		bookService.saveBook(book);
		log.info("Book Added.");
		
		return new ResponseEntity<>("Book Added.", HttpStatus.CREATED);
	}

	@GetMapping("/books")
	public List<Book> getAllBook() {
		log.info("Showing Books List");
		return bookService.findAllBook();
	}

	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> searchbBooks(@RequestBody String keyword) throws JsonProcessingException {
		log.info("String JSON Keyword Before: " + keyword);
		JSONObject json = new JSONObject(keyword);
		String search = json.getString("keyword");
		log.info("String JSON Keyword After: " + search);
		if (search == null) {
			log.info("Keyword cannot be empty.");
			throw new BookServiceException("Keyword cannot be empty.");
		}
		List<Book> books = bookService.findSearchedBook(search);
		if (books.isEmpty()) {
			log.info("Book not found with this keyword: " + search);
			throw new BookServiceException("Book not found with this keyword: " + search);
		}
		return books;
	}

	@GetMapping(value = "/search")
	public List<Book> searchBooks(@RequestParam("keyword") String keyword) {
		if (keyword == null) {
			log.info("Keyword cannot be empty.");
			throw new BookServiceException("Keyword cannot be empty.");
		}
		List<Book> books = bookService.findSearchedBook(keyword);
		if (books.isEmpty()) {
			log.info("Book not found with this keyword: " + keyword);
			throw new BookServiceException("Book not found with this keyword: " + keyword);
		}
		return books;
	}

	@GetMapping(path = "/{bookId}")
	public ResponseEntity<?> findBook(@PathVariable("bookId") Long bookId) {
		Optional<Book> book = bookService.findBookById(bookId);
		if (book.isEmpty()) {
			log.info("Book not found - " + bookId);
			throw new BookServiceException("Book not found - " + bookId);
		}
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@PutMapping(path = "/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateBook(@PathVariable("bookId") Long bookId, @Valid @RequestBody Book book) {
		Optional<Book> bookData = bookService.findBookById(bookId);
		if (!bookData.isPresent()) {
			log.info("Book not found - " + bookId);
			throw new BookServiceException("Book not found.");
		} else {
			book.setId(bookId);
			book.setTitle(book.getTitle());
			book.setAuthor(book.getAuthor());
			bookService.saveBook(book);
			log.info("Book updated - " + bookId);
		}
		return new ResponseEntity<>("Book Updated.", HttpStatus.OK);
	}

	@DeleteMapping(path = "/{bookId}")
	public ResponseEntity<?> deleteBook(@PathVariable("bookId") Long bookId) {
		Optional<Book> book = bookService.findBookById(bookId);
		if (book.isEmpty()) {
			log.info("Book not found - " + bookId);
			throw new BookServiceException("Book not found.");
		}
		bookService.deleteBook(bookId);
		log.info("Book deleted - " + bookId);
		return new ResponseEntity<>("Book Deleted.", HttpStatus.OK);
	}

}
