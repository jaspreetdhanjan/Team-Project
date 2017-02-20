package com.basementstudios.tag.screen;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;
import com.basementstudios.tag.level.Level;

/**
 * Chooses the level for a player, levels/ nodes are unlocked as the player progresses.
 * 
 * @author Jaspreet Dhanjan
 */

public class LevelScreen extends Screen {
	private String[] options = { "Level 1" };
	private Level[] levels = { new Level(Game.WIDTH - 50, Game.HEIGHT - 50) };
	private int selected = 0;

	private List<CharacterData> selectedCharas;

	public LevelScreen(List<CharacterData> selectedCharas) {
		this.selectedCharas = selectedCharas;
	}

	public void tick(Input input) {
		if ((input.up.clicked || input.left.clicked) && selected > 0) selected--;
		if ((input.down.clicked || input.right.clicked) && selected < options.length - 1) selected++;

		if (selected == 0 && (input.enter.clicked || input.space.clicked)) {
			screenManager.setScreen(new GameScreen(selectedCharas, levels[selected]));
		} else if (selected == 1 && (input.enter.clicked || input.space.clicked)) {
			exit();
		}
	}

	public void renderScene(Bitmap bm) {
		Font font = Font.getInstance();

		bm.clear();
		renderSelectables(font, bm, options, selected);
	}
}