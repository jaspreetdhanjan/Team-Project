package com.basementstudios.tag.audio;

import com.basementstudios.tag.resource.Audio;

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