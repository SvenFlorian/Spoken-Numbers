package se.cth.minges.spokennumbers.core;

/**
 * Some string constants.
 * @author Florian Minges
 */
public interface StringConstants {
	
	public final String START = "Start";
	public final String STOP = "Start recall";
	public final String EVALUATE = "Evaluate";
	public final String SHOW_KEY = "Show key";
	public final String SHOW_USER_INPUT = "Show user input";
	
	public final String PROJECT_ROOT_DIR = System.getProperty("user.dir");
	public final String EXCEL_ROOT_DIR = System.getProperty("user.dir") + 
			System.getProperty("file.separator") + "generated_sheets" + 
			System.getProperty("file.separator");
	
	public final String RESULTS_FILE_NAME = System.getProperty("user.dir") + 
			System.getProperty("file.separator") + "results.txt";
	
	public static final String SOUNDS_ROOT_DIR = System.getProperty("user.dir") + 
			System.getProperty("file.separator") + "resources" + System.getProperty("file.separator"); 
	
	public static final String SOUND_SUFFIX = ".wav";
}
