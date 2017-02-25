package com.basementstudios.tag.resource;

import java.io.IOException;

import javax.sound.sampled.*;

import com.basementstudios.tag.ResourceManager;

/**
 * Keeps data and plays an audio file.
 * 
 * @author Heston Sanctis
 * @author Jaspreet Dhanjan
 */

public class Audio extends Resource implements Runnable {
	private final String path;
	private final boolean loop;

	private Clip clip;
	private Thread runnable = new Thread(this);

	public Audio(ResourceManager resourceManager, String path, boolean loop) {
		super(ResourceType.AUDIO, resourceManager);
		this.path = path;
		this.loop = loop;
	}

	public void create() {
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

	public String getPath() {
		return path;
	}

	public void run() {
		clip.start();
	}

	public Thread getRunnable() {
		return runnable;
	}
}