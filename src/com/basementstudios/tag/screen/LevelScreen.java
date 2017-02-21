package com.basementstudios.tag.screen;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.OverlayRenderer;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.level.TestLevel;

/**
 * Chooses the level for a player, levels/ nodes are unlocked as the player progresses.
 * 
 * @author Jaspreet Dhanjan
 */

public class LevelScreen extends Screen {
	private OverlayRenderer<Level, Screen> overlayRenderer;

	private List<CharacterData> selectedCharas;

	public LevelScreen(List<CharacterData> selectedCharas) {
		this.selectedCharas = selectedCharas;
	}

	public void init() {
		Level testLevel = new TestLevel(Game.WIDTH - 50, Game.HEIGHT - 50);

		overlayRenderer = new OverlayRenderer<Level, Screen>(screenManager);
		overlayRenderer.add(testLevel, new GameScreen(selectedCharas, testLevel));
	}

	public void tick(Input input) {
		overlayRenderer.inputTick(input);
	}

	public void renderScene(Bitmap bm) {
		bm.clear();
		overlayRenderer.renderSelectables(Font.getInstance(), bm);
	}
}