package it.ttf.jsf.beans;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.RateEvent;

import it.ttf.db.operation.DatabaseOperationBean;


@ManagedBean
@RequestScoped

public class BooksBean {

	private int id;  
	private String title;
	private String genre; 
	private String author;
	private String pages;
	private String release;
	private Integer rating1;
	
	public ArrayList booksListFromDB;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}

	    public Integer getRating1() {
	        return rating1;
	    }
	 
	    public void setRating1(Integer rating1) {
	        this.rating1 = rating1;
	    }
	
	@PostConstruct
	public void init() {
		booksListFromDB = DatabaseOperationBean.getBooksListFromDB();
	}

	public ArrayList booksList() {
		return booksListFromDB;
	}
	public String returnBooksList() {
		return "booksList";
	}
	
	public String saveBooksDetails(BooksBean newBooksObj) {
		return DatabaseOperationBean.saveBooksDetailsInDB(newBooksObj);
	}
	
	public String editBooksRecord(int bookId) {
		return DatabaseOperationBean.editBooksRecordInDB(bookId);
	}
	
	public String updateBooksDetails(BooksBean updateBooksObj) {
		return DatabaseOperationBean.updateBooksDetailsInDB(updateBooksObj);
	}
	
	public String deleteBooksRecord(int bookId) {
		return DatabaseOperationBean.deleteBooksRecordInDB(bookId);
	}

    
}
