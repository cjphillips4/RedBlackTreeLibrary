// --== CS400 File Header Information ==--
// Name: Justin Mun Chung Seng
// Email: jseng3@wisc.edu
// Team: Red
// Group: HF
// TA: Hang
// Lecturer: Florian, Gary
// Notes to Grader: <optional extra notes>

import java.io.Reader;
import java.util.Scanner;

public class FrontEndLibrary {

	static Backend library;
	
	public void title() { //Allows the user to search up a book and choose to check in/out
		Scanner reader = new Scanner(System.in);
    	boolean run2 = true;
		do {
        	System.out.println("Enter Book Title:");
        	String title1 = reader.nextLine().trim();
        	if (library.lookupTitle(title1) == null) {
        		System.out.println("Please enter a valid Title.");
        		break;
        	} else {
        		System.out.println(library.lookupTitle(title1));
        		System.out.println("To return to Main Menu, enter \"b\", "
        							+ "To Checkout books, enter \"c\", "
        							+ "To Checkin books, enter \"i\"");
        		}        	
        	switch(reader.next().toLowerCase()) {
        	//Check in the searched up book
        	case "i": 
        		try {
        			library.checkIn(title1);
        		
        		run2 = false;
        		break;
        		} catch (IllegalArgumentException e) {
    				run2 = false;
    				System.out.println(e.getMessage());
    				break;
    			}
            //Check out the searched up book
        	case "c":
        		try {
        			 library.checkOut(title1);
    	    run2 = false;
    	    break;
			} catch (IllegalArgumentException e) {
				run2 = false;
				System.out.println(e.getMessage());
				break;
			}
        	    
        	//Returns to main menu
			case "b": 
				run2 = false;
				break;
			
			default:
				System.out.println("Please enter a valid command");
        	} 	
		} 	 while (run2);
	}
	
	
	public void genre() { //To return the list of books of a genre
		Scanner reader = new Scanner(System.in);
		boolean run3 = true;
		do {
			System.out.println("Enter Genre:");
			String genre = reader.nextLine();
			
			if (library.lookupGenre(genre).isEmpty()) {
			System.out.println("Please enter a valid Genre.");
			break;
			} else {
				System.out.println(library.lookupGenre(genre));
			}
			switch(reader.next().toLowerCase()) {
			//Returns to main menu
			case "b":  
				run3 = false;
				break;
			
			default:
				System.out.println("Please enter a valid command");
			}
		}	while (run3);
	}
	
	
	public void author() { //To return the list of books by an author
		Scanner reader =  new Scanner(System.in);
		boolean run4 = true;
		do {
			System.out.println("Enter Author:");
			String author = reader.nextLine();
			if(library.lookupAuthor(author).isEmpty()) {
				System.out.println("Please enter a valid Author.");
				break;
			} else {
				System.out.println(library.lookupAuthor(author));
			}
			switch(reader.next().toLowerCase()) {
			//Returns to main menu
			case "b": 
				run4 = false;
				break;
			
			default:
				System.out.println("Please enter a valid command");
			}
		}	while (run4);
	}
	
	public void list() { //To print out list of books in library
		Scanner reader =  new Scanner(System.in);
		boolean run5 = true;
		do {			
			System.out.println(library.getAllBooks()); 
			switch(reader.next().toLowerCase()) {
			//Returns to main menu
			case "b":
				run5 = false;
				break;
			
			default:
				System.out.println("Please enter a valid command");
			}
		}	while (run5);
	}
		

	public void allGenres() { //To print out list of Genres	
		Scanner reader =  new Scanner(System.in);
		boolean run6 = true;
		do {
			System.out.println(library.getAllGenres());
			switch(reader.next().toLowerCase()) {
			//Returns to main menu
			case "b":
				run6 = false;
				break;
			
			default:
				System.out.println("Please enter a valid command");
			}
		}	while (run6);
	}
		

	public void allAuthors() { //To print out list of Authors
		Scanner reader =  new Scanner(System.in);
		boolean run7 = true;
		do {
			System.out.println(library.getAllAuthors());
			switch(reader.next().toLowerCase()) {
			//Returns to main menu
			case "b":
				run7 = false;
				break;
			
			default:
				System.out.println("Please enter a valid command");
			}
		}	while (run7);
	} 
	
		
	 
	
	
	public static void run(Backend input) {
		library = input;
		//System.out.println();
		FrontEndLibrary menu = new FrontEndLibrary();
		Scanner reader = new Scanner(System.in);
        boolean run = true;

        //List of commands user can choose to navigate the library
        while(run) {
        	
        	System.out.println(
        			  "**********************************************************************************************************\n"
        					+ "* Welcome to our library! Here are the commands you can use to navigate the library:\n"
        					+ "* Enter \"b\" to return to the Main menu\n"
        					+ "* Enter \"t\" to LookUp a book by Title\n"
        					+ "* Enter \"g\" to LookUp books by Genre\n"
        					+ "* Enter \"a\" to LookUp books by Author\n"
        					+ "* Enter \"l\" to see the list of books in library\n"
        					+ "* Enter \"gg\" to see the list of Genres\n"
        					+ "* Enter \"aa\" to see the list of Authors\n"
        					//+ "* Enter \"c\" to check out the book which you have searched\n"
        					//+ "* Enter \"i\" to check in the book which you have searched\n"
        					+ "* Enter \"x\" to exit the program\n"
        					+ "**********************************************************************************************************");
        	
        	switch(reader.next().toLowerCase()) {
        	      	
        	//Return a book searched up through title
        	case "t": menu.title();
        	break;
        	
        	//Returns a list of books searched up through genre
        	case "g": menu.genre();
        	break;
    
        	//Returns a list of books searched up through author
        	case "a": menu.author();
        	break;
        	
        	//Prints out list of books in library
        	case "l": menu.list();
        	break;        	
        	
        	//Returns a list of Genres
        	case "gg": menu.allGenres();
        	break;
        	
        	//Returns a list of Authors
        	case "aa": menu.allAuthors();
        	break;
        	
        	//Exit the program
        	case "x": System.out.println("Thank you for using our library!");
        	run = false;
        	break;
        	
        	default:
				System.out.println("Please enter a valid command");	
           }       
	   }
	}
}

