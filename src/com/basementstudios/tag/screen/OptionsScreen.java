package com.basementstudios.tag.screen;

import java.util.Map;

import com.basementstudios.tag.*;
import com.basementstudios.tag.audio.Audio;
import com.basementstudios.tag.audio.AudioPlayer;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.*;

//TODO
// FPS lock
// Scaling?
// Character selection

/**
 * Options panel allows the user to change settings within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class OptionsScreen extends Screen {
	private TitleScreen titleScreen;
	private ActionInterface<String, Action> options;
	
	//test sound
	String key;
	
	//test resolution
	String[] res = new String[]{"full screen", "1920x1080", "1280x768", "400x300"};
	String keyres;
	int index = 0;
	
	//test fps lock
	String[] caps = new String[]{"Un-Capped", "30fps", "60fps"};
	String keycap;
	int indexCap = 0;
	
	public OptionsScreen(TitleScreen titleScreen) {
		this.titleScreen = titleScreen;
	}

	public void init() {
		
		//test audio
		//http://soundimage.org/fantasy-5/
		Audio test = new Audio("/sound/Backstory_Looping.wav", true);
		AudioPlayer.play(test);
		//------
		
		
		options = new ActionInterface<String, Action>("Options");
		options.add("Select Characters", new Action() {
			public void onClick() {
				screenManager.setScreen(new CharacterSelectionScreen());
			}
		});
		
		
		keyres = "Resolution: " + res[index];
		options.add(keyres, new Action() {
			public void onClick() {
				//change resolution
			}

			@Override
			public void onLeft() {
				if(index == 0){
					index = res.length;
				}
				index--;
				String newKeyRes;
				newKeyRes = "Resolution: " + res[index];
				options.replaceEntry(keyres, newKeyRes);
				keyres = newKeyRes;
				
			}

			@Override
			public void onRight() {
				if(index == res.length-1){
					index = -1;
				}
				index++;
				String newKeyRes;
				newKeyRes = "Resolution: " + res[index];
				options.replaceEntry(keyres, newKeyRes);
				keyres = newKeyRes;
			}
		});
		
		keycap = "FPS cap: " + caps[indexCap];
		options.add(keycap, new Action() {
			@Override
			public void onLeft() {
				if(indexCap == 0){
					indexCap = caps.length;
				}
				indexCap--;
				String newKeyRes;
				newKeyRes =  "FPS cap: " + caps[indexCap];
				options.replaceEntry(keycap, newKeyRes);
				keycap = newKeyRes;
				
			}

			@Override
			public void onRight() {
				if(indexCap == caps.length-1){
					indexCap = -1;
				}
				indexCap++;
				String newKeyRes;
				newKeyRes = "FPS cap: " + caps[indexCap];
				options.replaceEntry(keycap, newKeyRes);
				keycap = newKeyRes;
			}
		});
		
		options.add("Master Volume: 100", new Action() {
			public void onClick() {
			}
		});
		
		key = "Music Volume: " + test.getVolume();
		options.add(key, new Action() {
			@Override
			public void onLeft() {
				test.setGainControl(-1.0f);
				String newKey;
				newKey = "Music Volume: " + test.getVolume();
				options.replaceEntry(key, newKey);
				key = newKey;
				
			}

			@Override
			public void onRight() {
				test.setGainControl(1.0f);
				String newKey;
				newKey = "Music Volume: " + test.getVolume();
				options.replaceEntry(key, newKey);
				key = newKey;
				
			}
		});
		
		options.add("Sound Volume: 100", new Action() {
			public void onClick() {
			}
		});
		
		//options.add("Signout", new Action() {
		//	public void onClick() {
		//	}
		//});
		
		options.add("Exit to Title Screen", new Action() {
			public void onClick() {
				screenManager.toLastScreen();
				AudioPlayer.stop(test);
			}
		});
	}
	
	public void tick(Input input) {
		options.tick(input);
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		options.render(bm);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
	}
}