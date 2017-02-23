package com.basementstudios.tag.screen;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.*;
import com.basementstudios.tag.ui.RedirectInterface;

/**
 * Chooses the level for a player, levels/ nodes are unlocked as the player progresses.
 * 
 * @author Jaspreet Dhanjan
 */

public class LevelScreen extends Screen {
	private List<CharacterData> selectedCharas;
	private RedirectInterface<Level, Screen> options;

	public LevelScreen(List<CharacterData> selectedCharas) {
		this.selectedCharas = selectedCharas;
	}

	public void init() {
		Level testLevel = new TestLevel(Game.WIDTH - 50, Game.HEIGHT - 50);

		options = new RedirectInterface<Level, Screen>(screenManager, "Level Selector");
		options.add(testLevel, new GameScreen(selectedCharas, testLevel));
	}

	public void tick(Input input) {
		options.inputTick(input);
	}

	public void renderScene(Bitmap bm) {
		bm.clear();
		options.render(bm);
	}
}