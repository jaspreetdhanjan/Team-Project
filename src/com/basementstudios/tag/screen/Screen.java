package com.basementstudios.tag.screen;

import com.basementstudios.tag.Input;
import com.basementstudios.tag.ScreenManager;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * A base class that localises all screen states.
 * 
 * @author Jaspreet Dhanjan
 */

public class Screen {
	protected ScreenManager screenManager;

	public final void init(ScreenManager screenManager) {
		this.screenManager = screenManager;
		init();
	}

	public void init() {
	}

	public void tick(Input input) {
	}

	public void renderScreen(Bitmap bm) {
	}

	public void renderHud(Bitmap bm) {
	}

	public final void exit() {
		screenManager.setScreen(new ExitScreen());
	}

	public boolean isLive() {
		return false;
	}
}