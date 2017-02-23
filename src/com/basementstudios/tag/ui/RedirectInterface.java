package com.basementstudios.tag.ui;

import java.util.*;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.ScreenManager;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.screen.Screen;

/**
 * A basic interface that shows a number of selectables, redirects to T.
 * 
 * @author Jaspreet Dhanjan
 *
 * @param <S>
 *            the class (toString) representation of an option on the GUI.
 * @param <T>
 *            the destination String if option is chosen.
 */

public class RedirectInterface<S, T extends Screen> extends Interface {
	private ScreenManager manager;
	private String title;

	private Map<S, T> stateDirectory = new HashMap<S, T>();
	private T selected;
	private int selectedIndex = 0;

	public RedirectInterface(ScreenManager manager, String title) {
		this.manager = manager;
		this.title = title;
	}

	protected void onChanged(boolean moveUp, boolean moveDown, boolean clicked) {
		// Put all the keys from the directory into a list.
		// It is then easier to traverse this list using a simple index variable.
		// If clicked is true, then we go to the current index in that list and retrieve the new screen.

		List<S> nodes = new ArrayList<S>(stateDirectory.keySet());

		if (moveUp && selectedIndex >= 1) selectedIndex--;
		if (moveDown && selectedIndex < nodes.size() - 1) selectedIndex++;

		S key = nodes.get(selectedIndex);
		selected = stateDirectory.get(key);

		if (clicked && selected != null) {
			manager.setScreen(selected);
		}
	}

	public void render(Bitmap bm) {
		int xScale = 2;
		int yScale = 2;
		int xom = (Game.WIDTH - bm.getCharWidth(title) * xScale) / 2;
		bm.setScale(xScale, yScale);
		bm.drawString(title, xom, 84, 0xffffff);
		bm.setScale(1, 1);

		drawSelectables(bm, selectedIndex, stateDirectory);
	}

	public void add(S param, T screen) {
		stateDirectory.put(param, screen);
	}
}