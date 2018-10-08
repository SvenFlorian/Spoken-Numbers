package se.cth.minges.spokennumbers.control;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import se.cth.minges.spokennumbers.core.FileWriterSupport;
import se.cth.minges.spokennumbers.core.Model;
import se.cth.minges.spokennumbers.core.StringConstants;
import se.cth.minges.spokennumbers.view.GenerateNumbersDialog;
import se.cth.minges.spokennumbers.view.MainWindow;
import se.cth.minges.spokennumbers.view.ViewI;
/**
 * Controls the events in this project.
 * 
 * @author Florian Minges
 * 
 */
public class Controller {

	private Model model;
	private ViewI view;
	
	private static final String NUMBERS = "Numbers";
	private static final String BINARY = "Binary";

	public Controller(Model model, ViewI view) {
		this.model = model;
		this.view = view;
		this.model.setView(this.view);
		setListeners();
	}

	public void setListeners() {
		this.view.addStartStopActionListener(getStartStopButtonListener());
		this.view.addShowAnswerActionListener(getShowAnswerButtonListener());
		this.view.addAnswerGridFocusListener(new se.cth.minges.spokennumbers.control.FocusListener());
		addGenerateNumbersActionListener();
		addFocusShiftListener();
	}

	public ActionListener getStartStopButtonListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(StringConstants.START)) {
					File file = new File(StringConstants.EXCEL_ROOT_DIR + view.getSelectedFile());
					if (!file.exists()) {
						view.updateComboBoxModel();
						return;
					}
					//update GUI
					view.setStartStopButtonCommand(StringConstants.STOP);
					view.setShowKeyButtonVisible(false);
					view.clearSheet();
					
					if (MainWindow.flashNumbersCheck.isSelected()) {
						view.switchMainPanel();
					}
					
					MainWindow.flashNumbersCheck.setEnabled(false);
					MainWindow.countDownCheck.setEnabled(false);
					MainWindow.abcCountDownCheck.setEnabled(false);
					
					//start playback
					model.startPlayback(view.getSelectedFile(), view.getTimeInterval());
					
					/*
					JFrame newFrame = new JFrame();
					JPanel panel = new JPanel(new GridBagLayout());
					JLabel label = new JLabel("Hello");
					label.setFont(label.getFont().deriveFont(64.0f));
					panel.add(label);
					panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
					//label.setHorizontalAlignment(JLabel.CENTER);
					//label.setVerticalAlignment(JLabel.CENTER);
					newFrame.getContentPane().add(panel, BorderLayout.CENTER);
					newFrame.setTitle("TEST");
					newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					newFrame.setSize(800,600);
					newFrame.setLocationRelativeTo(null);
					//newFrame.pack();
					newFrame.setVisible(true);
					*/
				}

				else if (e.getActionCommand().equals(StringConstants.STOP)) {
					
					//stop playback
					model.interruptPlayback();
					
					//update GUI
					view.setStartStopButtonCommand(StringConstants.EVALUATE);
					
					if (MainWindow.flashNumbersCheck.isSelected()) {
						view.switchMainPanel();
					}
					
				}

				else if (e.getActionCommand().equals(StringConstants.EVALUATE)) {
					
					//store user input and evaluate it
					model.setUserAnswer(view.getAnswers());
					view.evaluateGrid(model.getEvaluationSheet());
					
					//update GUI
					view.setStartStopButtonCommand(StringConstants.START);
					view.setShowKeyButtonCommand(StringConstants.SHOW_KEY);
					view.setShowKeyButtonVisible(true);
					
					MainWindow.flashNumbersCheck.setEnabled(true);
					MainWindow.countDownCheck.setEnabled(true);
					MainWindow.abcCountDownCheck.setEnabled(true);
					
					//display result
					showResultMessage();
				}

			}
		};
	}

	public void addGenerateNumbersActionListener() {
		this.view.addGenerateNumbersActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGeneratedNumbersDialog(NUMBERS);
			}
		});
		this.view.addBinaryNumbersActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGeneratedNumbersDialog(BINARY);
			}
		});
	}

	private void showResultMessage() {
		int rawScore = this.model.getRawScore();
		int correct = this.model.getCorrectScore();
		int amount = this.model.getCount();
		double percentage = (double) correct / (double) amount;
		// double rawPercentage = (double)rawScore / (double)correct;

		String user = System.getProperty("user.name");
		String str;
		if (percentage == 1.0)
			str = "Wow, that was impressive " + user + "! But maybe you " + System.getProperty("line.separator") + "should try to aim higher next time? The key to success is failure!";
		else if (percentage > 0.9)
			str = "Ahhh, man! That was close " + user
					+ "! " + System.getProperty("line.separator") + "Better luck next time! :)";
		else if (percentage > 0.6)
			str = "Keep going " + user + ". Keep going and push yourself even further! :)";
		else if (percentage > 0.3)
			str = "Do not despair " + user + ", with enough practice you will reach your goal soon enough...";
		else if (percentage > 0.1)
			str = "Every journey starts with a small step. You may" + System.getProperty("line.separator") + "not be there yet, but keep practicing!";
		else 
			str = "Well, either you didn't try, or something went terribly wrong." + System.getProperty("line.separator") + "Anyways, keep practicing " + user + "!";

		str = str + System.getProperty("line.separator") + System.getProperty("line.separator") + "Rawscore: " + rawScore + System.getProperty("line.separator") + correct + " correct of "
				+ amount + " possible.";

		str = str + System.getProperty("line.separator") + System.getProperty("line.separator") + "*Press OK to log your result, cancel if not*";

		createDialogBox(str);
	}

	public void createGeneratedNumbersDialog(String type) {
		GenerateNumbersDialog dialog = new GenerateNumbersDialog();
		int selection = dialog.show();
		if (selection == JOptionPane.OK_OPTION) {
			try {
				String fileName = dialog.getFileName();
				int amount = dialog.getAmount();
				if (type.equals(NUMBERS)) {
					model.generateNumbers(amount, fileName);
				} else if (type.equals(BINARY)) {
					model.generateBinary(amount, fileName);
				}
				view.updateComboBoxModel();
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}

	}

	private void createDialogBox(String message) {
		String comment = (String) JOptionPane.showInputDialog(new JFrame(),
				message, "Your spoken numbers score",
				JOptionPane.PLAIN_MESSAGE, null, null, "");

		// If a string was returned, say so.
		if ((comment != null)) {
			storeResult(comment);
		}
	}

	private void storeResult(String comment) {
		StringBuilder result = new StringBuilder();

		// add date
		Calendar calendar = Calendar.getInstance();
		result.append(calendar.get(Calendar.YEAR));
		result.append("-");
		int month = calendar.get(Calendar.MONTH) + 1;
		result.append(month > 9 ? month : "0" + month); // making it
														// double-digit
		result.append("-");
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		result.append(day > 9 ? day : "0" + day); // making it double-digit
		result.append(result.length() < 10 ? "\t" : "");
		// add scores
		int rawScore = this.model.getRawScore();
		int correct = this.model.getCorrectScore();
		int amount = this.model.getCount();
		result.append("\t\t");
		result.append(rawScore);
		result.append(rawScore > 9 ? "" : "\t");
		result.append("\t\t\t");
		result.append("(");
		result.append(correct);
		result.append("/");
		result.append(amount);
		result.append(")\t\t");
		result.append((correct > 9) && (amount > 99) ? "" : "\t\t"); // append additional tab if score
																   // is NOT too wide
		int time = this.view.getTimeInterval();
		result.append(time);
		result.append(" ms\t\t\t");
		result.append(time < 1000 ? "\t" : "");
		
		// add comment
		result.append(comment);

		FileWriterSupport.appendToTextFile(StringConstants.RESULTS_FILE_NAME,
				result.toString());
	}

	public ActionListener getShowAnswerButtonListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (e.getActionCommand().equals(StringConstants.SHOW_KEY)) {
					// show key
					view.showKeyGrid(true);
					view.setShowKeyButtonCommand(StringConstants.SHOW_USER_INPUT);
				}
				
				else if (e.getActionCommand().equals(StringConstants.SHOW_USER_INPUT)) {
					// show users input 
					view.showKeyGrid(false);
					view.setShowKeyButtonCommand(StringConstants.SHOW_KEY);
				}
				
			}
		};
	}

	public void addFocusShiftListener() {
		List<JTextField> answerGrid = this.view.getAnswerGrid();
		for (int i = 0; i < answerGrid.size(); i++) {
			final JTextField textField = answerGrid.get(i);
			textField.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent arg0) {
					textField.transferFocus();
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					textField.transferFocus();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
				}

			});
		}
	}

}
