package com.basementstudios.tag.audio;

import com.basementstudios.tag.resource.Audio;

/**
 * Plays an audio track.
 * 
 * @author Heston Sanctis
 * @author Jaspreet Dhanjan
 */

public class AudioPlayer {
	public static int soundVolume = 100;
	public static int musicVolume = 100;

	public static void play(Audio audio) {
		Thread t = new Thread(audio);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void stop(Audio audio) {
		audio.stop();
	}
}