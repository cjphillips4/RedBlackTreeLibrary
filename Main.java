// --== CS400 File Header Information ==--
// Name: Sicheng Mo
// Email: smo3@wisc.edu
// Team: HF: red
// Role: Integration Manager
// TA: Hang Yin
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FrontEndLibrary test=new FrontEndLibrary();
		String path = "cs400BookDatabase.csv";
		FileReader reader;
		try {
			reader = new FileReader(path);
			Backend backend = new Backend(reader);
	        test.run(backend);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return;
		}
	}

}
