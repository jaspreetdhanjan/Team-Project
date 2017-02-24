package com.basementstudios.tag.audio;

/**
 * Plays an audio track.
 * 
 * @author Heston Sanctis
 * @author Jaspreet Dhanjan
 */

public class AudioPlayer {
	public static synchronized void play(Audio audio) {
		audio.getRunnable().start();
	}
}