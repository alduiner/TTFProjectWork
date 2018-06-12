package it.ttf.db.operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;

import it.ttf.jsf.beans.BooksBean;

public class DatabaseOperationBean {
	
	public static Statement stmtObj;
	public static Connection connObj;
	public static ResultSet resultSetObj;
	public static PreparedStatement pstmt;

	public static Connection getConnection(){  
		try{  
			Class.forName("com.mysql.jdbc.Driver");     
			String db_url ="jdbc:mysql://localhost:3306/books",
					db_userName = "root",
					db_password = "root";
			connObj = DriverManager.getConnection(db_url,db_userName,db_password);  
		} catch(Exception sqlException) {  
			sqlException.printStackTrace();
		}  
		return connObj;
	}

	public static ArrayList getBooksListFromDB() {
		ArrayList booksList = new ArrayList();  
		try {
			stmtObj = getConnection().createStatement();    
			resultSetObj = stmtObj.executeQuery("SELECT * from books order by book_id ");    
			while(resultSetObj.next()) {  
				BooksBean stuObj = new BooksBean(); 
				stuObj.setId(resultSetObj.getInt("book_id"));  
				stuObj.setTitle(resultSetObj.getString("book_title"));
				stuObj.setGenre(resultSetObj.getString("book_genre"));
				stuObj.setAuthor(resultSetObj.getString("book_author"));
				stuObj.setPages(resultSetObj.getString("book_pages"));
				stuObj.setRelease(resultSetObj.getString("book_release_date"));
				stuObj.setRating1(resultSetObj.getInt("book_rating")); 
				  
				booksList.add(stuObj);  
			}   
			System.out.println("Total Records Fetched: " + booksList.size());
			connObj.close();
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		} 
		return booksList;
	}

	public static String saveBooksDetailsInDB(BooksBean newBooksObj) {
		int saveResult = 0;
		String navigationResult = "";
		try {      
			pstmt = getConnection().prepareStatement("insert into books (book_title, book_genre, book_author, book_pages, book_release_date, book_rating) values (?, ?, ?, ?, ?, ?)");			
			pstmt.setString(1, newBooksObj.getTitle());
			pstmt.setString(2, newBooksObj.getGenre());
			pstmt.setString(3, newBooksObj.getAuthor());
			pstmt.setString(4, newBooksObj.getPages());
			pstmt.setString(5, newBooksObj.getRelease());
			pstmt.setInt(6, newBooksObj.getRating1());
			
			
			saveResult = pstmt.executeUpdate();
			connObj.close();
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		if(saveResult !=0) {
			navigationResult = "studentList" + "?faces-redirect=true";
		} else {
			navigationResult = "createStudent" + "?faces-redirect=true";
		}
		return navigationResult;
	}

	public static String editBooksRecordInDB(int bookId) {
		BooksBean editRecord = null;
		System.out.println("editBooksRecordInDB() : Book Id: " + bookId);


		Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		try {
			stmtObj = getConnection().createStatement();    
			resultSetObj = stmtObj.executeQuery("select * from books where book_id = "+ bookId);    
			if(resultSetObj != null) {
				resultSetObj.next();
				editRecord = new BooksBean(); 
				editRecord.setId(resultSetObj.getInt("book_id"));
				editRecord.setTitle(resultSetObj.getString("book_title"));
				editRecord.setGenre(resultSetObj.getString("book_genre"));
				editRecord.setAuthor(resultSetObj.getString("book_author"));
				editRecord.setPages(resultSetObj.getString("book_pages"));
				editRecord.setRelease(resultSetObj.getString("book_release_date"));
				editRecord.setRating1(resultSetObj.getInt("book_rating"));
			}
			sessionMapObj.put("editRecordObj", editRecord);
			connObj.close();
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "editStudent" + "?faces-redirect=true";
	}

	public static String updateBooksDetailsInDB(BooksBean updateBooksObj) {
		try {
			pstmt = getConnection().prepareStatement("update books set book_id=?, book_genre=?, book_author=?, book_pages=?, book_release_date=?, book_rating=? where book_id=?");    
			pstmt.setString(1,updateBooksObj.getTitle());
			pstmt.setString(2,updateBooksObj.getGenre());
			pstmt.setString(3,updateBooksObj.getAuthor());  
			pstmt.setString(4,updateBooksObj.getPages());    
			pstmt.setString(5,updateBooksObj.getRelease());
			pstmt.setInt(6,updateBooksObj.getRating1());  
			pstmt.setInt(7,updateBooksObj.getId());  
			pstmt.executeUpdate();
			connObj.close();			
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "booksList" + "?faces-redirect=true";
	}

	public static String deleteBooksRecordInDB(int bookId){
		System.out.println("deleteBooksRecordInDB() : Book Id: " + bookId);
		try {
			pstmt = getConnection().prepareStatement("delete from books where book_id = " + bookId);  
			pstmt.executeUpdate();  
			connObj.close();
		} catch(Exception sqlException){
			sqlException.printStackTrace();
		}
		return "booksList" + "?faces-redirect=true";
	}
}

