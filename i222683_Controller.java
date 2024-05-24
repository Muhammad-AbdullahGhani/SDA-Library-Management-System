package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class i222683_Controller {

	// Book class
	public class Book {
		private String title;
		private String author;
		private String isbn;
		private boolean avail;

		// Constructor
		public Book(String title, String author, String isbn, boolean availability) {
			this.title = title;
			this.author = author;
			this.isbn = isbn;
			this.avail = availability;
		}

		// Getters and setters
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

		public String getIsbn() {
			return isbn;
		}

		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}

		public boolean isAvailability() {
			return avail;
		}

		public void setAvailability(boolean availability) {
			this.avail = availability;
		}
	}

	@FXML
	private MenuItem addbook;

	@FXML
	private TextField authorid;

	@FXML
	private TextField deleteauthor;

	@FXML
	private MenuItem deletebook;
	@FXML
	private MenuItem editbook;

	@FXML
	private MenuItem exit;

	@FXML
	private TableColumn<Book, String> titleColumn;

	@FXML
	private TableColumn<Book, String> authorColumn;

	@FXML
	private TableColumn<Book, String> isbnColumn;

	@FXML
	private TableColumn<Book, Boolean> availabilityColumn;
	@FXML
	private MenuItem fileid;

	@FXML
	private TextField isbnid;

	@FXML
	private Button sortbutton;
	
	@FXML
	private Button searchbutton;

	@FXML
	private MenuItem newid;

	@FXML
	private MenuItem open;

	@FXML
	private MenuItem save;

	@FXML
	private MenuItem saveas;

	@FXML
	private MenuItem searchbook;

	@FXML
	private Button editsearchbutton;

	@FXML
	private Button edit;
	@FXML
	private Button bt1;

	@FXML
	private Button deletebuttonid;

	@FXML
	private TextField searchfield;

	@FXML
	private TextField editsearchtitle;

	@FXML
	private TextField editsearchisbn;

	@FXML
	private TextField editsearchauthor;

	@FXML
	private TextField editsearch;

	@FXML
	private RadioButton availableRadioButton;

	@FXML
	private RadioButton notavailableid;

	@FXML
	private TableView<Book> bookTableView;

	@FXML
	private TextField titleid;

	@FXML
	private MenuItem viewall;

	@FXML
	private void NavigateAddBook(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Addbookscene.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root, 600, 600);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void sorttable() {
	    try {
	      
	        String url = "jdbc:mysql://localhost:3306/new_schema"; 
	        String username = "root";
	        String password = "0d37aba1";

	        Connection myconn = DriverManager.getConnection(url, username, password);
	        String sort = "select * from books order by title";
	        Statement stmt = myconn.createStatement();
	        ResultSet myrs = stmt.executeQuery(sort);
	        List<Book> list = new ArrayList<>();
	        while (myrs.next()) {
	            String booktitle = myrs.getString("title");
	            String bookauthor = myrs.getString("author");
	            String bookisbn = myrs.getString("isbn");
	            boolean avail = myrs.getBoolean("availability");
	            list.add(new Book(booktitle, bookauthor, bookisbn, avail));
	        }
	        bookTableView.getItems().clear();
	        for (Book book : list) {
	            bookTableView.getItems().add(book);
	        }
	        myrs.close();
	        stmt.close();
	        myconn.close();

	    } catch (SQLException e) {
	        System.out.println("Error encountered");
	    }
	}


	@FXML
	private void DeleteButton(ActionEvent event) {
	    String Booktitle;
	    String Bookauthor;
	    String Bookisbn;
	    Booktitle = titleid.getText();
	    Bookauthor = authorid.getText();
	    Bookisbn = isbnid.getText();
	    if (Booktitle.isBlank() || Bookauthor.isBlank() || Bookisbn.isBlank()) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setContentText("Please fill all the fields");
	        alert.showAndWait();
	        return;
	    }

	    try {
	      
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        String url = "jdbc:mysql://localhost:3306/new_schema";
	        String username = "root";
	        String password = "0d37aba1";

	        try (Connection myconn = DriverManager.getConnection(url, username, password);
	             Statement mystmt = myconn.createStatement()) {

	            String check = "select * from books where title = '" + Booktitle + "' and author = '" + Bookauthor
	                    + "' AND isbn = '" + Bookisbn + "'";
	            ResultSet myRs = mystmt.executeQuery(check);

	            if (myRs.next()) {

	                String deletebook = "delete from books where title = '" + Booktitle + "' and author = '" + Bookauthor
	                        + "' AND isbn = '" + Bookisbn + "'";
	                mystmt.executeUpdate(deletebook);

	            }
	            myRs.close();

	            titleid.clear();
	            authorid.clear();
	            isbnid.clear();

	            updateTableView();

	        } 
	    } catch (Exception e) {
	    	System.out.println("Error found");
	    }
	}

		

	@FXML
	private void AddButton(ActionEvent event) {
	    String Booktitle;
	    String Bookauthor;
	    String Bookisbn;
	    Booktitle = titleid.getText();
	    Bookauthor = authorid.getText();
	    Bookisbn = isbnid.getText();
	    boolean availability = availableRadioButton.isSelected();

	    if (Booktitle.isBlank() || Bookauthor.isBlank() || Bookisbn.isBlank()) {
	    	Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setContentText("Please fill all the fields");
	        alert.showAndWait();
	        return;
	    }

	    try {
	       
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/new_schema";
	        String username = "root";
	        String password = "0d37aba1";

	        try (Connection myconn = DriverManager.getConnection(url, username, password)) {
	            Statement mystmt = myconn.createStatement();
	            String addbook = "insert into books (title, author, isbn, availability) values ('" + Booktitle + "', '" + Bookauthor + "', '" + Bookisbn + "', " + availability + ")";
	            mystmt.executeUpdate(addbook);
	       
	                titleid.clear();
	                authorid.clear();
	                isbnid.clear();

	                updateTableView();
	      
	            mystmt.close();
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println("Error");
	    }
	}


	@FXML
	private void NavigateeditBook(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("editbook.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root, 600, 600);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (IOException e) {
			System.out.println("Error found");
		}

	}
	
	@FXML
	private void editSearchBook(ActionEvent event) {
	   String searchisbn;
	    searchisbn = searchfield.getText();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        String url = "jdbc:mysql://localhost:3306/new_schema";
	        String username = "root";
	        String password = "0d37aba1";

	        try (Connection myconn = DriverManager.getConnection(url, username, password)) {
	        	  ResultSet myRs=null;
	        	  Statement mystmt=null;
	            String editbook = "SELECT * FROM books WHERE isbn = '" + searchisbn + "'";
	        mystmt = myconn.createStatement();
	          myRs = mystmt.executeQuery(editbook);
	            String editedtitle;
	            String editedauthor;
	            String editedisbn;
	        
	            if (myRs.next()) {
	             
	                editedtitle = titleid.getText();
	                editedauthor = authorid.getText();
	                editedisbn = isbnid.getText(); 
	                String updatebook = "update books set title = '" + editedtitle + "', author = '" + editedauthor + "', isbn = '" + editedisbn + "' WHERE isbn = '" + searchisbn + "'";
	                 mystmt.executeUpdate(updatebook);

	              
	            } else {
	             
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setContentText("Book not found");
	                alert.showAndWait();
	            }

	            updateTableView();
	            myRs.close();
	            mystmt.close();
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        System.out.println("Error found");
	    }
	}
	  @FXML private void initialize() { titleColumn.setCellValueFactory(new
	  PropertyValueFactory<>("title")); authorColumn.setCellValueFactory(new
	  PropertyValueFactory<>("author")); isbnColumn.setCellValueFactory(new
	  PropertyValueFactory<>("isbn")); availabilityColumn.setCellValueFactory(new
	  PropertyValueFactory<>("availability")); updateTableView(); }
	 

	@FXML
	private void updateTableView() {
		try {
			ObservableList<Book> data = FXCollections.observableArrayList();
String booktitle;
String bookauthor;
String bookisbn;
		
			String url = "jdbc:mysql://localhost:3306/new_schema";
			String username = "root";
			String password = "0d37aba1";
			ResultSet myrs=null;
			Connection myconn = DriverManager.getConnection(url, username, password);
			String update = "select * from books";
			Statement stmt = myconn.createStatement();
			 myrs = stmt.executeQuery(update);
			while (myrs.next()) {
				booktitle = myrs.getString("title");
				bookauthor = myrs.getString("author");
				bookisbn = myrs.getString("isbn");
				boolean avail = myrs.getBoolean("availability");
				data.add(new Book(booktitle, bookauthor, bookisbn, avail));
			}

			myrs.close();
			stmt.close();
			myconn.close();			bookTableView.setItems(data);

		} catch (SQLException e) {
			System.out.println("Error found");
		}
	}

}
