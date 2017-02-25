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

	public Audio(ResourceManager resourceManager, String path, boolean loop) {
		super(ResourceType.AUDIO, resourceManager);
		this.path = "/audio/" + path;
		this.loop = loop;
	}

	public void create() {
		try {
			clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(Audio.class.getResourceAsStream(path));
			clip.open(ais);
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
		clip.setFramePosition(0);
		clip.start();
	}
}