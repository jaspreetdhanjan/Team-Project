package com.basementstudios.tag.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Audio implements Runnable{
	
	private Clip clip;
	private AudioInputStream inputStream;
	private Thread thread;
	
	public Audio(String path) throws Exception{
        	this.inputStream = AudioSystem.getAudioInputStream(
        		this.getClass().getResourceAsStream(path));
	}
	
	public synchronized void play() {
		this.thread = new Thread(this);
		this.thread.setName(this.toString());
		this.thread.start();
	}
	
	@Override
	public void run() {
		try {
			this.clip = AudioSystem.getClip();
			this.clip.open(this.inputStream);
			clip.start(); 
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
