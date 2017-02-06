package com.basementstudios.tag.screen;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;

public class MenuScreen extends Screen {
	private String[] options = { "Play", "Exit" };
	private int selected = 0;

	public void tick(Input input) {
		if ((input.up.clicked || input.left.clicked) && selected > 0) selected--;
		if ((input.down.clicked || input.right.clicked) && selected < options.length - 1) selected++;

		if (selected == 0 && (input.enter.clicked || input.space.clicked)) game.setScreen(new GameScreen());
		if (selected == 1 && (input.enter.clicked || input.space.clicked)) exit();
	}

	public void render(Bitmap bm) {
		bm.fill(0, 0, bm.width, bm.height, 0xffffff);

		int xom = (Game.WIDTH - Font.instance.getCharWidth(Game.TITLE)) / 2;
		Font.instance.render(bm, Game.TITLE, xom, 64, 0);

		for (int i = 0; i < options.length; i++) {
			String option = options[i];
			if (i == selected) option = "-> " + option;
			int xo = (Game.WIDTH - Font.instance.getCharWidth(option)) / 2;
			int yo = 100 + i * 20;
			Font.instance.render(bm, option, xo, yo, 0);
		}
	}
}