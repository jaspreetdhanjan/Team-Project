package com.basementstudios.tag.screen;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * A base class that localises all screen states.
 * 
 * @author Jaspreet Dhanjan
 */

public class Screen {
	protected Game game;

	public final void init(Game game) {
		this.game = game;
		init();
	}

	public void init() {
	}

	public void tick(Input input) {
	}

	public void render(Bitmap bm) {
	}

	public final void exit() {
		System.exit(1);
	}
}