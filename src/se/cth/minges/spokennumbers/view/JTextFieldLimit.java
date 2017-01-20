package se.cth.minges.spokennumbers.view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Document that can limit the number of characters for
 * a JTextField by calling
 * 'textField.setDocument(new JTextFieldLimit(12))'
 * It also prevents any characters, except for numbers
 * to be written.
 * @author http://www.rgagnon.com/javadetails/java-0198.html
 */
public class JTextFieldLimit extends PlainDocument {
	private int limit;
	// optional uppercase conversion
	private boolean toUppercase = false;

	JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
		toUppercase = upper;
	}

	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException {
		if (str == null)
			return;

		if ((getLength() + str.length()) <= limit && isIntegerValue(str)) {
			if (toUppercase)
				str = str.toUpperCase();
			super.insertString(offset, str, attr);
		}
	}
	
	/** Returns true if the given string can be converted
	 *  into an integer. */
	public static boolean isIntegerValue(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
