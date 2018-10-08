package se.cth.minges.spokennumbers.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import se.cth.minges.spokennumbers.control.Controller;
import se.cth.minges.spokennumbers.core.Model;
import se.cth.minges.spokennumbers.core.SoundPlayer;
import se.cth.minges.spokennumbers.core.StringConstants;

/**
 * The view which shows/represents the data 
 * provided by the model.
 * @author Florian Minges
 */
public class MainWindow extends JFrame implements ViewI {
	
	public static final Dimension WINDOW_SIZE = new Dimension(800, 600);
	
	private Model model;
	private Controller controller; //reference to the controller, do we need that?
	
	private JSpinner msSpinner;
	private JComboBox documentBox;
	public static JCheckBox countDownCheck;
	public static JCheckBox abcCountDownCheck;
	public static JCheckBox flashNumbersCheck;
	private JButton startStopButton, showKeyButton;
	private List<JTextField> answerGrid;
	private JPanel mainPanel;
	private String currentMainPanel;
	private JLabel flashNumberLabel;
	
	private JMenuItem generateComponent;
	private JMenuItem binaryComponent;
	
	private final int ROWS = 33;
	private final int COLS = 30;
	
	private final String ANSWER_GRID = "Answer Grid";
	private final String FLASH_NUMBERS = "Flash Numbers";

	
	public MainWindow(Model model, String windowName) {
		initFrame(windowName);
		this.model = model;
		createGUI();
		this.controller = new Controller(this.model, this);
	}
	
	public MainWindow(Model model, Controller controller, String windowName) {
		initFrame(windowName);
		this.model = model;
		createGUI();
		this.controller = controller;
	}
	
	public void initFrame(String windowName) {
		this.setTitle(windowName);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(MainWindow.WINDOW_SIZE);
		this.setLocationRelativeTo(null);
	}
	
	public void createMenuBar() {
		//create components
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("File");
		this.generateComponent = new JMenuItem("Generate numbers...");
		this.binaryComponent = new JMenuItem("Generate binary...");
		JMenuItem exit = new JMenuItem("Exit program");
		JSeparator separator = new JSeparator();
		
		//initialize components
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menu.add(this.generateComponent);
		menu.add(this.binaryComponent);
		menu.add(separator);
		menu.add(exit);
		bar.add(menu);

		this.setJMenuBar(bar);
	}
	
	/**
	 * Creates the GUI-components and adds them to the
	 * main panel.
	 */
	public void createGUI() {
		setLayout(new BorderLayout());
		createMenuBar();
		this.getContentPane().add(createMenuPanel(), BorderLayout.WEST);
		this.getContentPane().add(createMainPanel(), BorderLayout.CENTER);
		//this.getContentPane().add(createAnswerGrid(), BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
		
	}
	
	public JPanel createMainPanel() {
		this.mainPanel = new JPanel(new CardLayout());
		this.mainPanel.add(createAnswerGrid(), ANSWER_GRID);
		this.mainPanel.add(createFlashNumbersDisplay(), FLASH_NUMBERS);
		this.currentMainPanel = ANSWER_GRID;
		return this.mainPanel;
	}
	
	public void switchMainPanel() {
		CardLayout cardLayout = (CardLayout)(this.mainPanel.getLayout());
		this.currentMainPanel = (this.currentMainPanel.equals(ANSWER_GRID)) ? FLASH_NUMBERS : ANSWER_GRID;
		cardLayout.show(this.mainPanel, this.currentMainPanel);
	}
	
	/** 
	 * Creates the menu panel on the (currently) left side. 
	 * @return the menu panel. 
	 */
	public JPanel createMenuPanel() {
		JPanel panel = new JPanel(new BorderLayout()); //(2,1, 20, 0));
		JPanel bottom = createButtonPanel();
		JPanel upper = createSettingsPanel(1000);
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		JPanel paddingPanel = new JPanel(new BorderLayout());
		
		paddingPanel.setPreferredSize(new Dimension(10,10));
		paddingPanel.setSize(10, 10);
		
		panel.add(upper, BorderLayout.PAGE_START);
		panel.add(bottom, BorderLayout.LINE_START);
		//panel.add(paddingPanel);
		
		return panel;
	}
	
	/** 
	 * Creates the panel with the buttons. 
	 * @return the panel with the buttons.
	 */
	public JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.setBorder(BorderFactory.createEmptyBorder(20,5,10,5));
		
		//create buttons
		this.startStopButton = new JButton(StringConstants.START);
		this.showKeyButton = new JButton(StringConstants.SHOW_KEY);
		
		//make one button invisible for now
		this.showKeyButton.setVisible(false);
		
		//set action commands
		this.startStopButton.setActionCommand(StringConstants.START);
		this.showKeyButton.setActionCommand(StringConstants.SHOW_KEY);
		
		//add buttons to panel
		panel.add(this.startStopButton);
		panel.add(this.showKeyButton);
		
