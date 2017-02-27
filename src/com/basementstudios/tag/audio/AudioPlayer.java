package com.basementstudios.tag.audio;

import com.basementstudios.tag.resource.Audio;

/**
 * Plays an audio track.
 * 
 * @author Heston Sanctis
 * @author Jaspreet Dhanjan
 */

public class AudioPlayer {
	private static Audio soundtrack;
	
	public static void play(Audio audio) {
		Thread t = new Thread(audio);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void setSoundtrack(Audio soundtrack) {
		AudioPlayer.soundtrack = soundtrack;
	}
	
	public static void setVolume(int volume) {
		
	}
}