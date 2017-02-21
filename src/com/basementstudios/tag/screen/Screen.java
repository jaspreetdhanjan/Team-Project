package com.basementstudios.tag.screen;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.ScreenManager;
import com.basementstudios.tag.graphics.Bitmap;

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
			renderHud(bm, 8, yOffs + 8);
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
	protected void renderHud(Bitmap bm, int xStart, int yStart) {
	}

	public final void exit() {
		screenManager.setScreen(new ExitScreen());
	}

	public boolean isLive() {
		return false;
	}
}