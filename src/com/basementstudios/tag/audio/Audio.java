package com.basementstudios.tag.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio implements Runnable{
	
	private AudioInputStream inputStream;
	private Clip clip;
	private String path;
	
	public Audio(String path, boolean loop){
		this.path = path;
		setAudioInputStream(path, loop);
	}

	public void setAudioInputStream(String path, boolean loop){
		try {
			inputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(path));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		clip.start();
	}
	
	@Override
	public String toString() {
		return this.path;
	}

}

