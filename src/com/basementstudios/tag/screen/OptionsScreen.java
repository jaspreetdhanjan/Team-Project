package com.basementstudios.tag.screen;

import com.basementstudios.network.Token;

import com.basementstudios.tag.*;
import com.basementstudios.tag.audio.AudioPlayer;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.*;

/**
 * Options panel allows the user to change settings within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class OptionsScreen extends Screen {
	private TitleScreen titleScreen;
	private ActionInterface<String, Action> options;

	public OptionsScreen(TitleScreen titleScreen) {
		this.titleScreen = titleScreen;
	}

	// TODO: Stuff to add: FPS lock, scaling, logout, volume
	public void init() {
		options = new ActionInterface<String, Action>("Options");
		options.add("Select Characters", new Action() {
			public void onClick() {
				screenManager.setScreen(new CharacterSelectionScreen());
			}
		});

		String key = getSoundString();
		options.add(key, new Action() {
			public void onLeft() {
				if (AudioPlayer.soundVolume > 0) AudioPlayer.soundVolume--;
				options.replaceOption(key, getSoundString());
			}

			public void onRight() {
				if (AudioPlayer.soundVolume < 100) AudioPlayer.soundVolume++;
				options.replaceOption(key, getSoundString());
			}
		});

		options.add("Music volume: " + AudioPlayer.musicVolume, new Action() {
			public void onLeft() {
				if (AudioPlayer.musicVolume > 0) AudioPlayer.musicVolume--;
			}

			public void onRight() {
				if (AudioPlayer.musicVolume < 100) AudioPlayer.musicVolume++;
			}
		});

		options.add("Logout", new Action() {
			public void onClick() {
				Token.remove();
				exit();
			}
		});

		options.add("Exit to Title", new Action() {
			public void onClick() {
				screenManager.setScreen(titleScreen);
			}
		});
	}

	private String getSoundString() {
		return "Sound volume: " + AudioPlayer.soundVolume;
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