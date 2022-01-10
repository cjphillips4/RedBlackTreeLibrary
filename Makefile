Srun: compile
	java Main


compile: clean BookInterface.class Book.class BookReaderInterface.class BookReader.class Backend.class FrontEndLibrary.class Main.class

BookInterface.class:  BookInterface.java
	javac BookInterface.java
Book.class: Book.java
	javac Book.java
BookReaderInterface.class: BookReaderInterface.java
	javac BookReaderInterface.java
BookReader.class: BookReader.java
	javac BookReader.java
Backend.class: Backend.java
	javac Backend.java
FrontEndLibrary.class: FrontEndLibrary.java
	javac FrontEndLibrary.java
Main.class: Main.java
	javac Main.java
BookReaderTest.class: BookReaderTest.java
	javac BookReaderTest.java
TestBackend.class: TestBackend.java
	javac TestBackend.java
FrontEndDeveloperTests.class: FrontEndDeveloperTests.java
	javac FrontEndDeveloperTests.java


test: testData testBackend testFrontend

testData: 
	javac -cp .:junit5.jar BookReaderTest.java	
	java -jar junit5.jar -cp . --scan-classpath -n BookReaderTest
	

testBackend:
	javac -cp .:junit5.jar TestBackend.java
	java -jar junit5.jar -cp . --scan-classpath -n TestBackend


testFrontend:
	javac -cp .:junit5.jar FrontEndDeveloperTests.java
	java -jar junit5.jar -cp . --scan-classpath -n FrontEndDeveloperTests

clean:
	$(RM) *.class
