package se.cth.minges.spokennumbers.control;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

/**
 * A simple focuslistener, which should be an inner class.
 * @author Florian Minges
 */
public class FocusListener extends FocusAdapter {

	public void focusGained(FocusEvent e) {
		JTextField textField = (JTextField) e.getSource();
		textField.selectAll();
	}
}
