package com.basementstudios.tag.screen;

import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.*;

/**
 * Performs an exit sequence.
 * 
 * @author Jaspreet Dhanjan
 */

public class ExitScreen extends Screen {
	private int time = 1 * 60;
	private int exitCode = 0;

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	public void tick(Input input) {
		if (--time < 0) {
			System.exit(exitCode);
		}
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();

		String title = "Exiting in " + (time / 5) + "...";
		int xOffs = (bm.width - bm.getCharWidth(title)) / 2;
		int yOffs = 80;

		bm.drawString(title, xOffs, yOffs, 0xffffff);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
	}
}