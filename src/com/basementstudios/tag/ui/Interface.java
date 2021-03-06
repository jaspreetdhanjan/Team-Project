package com.basementstudios.tag.ui;

import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;

import java.util.Map;

/**
 * Partial implementation of the screen UI.
 * 
 * @author Jaspreet Dhanjan
 */

public abstract class Interface {
	protected int xStart = 85;
	protected int paddingFromTitle = 64;
	protected int paddingBetweenButtons = 32;

	public void tick(Input input) {
		boolean moveUp = input.up.isClicked();
		boolean moveDown = input.down.isClicked();
		boolean moveLeft = input.left.isClicked();
		boolean moveRight = input.right.isClicked();

		boolean clicked = input.enter.isClicked() || input.space.isClicked();

		if (moveUp || moveDown || moveLeft || moveRight || clicked) {
			onChanged(moveUp, moveDown, moveLeft, moveRight, clicked);
		}
	}

	protected void onChanged(boolean moveUp, boolean moveDown, boolean moveLeft, boolean moveRight, boolean clicked) {
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

			int xo = (bm.width - bm.getCharWidth(option)) / 2;
			int yo = xStart + paddingFromTitle + i * paddingBetweenButtons;
			bm.drawString(option, xo, yo, col);
			i++;
		}
	}
}