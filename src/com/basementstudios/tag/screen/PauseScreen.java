package com.basementstudios.tag.screen;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * Screen representation for when the game is in a paused state.
 * 
 * @author Jaspreet Dhanjan
 */

public class PauseScreen extends Screen {
	public void tick(Input input) {
		if (input.space.isClicked()) {
			screenManager.toLastScreen();
		}
	}

	public void renderScene(Bitmap bm) {
		bm.clear();
		String m = "Press space to resume!";
		int xo = (Game.WIDTH - bm.getCharWidth(m)) / 2;
		int yo = (Game.HEIGHT - 8) / 2;

		bm.drawString(m, xo, yo, 0xff);
	}
}