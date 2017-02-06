package com.basementstudios.tag.screen;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;

public class Screen {
	public Game game;

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