		return panel;
	}
	
	/** 
	 * Creates the panel with the setting-components, ie
	 * spinners for amount of numbers, and the time interval.
	 * @param preset_ms The default time interval.
	 * @return the panel with the setting-componnents.
	 */
	public JPanel createSettingsPanel(int preset_ms) {
		//create panels
		JPanel panel = new JPanel(new GridLayout(3,1, 10, 10));
		JPanel msPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		JPanel numbersPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		//create labels
		JLabel msLabel = new JLabel("Time in milliseconds:");
		JLabel numbersLabel = new JLabel("Choose file:");

		//create spinners
		SpinnerModel msModel = new SpinnerNumberModel(preset_ms, 500, 3000, 50);
		this.msSpinner = new JSpinner(msModel);
		this.documentBox = new JComboBox(getExcelDocuments());
		this.documentBox.setSelectedIndex(0);
		
		//add to panel
		msPanel.add(msLabel);
		msPanel.add(this.msSpinner);
		numbersPanel.add(numbersLabel);
		numbersPanel.add(this.documentBox);
		panel.add(msPanel);
		panel.add(numbersPanel);
		
		addCountDownCheck(panel);

		return panel;
	}
	
	private void addCountDownCheck(JPanel panel) {
		JPanel tmpPanel = new JPanel(new GridLayout(3,1,10,10));
		flashNumbersCheck = new JCheckBox("Spoken + Flash Numbers");
		flashNumbersCheck.setSelected(false);
		
		countDownCheck = new JCheckBox("Countdown:   3  2  1");
		countDownCheck.setSelected(true);
		
		abcCountDownCheck = new JCheckBox("Countdown:   A  B  C");
		abcCountDownCheck.setSelected(true);
		
		tmpPanel.add(flashNumbersCheck);
		tmpPanel.add(countDownCheck);
		tmpPanel.add(abcCountDownCheck);
		panel.add(tmpPanel);
//		JLabel label = new JLabel("\t\t\t");
//		JLabel label2 = new JLabel("");
//		tmpPanel.add(label, BorderLayout.WEST);
//		tmpPanel.add(countDownCheck, BorderLayout.CENTER);
//		tmpPanel.add(label2, BorderLayout.EAST);
//		panel.add(tmpPanel);
	}

	private Vector<String> getExcelDocuments() {
		File folder = new File(StringConstants.EXCEL_ROOT_DIR);
		
		File[] listOfFiles = folder.listFiles();
		Vector<String> strings = new Vector<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith("xls")) {
				strings.add(listOfFiles[i].getName());
			}
		}
		
		this.startStopButton.setEnabled(strings.size() != 0);
		if (strings.size() == 0) {
			strings.add("No generated files found");
		} 
		
		return strings;
	}
	
	public void updateComboBoxModel() {
		this.documentBox.setModel(new DefaultComboBoxModel(getExcelDocuments()));
	}
	
	/**
	 * Creates a grid of cells and enables/disables
	 * a certain amount of it.
	 * @param nbrOfIntegers the number of cells to enable.
	 * @return the answer grid.
	 */
	public JComponent createFlashNumbersDisplay() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
		Font font = new Font("Tahoma", Font.BOLD, 96);
		this.flashNumberLabel = new JLabel("-");
		this.flashNumberLabel.setFont(font);
		this.flashNumberLabel.setHorizontalAlignment( SwingConstants.CENTER );
		panel.add(this.flashNumberLabel);
		
		return panel;
	}
	
	public void setFlashNumber(String textToFlash) {
		this.flashNumberLabel.setText(textToFlash);
	}
	
	/**
	 * Creates the grid used for input and returns it.
	 * @return the answer grid.
	 */
	public JComponent createAnswerGrid() {
		return createAnswerGrid(100);
	}
	
	/**
	 * Creates a grid of cells and enables/disables
	 * a certain amount of it.
	 * @param nbrOfIntegers the number of cells to enable.
	 * @return the answer grid.
	 */
	public JComponent createAnswerGrid(int nbrOfIntegers) {
		List<JTextField> list = new LinkedList<JTextField>();
		JPanel panel = new JPanel(new GridLayout(this.ROWS + 1, this.COLS + 1));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
		
		//specify font
		Font font = new Font("Tahoma", Font.ITALIC, 10);
		
		for (int j = 1; j <= this.COLS + 1; j++) {
			JLabel label = new JLabel(j % 5 == 0 ? j + "" : "");
			label.setFont(font);
			label.setHorizontalAlignment( SwingConstants.CENTER );
			panel.add(label);
		}
		
		int nbrOfCells = (this.COLS + 1) * this.ROWS;
		for (int i = 0; i < nbrOfCells; i++) {
			if ((i + 1) % (this.COLS + 1) == 0) {
				int helpNumber = this.COLS * (1 + (i / (this.COLS + 1)));
				JLabel label = new JLabel(helpNumber + "");
				label.setFont(font);
				panel.add(label);
				continue; 
			}
			
		    JTextField textField = new JTextField();
		    textField.setDocument(new JTextFieldLimit(1));
		    textField.setHorizontalAlignment(JTextField.CENTER);
		    
		    if (i > nbrOfIntegers) {
		    	textField.setEnabled(false);
		    	textField.setBackground(Color.GRAY);
		    }
		    
		    textField.setMaximumSize(new Dimension(15,40));
		    textField.setMinimumSize(new Dimension(12,33));
		    panel.add(textField);
		    list.add(textField); //store the textfields as well
		}
		
		this.answerGrid = list;
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setMaximumSize(new Dimension(640, 420));
		scrollPane.setPreferredSize(new Dimension(640, 420));
		scrollPane.setSize(new Dimension(640, 420));
		return scrollPane;
	}
	
	/** Enables a certain amount of cells in the grid. */
	public void enableAnswerGrid(int enabledFields) {
		for (int i = 0; i < this.answerGrid.size(); i++) {
			this.answerGrid.get(i).setEnabled(i < enabledFields ? 
					true : false);
			this.answerGrid.get(i).setBackground(i < enabledFields ? 
					Color.white : Color.gray);
			if (i % 5 == 4 && i < enabledFields)
				this.answerGrid.get(i).setBackground(Color.lightGray);
				
		}
	}
	
	/**
	 * Colors the grid, depending on if marked correct or not.
	 * @param correctAnswers
	 */
	public void evaluateGrid(List<Boolean> correctAnswers) {
		for (int i = 0; i < correctAnswers.size(); i++) {
			JTextField textField = this.answerGrid.get(i);
			Color color = correctAnswers.get(i) ? Color.GREEN : Color.RED;
			textField.setBackground(color);
		}
	}
	
	/**
	 * Shows the correct answers if true.
	 * @param showAnswer
	 */
	public void showKeyGrid(boolean showAnswer) {
		List<Integer> list = showAnswer ? this.model.getNumbers() : 
											this.model.getUserAnswer();
		for (int i = 0; i < this.model.getCount(); i++) {
			this.answerGrid.get(i).setText(list.get(i) + "");
		}
		
		for (int j = this.model.getCount(); j < this.answerGrid.size(); j++) {
			this.answerGrid.get(j).setText("");
		}
		
		this.showKeyButton.setActionCommand(showAnswer ? StringConstants.SHOW_USER_INPUT : 
															StringConstants.SHOW_KEY);
	}
	
	public void clearSheet() {
		for (int i = 0; i < this.answerGrid.size(); i++) {
			this.answerGrid.get(i).setText("");
		}
		enableAnswerGrid(0);
	}
	
	public void addStartStopActionListener(ActionListener a) {
		this.startStopButton.addActionListener(a);
	}
	
	public void addShowAnswerActionListener(ActionListener a) {
		this.showKeyButton.addActionListener(a);
	}
	
	public void addAnswerGridFocusListener(FocusAdapter fa) {
		for (JTextField textField : this.answerGrid) {
			textField.addFocusListener(fa);
		}
	}
	
	public void addGenerateNumbersActionListener(ActionListener a) {
		this.generateComponent.addActionListener(a);
	}
	
	public void addBinaryNumbersActionListener(ActionListener a) {
		this.binaryComponent.addActionListener(a);
	}
	
	public void setShowKeyButtonVisible(boolean visible) {
		this.showKeyButton.setVisible(visible);
	}
	
	public int getTimeInterval() {
		return (Integer) this.msSpinner.getValue();
	}
	
	public String getSelectedFile() {
		return this.documentBox.getSelectedItem().toString();
	}
	
	public List<Integer> getAnswers() {
		List<Integer> list = new LinkedList<Integer>();
		for (Iterator<JTextField> iterator = answerGrid.iterator(); iterator.hasNext();) {
			try {
				Integer answer = Integer.parseInt(iterator.next().getText());
				list.add(answer);
			} catch (NumberFormatException nfe) {
				list.add(null);
			}
		}
		
		return list;
	}
	
	public List<JTextField> getAnswerGrid() {
		return this.answerGrid;
	}
	
	public void enableComboBox(boolean enable) {
		this.documentBox.setEnabled(enable);
	}
	
	public String getExcelFile() {
		return this.documentBox.getSelectedItem().toString();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//why we don't use this?
		if (o instanceof SoundPlayer) {
			SoundPlayer player = (SoundPlayer) o;
			int nNumbersSeen = flashNumbersCheck.isSelected() ? player.getCount() * 2 : player.getCount();
			enableAnswerGrid(nNumbersSeen);
			
			if (arg != null) {
				if (arg.equals(SoundPlayer.DONE)) {
					// fake push stop button
					//startStopButton.doClick();
				}
			}
			//setFlashNumber(String.valueOf(player.getCount()));
//			if (player.getCount() == model.numbers) {
//				this.startStopButton.doClick();
//			}
		} else if (o instanceof Model) {
			if (arg != null) {
				setFlashNumber(arg.toString());
			}
		}
	}

	@Override
	public void setStartStopButtonCommand(String s) {
		this.startStopButton.setText(s);
		this.startStopButton.setActionCommand(s);
		
	}

	@Override
	public void setShowKeyButtonCommand(String s) {
		this.showKeyButton.setText(s);
		this.showKeyButton.setActionCommand(s);	
	}

}
