package com.basementstudios.tag.screen;

import com.basementstudios.tag.*;
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

	public OptionsScreen(TitleScreen titleScreen) {
		this.titleScreen = titleScreen;
	}
	
	public void init() {
		options = new ActionInterface<String, Action>("Options");
		options.add("Select Characters", new Action() {
			public void onHovered() {
			}

			public void onClick() {
				screenManager.setScreen(new CharacterSelectionScreen());
			}
		});
		options.add("Quit", new Action() {
			public void onClick() {
				screenManager.setScreen(titleScreen);
			}

			public void onHovered() {
			}
		});
	}

	public void tick(Input input) {
		options.inputTick(input);
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		options.render(bm);
	}

	public void renderHud(Bitmap bm) {
	}
}