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
	private ActionInterface<String, Action> options;

	public void init() {
		options = new ActionInterface<String, Action>("Options");
		options.add("Return", new Action() {
			public void onClick() {
				screenManager.toLastScreen();
			}
		});
	}

	public void tick(Input input) {
		options.inputTick(input);
	}

	public void renderScene(Bitmap bm) {
		bm.clear();
		options.render(bm);
	}
}