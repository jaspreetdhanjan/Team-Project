package com.basementstudios.tag.screen;

import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.*;
import com.basementstudios.tag.ui.RedirectInterface;

/**
 * Chooses the level for a player, levels/ nodes are unlocked as the player progresses.
 * 
 * @author Jaspreet Dhanjan
 */

public class LevelScreen extends Screen {
	private RedirectInterface<Level, Screen> options;

	public void init() {
		Level testLevel = new TestLevel(Game.WIDTH - 50, Game.HEIGHT - 50);

		options = new RedirectInterface<Level, Screen>(screenManager, "Level Selector");
		options.add(testLevel, new GameScreen(testLevel));
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