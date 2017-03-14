package com.basementstudios.tag.resource;

import com.basementstudios.tag.ResourceManager;

import javax.sound.sampled.*;
import java.io.IOException;

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

	public Audio(ResourceManager resourceManager, String filename, boolean loop) {
		super(ResourceType.AUDIO, resourceManager);
		this.path = "/audio/" + filename;
		this.loop = loop;
	}

	public void create() {
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
			AudioFormat format = inputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
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
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		clip.stop();
	}
}