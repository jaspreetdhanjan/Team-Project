package com.basementstudios.tag.audio;

import java.util.ArrayList;

public class AudioPlayer {
	
	private ArrayList<Audio> tracks;
	private Thread thread;

	public AudioPlayer(Audio ... path) {
		tracks = new ArrayList<Audio>();
		addAudio(path);
	}
	
	public AudioPlayer(){
		tracks = new ArrayList<Audio>();
	}
	
	public void addAudio(Audio ... audio){
		for(int i = 0; i < audio.length; i++){
			tracks.add(audio[i]);
		}
	}
	
	public void removeAudio(){
	}
	
	public synchronized void playTrack(int index) {
		Audio track = tracks.get(index);
		thread = new Thread(track);
		thread.setName(track.toString());
		thread.start();
	}
	
}