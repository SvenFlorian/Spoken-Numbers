package se.cth.minges.spokennumbers.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import se.cth.minges.spokennumbers.core.excel.ReadExcel;
import se.cth.minges.spokennumbers.core.excel.WriteExcel;

/**
 * Class representing this projects data.
 * @author Florian Minges
 */
public class Model extends Observable {
	
	private List<Integer> numbers;
	private List<Integer> user_answer;
	private SoundPlayer soundPlayer;
	private Thread playback_thread;

	public Model() {
		this.soundPlayer = new SoundPlayer();
	}
	
	/**
	 * Starts to play spoken numbers in a background thread.
	 * @param amount The amount of numbers spoken.
	 * @param delay_in_ms The intervall pace in ms.
	 */
	public void startPlayback(String fileName, final int delay_in_ms) {
		readNumbersIntoMemory(fileName);
		this.playback_thread = new Thread(new Runnable() {
			public void run() {
				soundPlayer.play(numbers, delay_in_ms);
			}
		});
		this.playback_thread.start();
		
	}
	
	/**
	 * If there is currently a background thread operating,
	 * ie playing spoken numbers, we interrupt it.
	 */
	public void interruptPlayback() {
		if (this.playback_thread != null)
			this.playback_thread.interrupt();
	}
	
	/**
	 * Generates a certain amount of random numbers and stores
	 * them in an instance variable list.
	 * @param amountOfNumbers The amount of random numbers to generate.
	 * @param fileName The file to store the numbers in. The path to
	 * provide is relative to the root-folder for the generated excel-sheets.
	 */
	public void generateNumbers(int amountOfNumbers, String fileName) {
		List<Integer> list = new LinkedList<Integer>();
		Random generator = new Random();
		for (int i = 0; i < amountOfNumbers; i++) {
			list.add(generator.nextInt(10)); //generates a random number from 0 to 9
		}
		
		WriteExcel excel = new WriteExcel();
		try {
			StringBuilder filePath = new StringBuilder(StringConstants.EXCEL_ROOT_DIR);
			filePath.append(fileName);
			filePath.append(".xls");
			excel.writeNumbersToFile(filePath.toString(), list);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO Refactor with above method
	public void generateBinary(int amountOfNumbers, String fileName) {
		List<Integer> list = new LinkedList<Integer>();
		Random generator = new Random();
		for (int i = 0; i < amountOfNumbers; i++) {
			list.add(generator.nextInt(2)); //generates a random number from 0 to 9
		}
		
		WriteExcel excel = new WriteExcel();
		try {
			StringBuilder filePath = new StringBuilder(StringConstants.EXCEL_ROOT_DIR);
			filePath.append(fileName);
			filePath.append(".xls");
			excel.writeNumbersToFile(filePath.toString(), list);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readNumbersIntoMemory(String fileName) {
		ReadExcel excel = new ReadExcel();
		try {
			//possible to pass the name of a file, instead of the absolute path
			this.numbers = fileName.contains(System.getProperty("file.separator")) ? excel.readNumbers(fileName) :
						excel.readNumbers(StringConstants.EXCEL_ROOT_DIR + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < 100; i++) {
				list.add(i % 10);
			}
			this.numbers = list;
		}

	}
	
	/** Returns the stored generated random numbers. */
	public List<Integer> getNumbers(String fileName) {
		readNumbersIntoMemory(fileName);
		return this.numbers;
	}
	
	/** Returns the stored numbers. */
	public List<Integer> getNumbers() {
		return this.numbers;
	}
	
	/** Returns the users input, if it has been saved. */
	public List<Integer> getUserAnswer() {
		return this.user_answer; //null if not initiated
	}
	
	/** Returns which answers from the user are correct,
	 *  and which are not. */
	public List<Boolean> getEvaluationSheet() {
		List<Boolean> sheet = new ArrayList<Boolean>();
		for (int i = 0; i < getCount(); i++) {
			sheet.add(i, this.numbers.get(i) == this.user_answer.get(i));
		}
		return sheet;
	}
	
	/** Returns the number of correct answers until the
	 *  first mistake, ie the raw score. */
	public int getRawScore() {
		List<Boolean> list = getEvaluationSheet();
		int rawScore = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i))
				rawScore++;
			else
				break;
		}
		return rawScore;
	}
	
	/** Returns the number of correct answers. */
	public int getCorrectScore() {
		List<Boolean> list = getEvaluationSheet();
		int score = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i))
				score++;
		}
		return score;
	}
	
	/** Returns the number of played sounds. */
	public int getCount() {
		return this.soundPlayer.getCount();
	}
	
	/** 
	 * Stores the users input.
	 * @param list The users input.
	 */
	public void setUserAnswer(List<Integer> list) {
		this.user_answer = list;
	}
	
	//core-classes shall not know of ANY view-classes!
//	/**
//	 * This adds the possibility to have more requirements 
//	 * for the view who wants to observe this model. Though
//	 * it might sometimes be better to override the 
//	 * addObserver()-method, since Views can still call on
//	 * that method for now. However, it is not always that
//	 * all the observers need special functionality. :)
//	 * @param view An observer with some extra required
//	 * functionality.
//	 */
//	public void setView(ViewI view) {
//		this.addObserver(view);
//		this.soundPlayer.addObserver(view);
//	}
	
	public void setView(Observer observer) {
		this.addObserver(observer);
		this.soundPlayer.addObserver(observer);
	}
}
