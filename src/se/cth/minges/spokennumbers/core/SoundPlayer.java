package se.cth.minges.spokennumbers.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import se.cth.minges.spokennumbers.view.MainWindow;

/**
 * Class that can play Sounds.
 * 
 * @author Florian Minges
 */
public class SoundPlayer extends Observable {

	private int playedSounds;
	private Clip[] clips = new Clip[10];
	private Clip[] clips2 = new Clip[10];
	private Clip clip;
	private SoundManager manager;
	
	public static final String DONE = "Done";

	public SoundPlayer() {
		this.playedSounds = 0;
		manager = SoundManager.getInstance();
//		loadSounds(clips);
//		loadSounds2(clips2);
	}
	
	public void playClip(Clip clip) {
		if (clip.isOpen()) {
			clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void loadSounds2(Clip[] array) {
		for (int i = 0; i < 10; i++) {
			String fileName = StringConstants.PROJECT_ROOT_DIR + System.getProperty("file.separator") +
					"resources" + System.getProperty("file.separator") + "backup" + 
					System.getProperty("file.separator") + i + ".wav";
			File file = new File(fileName);
			AudioInputStream audioIn;
			try {
				audioIn = AudioSystem.getAudioInputStream(file);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				array[i] = clip;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void loadSounds(Clip[] array) {
		for (int i = 0; i < 10; i++) {
			Sounds sound = Sounds.convert(i);
			
			// Open an audio input stream.
			File file = new File(sound.getValue());
			AudioInputStream audioIn;
			try {
				audioIn = AudioSystem.getAudioInputStream(file);
				// Get a sound clip resource.
				Clip clip = AudioSystem.getClip();

				// Open audio clip and load samples from the audio input stream.
				clip.open(audioIn);
				array[i] = clip;
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Plays a sound.
	 * 
	 * @param sound
	 *            The sound to play.
	 */
	public void play(Sounds sound) {
		// try {
		// Open an audio input stream.
		// File file = new File(sound.getValue());
		// AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
		//
		// // Get a sound clip resource.
		// Clip clip = AudioSystem.getClip();
		//
		// // Open audio clip and load samples from the audio input stream.
		// clip.open(audioIn);
		clip = playedSounds % 2 == 0 ? clips[sound.ordinal()]
				: clips2[sound.ordinal()];
//		clip = clips[sound.ordinal()];
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
		
		//
		// } catch (UnsupportedAudioFileException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (LineUnavailableException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * Can play several numbers in a given interval.
	 * 
	 * @param numbers
	 *            The numbers to play.
	 * @param delay_in_ms
	 *            The delay in ms between playing two sounds.
	 */
	public void play(List<Integer> numbers, int delay_in_ms) {
		resetCounter();
		for (int j = 3; j >= 1 && MainWindow.countDownCheck.isSelected(); j--) {
//			final int number = j;
			// Thread thread = new Thread(new Runnable() {
			// public void run() {
//			play(Sounds.convert(number));
			playClip(manager.getClip(j));
			setChanged();
			notifyObservers(j);
			// }
			// });
			// thread.start();
			try {
				Thread.sleep(delay_in_ms);
			} catch (InterruptedException ie) {
				return; // end method
			}
		}
		for (int j = 10; j < 13 && MainWindow.abcCountDownCheck.isSelected(); j++) {
			playClip(manager.getClip(j));
			String [] letters = {"A", "B", "C"};
			setChanged();
			notifyObservers(letters[j-10]);
			try {
				Thread.sleep(delay_in_ms);
			} catch (InterruptedException ie) {
				return; // end method
			}
			
		}
		
		setChanged();
		notifyObservers();

		try {
			Thread.sleep(delay_in_ms);
		} catch (InterruptedException ie) {
			return; // end method
		}
		
		int nbrOfIntegers = numbers.size();
		for (int i = 0; i < nbrOfIntegers; i++) {
			final int currentNumber = numbers.get(i);
			// Thread thread = new Thread(new Runnable() {
			// public void run() {
//			play(Sounds.convert(currentNumber));
			playClip(manager.getClip(currentNumber));
			incrementCounter();
			// }
			// });
			// thread.start();
			try {
				Thread.sleep(delay_in_ms);
			} catch (InterruptedException ie) {
				return; // end method
			}
		}

		setChanged();
		notifyObservers(SoundPlayer.DONE);
	}

	/** Returns the number of continuous played sounds. */
	public int getCount() {
		return this.playedSounds;
	}

	private void incrementCounter() {
		this.playedSounds++;
		setChanged();
		notifyObservers();
	}

	private void resetCounter() {
		this.playedSounds = 0;
		setChanged();
		notifyObservers(this.playedSounds);
	}

}
