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
		int s = 2;
		Level testLevel = new TestLevel(128 * s, 128 * s);

		options = new RedirectInterface<Level, Screen>(screenManager, "Level Selector");
		options.add(testLevel, new GameScreen(testLevel));
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
	/*
		private void renderLevelStats(Level level, Bitmap bm) {
			int xStart = 8;
			int yStart = 8;
			int pad = 12;
			int pp = 0;
			bm.drawString(level.toString(), xStart, yStart + pad * pp++, 0xffffff);
			bm.drawString("Width: " + level.getWidth(), xStart, yStart + pad * pp++, 0xffffff);
			bm.drawString("Height: " + level.getHeight(), xStart, yStart + pad * pp++, 0xffffff);
			bm.drawString("Difficulty: " + level.getDifficulty(), xStart, yStart + pad * pp++, 0xffffff);
		}*/
}