package com.basementstudios.tag;

import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.screen.*;

/**
 * Manages the screen states.
 * 
 * @author Jaspreet Dhanjan
 */

public class ScreenManager {
	private Input input;
	private Screen currentScreen, lastScreen;

	public ScreenManager(Input input, Screen initialScreen) {
		this.input = input;
		setScreen(initialScreen);
	}

	public void setScreen(Screen screen) {
		lastScreen = currentScreen;
		currentScreen = screen;
		currentScreen.init(this);
	}

	public void tick() {
		input.tick();
		if (!input.hasFocus() && currentScreen.isLive()) {
			setScreen(new PauseScreen());
		}

		currentScreen.tick(input);
	}

	public void renderScreen(Bitmap screenBitmap) {
		currentScreen.renderScreen(screenBitmap);
	}
	
	public void renderHud(Bitmap hudBitmap) {
		currentScreen.renderHud(hudBitmap);
	}

	/**
	 * Moves to the last screen state and restores current screen state.
	 * 
	 * WARNING: Does not call Screen.init()!
	 */
	public void toLastScreen() {
		Screen tmpScreen = currentScreen;
		currentScreen = lastScreen;
		lastScreen = tmpScreen;
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}
}