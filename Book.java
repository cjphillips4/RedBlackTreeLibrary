// --== CS400 File Header Information ==--
// Name: Andy Truong
// Email: ahtruong@wisc.edu
// Team: Red
// Role: Data Wrang'er
// TA: Hang Yin
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public class Book implements BookInterface {

  private String title;
  private String author;
  private boolean isCheckedOut;
  private String genre;

  public Book() {}

  public Book (String title,String genre, String author)
  {
	 this.title=title;
	 this.author=author;
	 this.genre=genre;
	 isCheckedOut=false;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void checkOut()
  {
	  isCheckedOut=true;
  }

  public void checkIn()
  {
	  isCheckedOut=false;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public String getAuthor() {
    return author;
  }

  @Override
  public boolean checkedOut() {
    return isCheckedOut;
  }

@Override
  public boolean isCheckedOut() {
	return isCheckedOut;
}

  @Override
  public String getGenre() {
    return genre;
  }

  public int compareTo(BookInterface book) {
    return this.title.compareTo(book.getTitle());
  }

  public String toString() {
	  String str = "";
	    str = str.concat("[" + this.title + ", ");
	    str = str.concat("" + this.author + ", ");
	    str = str.concat("" + this.isCheckedOut + ", ");
	    str = str.concat("" + this.genre + "]");
	    return str;
  }

}