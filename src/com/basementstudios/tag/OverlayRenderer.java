package com.basementstudios.tag;

import java.util.*;

import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.screen.Screen;

/**
 * Basically a GUI renderer.
 * 
 * @author Jaspreet Dhanjan
 *
 * @param <S>
 *            the class (toString) representation of an option on the GUI.
 * @param <T>
 *            the destination String if option is chosen.
 */

public class OverlayRenderer<S, T extends Screen> {
	private ScreenManager manager;

	private Map<S, T> stateDirectory = new HashMap<S, T>();
	private T selected;
	private int selectedIndex = 0;

	public OverlayRenderer(ScreenManager manager) {
		this.manager = manager;
	}

	public void inputTick(Input input) {
		boolean moveUp = input.up.isClicked() || input.left.isClicked();
		boolean moveDown = input.down.isClicked() || input.right.isClicked();
		boolean clicked = input.enter.isClicked() || input.space.isClicked();

		if (moveUp || moveDown || clicked) {
			opTick(moveUp, moveDown, clicked);
		}
	}

	private void opTick(boolean moveUp, boolean moveDown, boolean clicked) {
		List<S> nodes = new ArrayList<S>(stateDirectory.keySet());

		if (moveUp && selectedIndex >= 1) selectedIndex--;
		if (moveDown && selectedIndex < nodes.size() - 1) selectedIndex++;

		S key = nodes.get(selectedIndex);
		selected = stateDirectory.get(key);

		if (clicked && selected != null) {
			manager.setScreen(selected);
		}
	}

	int i = 0;

	public void renderSelectables(Bitmap bm) {
		i = 0;
		stateDirectory.entrySet().forEach((e) -> {
			S op = e.getKey();

			String option = op.toString();
			if (i == selectedIndex) option = "-> " + option;

			int xo = (Game.WIDTH - bm.getCharWidth(option)) / 2;
			int yo = 128 + i * 20;
			bm.drawString(option, xo, yo, 0xffffff);
			i++;
		});
	}

	public void add(S param, T screen) {
		stateDirectory.put(param, screen);
	}
}