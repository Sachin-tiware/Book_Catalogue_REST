package com.book.rest.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "title", nullable = false)
	@NotNull(message = "Title cannot be blank.")
	@Size(min = 2, max = 50, message = "Title must be equal to greater than 5 characters and less than 50 characters.")
	private String title;
	
	@Column(name = "Author", nullable = false)
	@NotNull(message = "Author cannot be blank.")
	@Size(min = 2, max = 50, message = "Autnor name must be equal to greater than 2 characters and less than 50 characters.")
	private String author;

	@Column(name = "ISBN", nullable = false, length = 13, unique = true)
	//@Max(value = 13)
	private Long isbn;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publication_date", nullable = false)
	@NotNull(message = "Publication date cannot be blank.")
	private Date publicationDate;

	public Book() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", publicationDate="
				+ publicationDate + "]";
	}

}
