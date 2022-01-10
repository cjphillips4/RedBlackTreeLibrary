// --== CS400 File Header Information ==--
// Name: Andy Truong
// Email: ahtruong@wisc.edu
// Team: Red
// Role: Data Wrang'er
// TA: Hang Yin
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookReader implements BookReaderInterface{

  /**
   * Puts CSV data into a list of Book objects
   * 
   * @param inputFileReader CSV File path that was read
   * @return listofBooks List of Book objects
   */
  @Override
  public List<BookInterface> readDataSet(Reader inputFileReader) {
        
    List<BookInterface> listofBooks = new ArrayList<BookInterface>();
    
    String[] list = null;
    Book addedBook = new Book();
    try {
    String line = "";    
    BufferedReader br = new BufferedReader(inputFileReader);
    int counter = 0;
    //Reads each line of the CSV file
    while((line = br.readLine()) != null) {      
    
      //Puts each line into an array
      //Index 0 = Title, Index 1 = Author, Index 2 = Genre
      list = line.split(",");
      
      //Creates a new book and sets variables
      addedBook = new Book();
      addedBook.setTitle(list[0]);      
      addedBook.setAuthor(list[1]);
      addedBook.setGenre(list[2]);
      
      //Adds a book to the list
      listofBooks.add(addedBook);
      counter++;
      
    }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch(NullPointerException e) {
      e.printStackTrace();
    }
    //showList(listofBooks);
    return listofBooks;
  }
  
  private void showList( List<BookInterface> listofBooks) {
	for(int i =0; i<listofBooks.size();i++) {
		System.out.println(listofBooks.get(i).toString());
	}
}
  
  
}
