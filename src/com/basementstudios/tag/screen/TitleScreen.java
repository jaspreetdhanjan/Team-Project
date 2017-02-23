package com.basementstudios.tag.screen;

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

	public void init() {
		options = new RedirectInterface<String, Screen>(screenManager, Game.TITLE);
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
		bm.fill(0, 0, bm.width, 5, 0x333333);

		panelDraw(bm, "Visit theadventurersguild.co.uk for the latest updates!", 1);
		panelDraw(bm, "Tweet us at: theadventurersguild", 2);
	}

	private void panelDraw(Bitmap bm, String msg, int line) {
		int xCenter = (bm.width - bm.getCharWidth(msg)) / 2;
		int yCenter = 12;
		int xx = (int) (Math.sin(System.currentTimeMillis() / (10.0*msg.length()) % 500.0) * 30.0);
		bm.drawString(msg, xCenter + xx, yCenter * line, 0xffffff);
	}
}