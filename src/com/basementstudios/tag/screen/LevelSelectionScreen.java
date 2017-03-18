package com.basementstudios.tag.screen;

import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.*;
import com.basementstudios.tag.ui.RedirectInterface;

/**
 * Chooses the level for a player, levels/ nodes are unlocked as the player progresses.
 * 
 * @author Jaspreet Dhanjan
 */

public class LevelSelectionScreen extends Screen {
	private RedirectInterface<Level, GameScreen> options;

	public void init() {
		Level testLevel = new DemoLevel();
		Level easyLevel = new EasyLevel();

		options = new RedirectInterface<Level, GameScreen>(screenManager, "Level Selector");
		options.add(testLevel, new GameScreen(testLevel));
		options.add(easyLevel, new GameScreen(easyLevel));
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
		if (options.getSelected() != null) {
			renderLevelStats(options.getSelected().getLevel(), bm);
		}
	}

	private void renderLevelStats(Level level, Bitmap bm) {
		int xStart = 8;
		int yStart = 8;
		int pad = 24;
		int pp = 0;
		bm.drawString(level.toString(), xStart, yStart + pad * pp++, 0xffffff);
		pp++;
		bm.drawString("Difficulty: " + level.getDifficulty(), xStart, yStart + pad * pp++, 0xffffff);
		bm.drawString("Winning XP: " + level.getDifficulty() * 8, xStart, yStart + pad * pp++, 0xffffff);
	}
}