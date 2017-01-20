package se.cth.minges.spokennumbers.core;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	private Clip [] clips0;
	private Clip [] clips1;
	private Clip [][] clips = {clips0, clips1};
	private static SoundManager manager;
	private int counter;
	
	public static SoundManager getInstance() {
		if (manager == null)
			manager = new SoundManager();
		return manager;
	}
	
	private SoundManager() {
		counter = 0;
		for (int i = 0; i < clips.length; i++) {
			clips[i] = new Clip[13];
		}
		loadClips();
	}
	
	public void loadClips() {
		AudioInputStream audioStream;

		for (int i = 0; i < clips.length; i++) {
			for (int j = 0; j < 13; j++) {
				try {
					audioStream = AudioSystem.getAudioInputStream(
							new File(Sounds.convert(j).getValue()));
					clips[i][j] = AudioSystem.getClip();
					clips[i][j].open(audioStream);
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Clip getClip(int number) {
		return clips[counter++ % 2][number];
	}

}
