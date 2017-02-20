package com.basementstudios.tag.screen;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.ScreenManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;

/**
 * A base class that localises all screen states.
 * 
 * @author Jaspreet Dhanjan
 */

public class Screen {
	private static final int HUD_HEIGHT = 100;
	
	protected ScreenManager screenManager;

	public final void init(ScreenManager screenManager) {
		this.screenManager = screenManager;
		init();
	}

	public void init() {
	}

	public void tick(Input input) {
	}

	public final void render(Bitmap bm) {
		renderScene(bm);

		if (this instanceof GameScreen) {
			int yOffs = Game.HEIGHT - HUD_HEIGHT;
			bm.fill(0, yOffs, bm.width, bm.height, 0);
			renderHud(bm, Font.getInstance(), 8, yOffs + 8);
		}
	}

	/**
	 * The "scene" is actually anything within the screen.
	 */
	protected void renderScene(Bitmap bm) {
	}

	/**
	 * The "hud" is the content displayed in the box in the gamescreen.
	 */
	protected void renderHud(Bitmap bm, Font font, int xStart, int yStart) {
	}
	
	public void renderSelectables(Font font, Bitmap bm, String[] options, int selected) {
		for (int i = 0; i < options.length; i++) {
			String option = options[i];
			if (i == selected) option = "-> " + option;
			int xo = (Game.WIDTH - font.getCharWidth(option)) / 2;
			int yo = 128 + i * 20;
			font.draw(bm, option, xo, yo, 0xffffff);
		}
	}

	public final void exit() {
		System.exit(1);
	}

	public boolean isLive() {
		return false;
	}
}