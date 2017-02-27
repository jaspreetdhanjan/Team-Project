package com.basementstudios.tag.screen;

import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.*;

/**
 * Screen representation for when the game is in a paused state.
 * 
 * ESC enters the paused state, Space will exit.
 * 
 * @author Jaspreet Dhanjan
 */

public class PauseScreen extends Screen {
	private ActionInterface<String, Action> options;

	private boolean releaseAll;

	public void init() {
		options = new ActionInterface<String, Action>("Paused");

		options.add("Return to Game", new Action() {
			public void onClick() {
				screenManager.toLastScreen();
			}
		});

		options.add("Exit to Title", new Action() {
			public void onClick() {
				screenManager.setScreen(new TitleScreen());
			}
		});
	}

	public void tick(Input input) {
		if (!releaseAll) {
			input.releaseAll();
			releaseAll = true;
		}

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