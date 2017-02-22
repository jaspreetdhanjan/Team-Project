package com.basementstudios.tag.screen;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.OptionsInterface;

/**
 * Screen representation for when the game is in the menu state.
 * 
 * @author Jaspreet Dhanjan
 */

public class TitleScreen extends Screen {
	private OptionsInterface<String, Screen> options;
	private int tickCount = 0;

	private List<CharacterData> selectedCharas;

	public TitleScreen(List<CharacterData> selectedCharas) {
		this.selectedCharas = selectedCharas;
	}

	public void init() {
		options = new OptionsInterface<String, Screen>(screenManager);
		options.add("Play", new LevelScreen(selectedCharas));
		options.add("Settings", new SettingsScreen());
		options.add("Exit", new ExitScreen());
	}

	public void tick(Input input) {
		tickCount++;
		options.inputTick(input);
	}

	public void renderScene(Bitmap bm) {
		bm.clear();
		for (int i = 0; i < bm.pixels.length; i++) {
			bm.pixels[i] = i + tickCount;
		}

		int xom = (Game.WIDTH - bm.getCharWidth(Game.TITLE)) / 2;
		bm.drawString(Game.TITLE, xom, 84, 0xffffff);

		options.renderSelectables(bm);
	}
}