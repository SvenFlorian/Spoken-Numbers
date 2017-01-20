package se.cth.minges.spokennumbers;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import se.cth.minges.spokennumbers.core.Model;
import se.cth.minges.spokennumbers.view.MainWindow;

/**
 * Main entry point for this application.
 * @author Florian Minges
 */
public class SpokenNumbers {
	
	public static final String APP_NAME = "Spoken Numbers";
	
	public static void main(String[] arguments) {
		UIManager.put("TextField.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 24)));
		Model model = new Model();
		new MainWindow(model, SpokenNumbers.APP_NAME);
	}

}
