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
		options.add("Return to Title", new Action() {
			public void onClick() {
				screenManager.setScreen(new TitleScreen(null));
			}
		});
	}

	public void tick(Input input) {
		if (!releaseAll) {
			input.releaseAll();
			releaseAll = true;
		}

		if (input.space.isClicked()) {
			screenManager.toLastScreen();
		}

		options.inputTick(input);
	}

	public void renderScene(Bitmap bm) {
		bm.clear();

		// Effects render, leave this.
		/*
		for (int i = 0; i < bm.pixels.length; i++) {
			bm.pixels[i] = ((i + tickCount) % 256);
		}
		
		int scale = 2;
		int xBob = (int) ((Math.sin(tickCount / 10.0) * 10.0) % 50.0);
		int yBob = (int) ((Math.cos(tickCount / 10.0) * 10.0) % 50.0);
		
		String m = "Press space to resume!";
		int xo = ((Game.WIDTH - bm.getCharWidth(m)) / 2) / scale;
		int yo = Game.HEIGHT / 2;
		xo += xBob;
		yo += yBob;
		
		bm.setScale(scale, scale);
		bm.drawString(m, xo, yo, 0xffffff);
		bm.setScale(1, 1);*/

		options.render(bm);
	}
}