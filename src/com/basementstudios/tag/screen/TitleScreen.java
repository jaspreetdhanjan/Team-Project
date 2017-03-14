package com.basementstudios.tag.screen;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.audio.AudioPlayer;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.ui.RedirectInterface;

/**
 * Screen representation for when the game is in the menu state.
 * 
 * @author Jaspreet Dhanjan
 */

public class TitleScreen extends Screen {
	private RedirectInterface<String, Screen> options;

	public void init() {
        AudioPlayer.play(ResourceManager.i.soundtrackSound);

        options = new RedirectInterface<String, Screen>(screenManager, Game.TITLE);
		//options.add("End", new EndScreen());
		options.add("Play", new LevelScreen());
		options.add("Options", new OptionsScreen(this));
		options.add("Quit", new ExitScreen());
	}

	public void tick(Input input) {
		options.tick(input);
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		options.render(bm);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
		int pp = 0;
        int s = 30;
        bm.drawString("Use the arrow keys to select an option", 8, 8 + s * pp++, 0xffffff);
		bm.drawString("For updates visit: " + Game.URL, 8, 8 + s * pp++, 0xffffff);
	}
}