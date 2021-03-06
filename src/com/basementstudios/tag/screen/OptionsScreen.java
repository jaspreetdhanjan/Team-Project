package com.basementstudios.tag.screen;

import com.basementstudios.tag.*;
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
		options.add("Exit to Title", new Action() {
			public void onClick() {
				screenManager.setScreen(titleScreen);
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