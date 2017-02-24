package com.basementstudios.tag.audio;

import java.io.IOException;

import javax.sound.sampled.*;

/**
 * Loads and stores an audio file in the game. Uses a singleton pattern for each sound.
 * 
 * @author Heston Sanctis
 * @author Jaspreet Dhanjan
 */

public class Audio implements Runnable {
	private final String path;
	private Clip clip;
	private Thread runnable = new Thread(this);

	public Audio(String path, boolean loop) {
		this.path = path;

		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(path));
			clip = AudioSystem.getClip();
			clip.open(inputStream);

			if (loop) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		clip.start();
	}

	public String toString() {
		return path;
	}

	public Thread getRunnable() {
		return runnable;
	}
}