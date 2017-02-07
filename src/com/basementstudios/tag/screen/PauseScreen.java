package com.basementstudios.tag.screen;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;

/**
 * Screen representation for when the game is in a paused state.
 * 
 * @author Jaspreet Dhanjan
 */

public class PauseScreen extends Screen {
	private Screen oldScreen;

	public PauseScreen(Screen oldScreen) {
		this.oldScreen = oldScreen;
	}

	public void tick(Input input) {
		if (input.hasFocus()) {
			game.setScreen(oldScreen);
		}
	}

	public void render(Bitmap bm) {
		bm.fill(0, 0, bm.width, bm.height, 0xffffff);

		String m = "Paused";
		int xo = (Game.WIDTH - Font.instance.getCharWidth(m)) / 2;
		int yo = (Game.HEIGHT - 8) / 2;
		Font.instance.draw(bm, m, xo, yo, 0xff);
	}
}