package se.cth.minges.spokennumbers.view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * Class that provides a dialog for generating numbers.
 * @author Florian Minges
 */
public class GenerateNumbersDialog {
	private JSpinner spinner;
	private JTextField textField;

	public GenerateNumbersDialog() {
	}
	
	public int show() {
		return JOptionPane.showConfirmDialog(null, createInterface(), "Generate Numbers", 
		          JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	private JPanel createInterface() {
		// create panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));

		// create components
		JLabel numbers_string = new JLabel("How many numbers would you like to generate?");
		JLabel store_string = new JLabel("What do you want to call the file?");
		SpinnerModel numbersModel = new SpinnerNumberModel(100, 10, 990, 20);
		this.spinner = new JSpinner(numbersModel);
		this.textField = new JTextField("test");

		// add them to the panel
		panel.add(numbers_string);
		panel.add(this.spinner);
		panel.add(store_string);
		panel.add(this.textField);

		return panel;
	}

	public int getAmount() {

		Object o = this.spinner.getValue();
		int number = 100;
		try {
			number = Integer.valueOf(o.toString());
		} catch (ClassCastException cce) {
			cce.printStackTrace();
		}

		return number;
	}

	public String getFileName() {
		return this.textField.getText();
	}

}
