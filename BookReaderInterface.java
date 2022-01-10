// --== CS400 File Header Information ==--
// Name: Andy Truong
// Email: ahtruong@wisc.edu
// Team: Red
// Role: Data Wrangler
// TA: Hang Yin
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.List;
import java.io.Reader;

public interface BookReaderInterface {
  
  public List<BookInterface> readDataSet(Reader inputFileReader);  

}
