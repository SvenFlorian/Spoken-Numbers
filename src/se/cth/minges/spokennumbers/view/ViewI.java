package se.cth.minges.spokennumbers.view;

import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.util.List;
import java.util.Observer;

import javax.swing.JTextField;

/**
 * Interface that places extra requirements
 * upon Observer-objects.
 * @author Florian Minges
 */
public interface ViewI extends Observer {
	
	/* Add listeners. */
	public void addStartStopActionListener(ActionListener a);
	public void addShowAnswerActionListener(ActionListener a);
	public void addAnswerGridFocusListener(FocusAdapter fa);
	public void addGenerateNumbersActionListener(ActionListener a);
	public void addBinaryNumbersActionListener(ActionListener a);
	
	
	/* Sets text and actioncommand of the respective button. */
	public void setStartStopButtonCommand(String s);
	public void setShowKeyButtonCommand(String s);
	
	
	/* Request input-info from the window. */
	public int getTimeInterval();
	public String getSelectedFile();
	public List<Integer> getAnswers();
	
	
	/* TODO This is BAD - should not give away reference to the grid! 
	 * However, this seems to be sparing some trouble, so I will keep 
	 * it that way for now. */
	public List<JTextField> getAnswerGrid();

	
	/* Update/alter the GUI in some way. */
	public void clearSheet();
	public void updateComboBoxModel();
	public void evaluateGrid(List<Boolean> correctAnswers);
	
	
	/* enable/disable or show/hide some components */
	public void showKeyGrid(boolean showAnswer);
	public void enableComboBox(boolean enable);
	public void setShowKeyButtonVisible(boolean visible);
	public void enableAnswerGrid(int enabledFields);

}
