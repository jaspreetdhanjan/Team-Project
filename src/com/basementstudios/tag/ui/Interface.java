package com.basementstudios.tag.ui;

import java.util.Map;

import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;

public abstract class Interface {
	public final void inputTick(Input input) {
		boolean moveUp = input.up.isClicked() || input.left.isClicked();
		boolean moveDown = input.down.isClicked() || input.right.isClicked();
		boolean clicked = input.enter.isClicked() || input.space.isClicked();

		if (moveUp || moveDown || clicked) {
			onChanged(moveUp, moveDown, clicked);
		}
	}

	protected void onChanged(boolean moveUp, boolean moveDown, boolean clicked) {
	}

	public abstract void render(Bitmap bm);

	protected <S, T> void drawSelectables(Bitmap bm, int selectedIndex, Map<S, T> stateDirectory) {
		boolean shift = (System.currentTimeMillis() / 16) / 20 % 2 == 0;

		int i = 0;
		for (Map.Entry<S, T> entry : stateDirectory.entrySet()) {
			int col = 0x444444;
			S op = entry.getKey();
			String option = op.toString();
			if (selectedIndex == i) {
				String la = "> ";
				String ra = " <";
				if (shift) {
					la = la + " ";
					ra = " " + ra;
				}
				col = 0xffffff;
				option = la + option + ra;
			}

			int xo = (Game.WIDTH - bm.getCharWidth(option)) / 2;
			int yo = 128 + i * 20;
			bm.drawString(option, xo, yo, col);
			i++;
		}
	}
}