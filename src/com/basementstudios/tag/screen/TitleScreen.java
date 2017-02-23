package com.basementstudios.tag.screen;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.RedirectInterface;

/**
 * Screen representation for when the game is in the menu state.
 * 
 * @author Jaspreet Dhanjan
 */

public class TitleScreen extends Screen {
	private RedirectInterface<String, Screen> options;

	private List<CharacterData> selectedCharas;

	public TitleScreen(List<CharacterData> selectedCharas) {
		this.selectedCharas = selectedCharas;
	}

	public void init() {
		options = new RedirectInterface<String, Screen>(screenManager, Game.TITLE);
		options.add("Play", new LevelScreen(selectedCharas));
		options.add("Options", new OptionsScreen());
		options.add("Exit", new ExitScreen());
	}

	public void tick(Input input) {
		options.inputTick(input);
	}

	public void renderScene(Bitmap bm) {
		bm.clear();
		options.render(bm);
	}
}