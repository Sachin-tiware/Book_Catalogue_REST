package com.book.rest.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.book.rest.api.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	@Query(value = "select distinct b from Book b where locate(?1, b.title)>0 or locate(?1, b.author)>0 or b.isbn<=?1")
	List<Book> getSearchedBooks(String keyword);
}
