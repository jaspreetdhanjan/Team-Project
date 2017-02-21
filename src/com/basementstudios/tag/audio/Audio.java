package com.basementstudios.tag.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 * Basic audio player implementation.
 * 
 * @author Heston Sanctis
 */

public class Audio implements Runnable {
	private AudioInputStream inputStream;

	private Clip clip;
	private Thread thread;

	public Audio(String path) throws Exception {
		inputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(path));
	}

	public synchronized void play() {
		thread = new Thread(this);
		thread.setName(this.toString());
		thread.start();
	}

	public void run() {
		try {
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.start();
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}
}