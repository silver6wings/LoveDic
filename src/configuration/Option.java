package configuration;

public class Option {

	public String sourcePath = "D:/Topic";
	
	static Option instance = null;
	
	public static Option sharedOption(){		
		if (instance == null) {
			instance = new Option(); 
		}		
		return instance;
	}
}
