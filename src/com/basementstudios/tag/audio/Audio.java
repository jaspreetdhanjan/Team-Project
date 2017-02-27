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
	private FloatControl gainControl;
	private Thread runnable = new Thread(this);

	public Audio(String path, boolean loop) {
		this.path = path;
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(path));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(6.0f);
			
			if (loop) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void setGainControl(float decibels){
		float gain = gainControl.getValue() + decibels;
		if(gain <= gainControl.getMaximum() && gain >= gainControl.getMinimum())
			gainControl.setValue(gain);
	}
	
	public int getVolume(){
		double maxVal = (double) (gainControl.getMaximum() - gainControl.getValue());
		double max = (double) (gainControl.getMaximum() - gainControl.getMinimum());
		int res = (int)((maxVal/max) * 100);
		return (int)(100-res);
	}
	
	public void run() {
		clip.start();
	}

	public void stop(){
		clip.stop();
	}
	
	public String toString() {
		return path;
	}

	public Thread getRunnable() {
		return runnable;
	}
}