package clueGame;

public class BadConfigFormatException extends Exception{
	
	// default constructor
	public BadConfigFormatException() {
		
	}
	
	// constructor
	public BadConfigFormatException(String arg0) {
		super(arg0);
		
	}

	// toString override function
	@Override
	public String toString() {
		return "BadConfigFormatException []";
	}

}
