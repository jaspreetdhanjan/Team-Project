package com.basementstudios.tag.screen;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;

/**
 * Screen representation for when the game is in the menu state.
 * 
 * @author Jaspreet Dhanjan
 */

public class MenuScreen extends Screen {
	private String[] options = { "Play", "Exit" };
	private int selected = 0;
	private int tickCount = 0;

	private List<CharacterData> selectedCharas;

	public MenuScreen(List<CharacterData> selectedCharas) {
		this.selectedCharas = selectedCharas;
	}

	public void tick(Input input) {
		tickCount++;

		if ((input.up.clicked || input.left.clicked) && selected > 0) selected--;
		if ((input.down.clicked || input.right.clicked) && selected < options.length - 1) selected++;

		if (selected == 0 && (input.enter.clicked || input.space.clicked)) game.setScreen(new GameScreen(selectedCharas));
		if (selected == 1 && (input.enter.clicked || input.space.clicked)) exit();
	}

	public void renderScene(Bitmap bm) {
		Font font = Font.getInstance();

		bm.clear();
		for (int i = 0; i < bm.pixels.length; i++) {
			bm.pixels[i] = ((i + tickCount) & 16);
		}

		int xom = (Game.WIDTH - font.getCharWidth(Game.TITLE)) / 2;
		font.draw(bm, Game.TITLE, xom, 84, 0xffffff);

		for (int i = 0; i < options.length; i++) {
			String option = options[i];
			if (i == selected) option = "-> " + option;
			int xo = (Game.WIDTH - font.getCharWidth(option)) / 2;
			int yo = 128 + i * 20;
			font.draw(bm, option, xo, yo, 0xffffff);
		}
	}
}