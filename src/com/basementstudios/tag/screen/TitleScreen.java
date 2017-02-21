package com.basementstudios.tag.screen;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.OverlayRenderer;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * Screen representation for when the game is in the menu state.
 * 
 * @author Jaspreet Dhanjan
 */

public class TitleScreen extends Screen {
	private OverlayRenderer<String, Screen> overlayRenderer;
	private int tickCount = 0;

	private List<CharacterData> selectedCharas;

	public TitleScreen(List<CharacterData> selectedCharas) {
		this.selectedCharas = selectedCharas;
	}

	public void init() {
		overlayRenderer = new OverlayRenderer<String, Screen>(screenManager);
		overlayRenderer.add("Play", new LevelScreen(selectedCharas));
		overlayRenderer.add("Options", new OptionsScreen());
		overlayRenderer.add("Exit", new ExitScreen());
	}

	public void tick(Input input) {
		tickCount++;
		overlayRenderer.inputTick(input);
	}

	public void renderScene(Bitmap bm) {
		bm.clear();
		for (int i = 0; i < bm.pixels.length; i++) {
			bm.pixels[i] = i + tickCount;
		}

		int xom = (Game.WIDTH - bm.getCharWidth(Game.TITLE)) / 2;
		bm.drawString(Game.TITLE, xom, 84, 0xffffff);

		overlayRenderer.renderSelectables(bm);
	}
}