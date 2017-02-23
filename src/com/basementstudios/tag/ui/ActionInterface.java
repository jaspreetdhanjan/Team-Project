package com.basementstudios.tag.ui;

import java.util.*;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * A basic interface that runs the Action class when an item is clicked
 * 
 * @author Jaspreet Dhanjan
 *
 * @param <S>
 *            the class (toString) representation of an option on the GUI.
 * @param <T>
 *            the action to run.
 */

public class ActionInterface<S, T extends Action> extends Interface {
	private String title;

	private Map<S, T> stateDirectory = new HashMap<S, T>();
	private T selected;
	private int selectedIndex = 0;

	public ActionInterface(String title) {
		this.title = title;
	}

	protected void onChanged(boolean moveUp, boolean moveDown, boolean clicked) {
		List<S> nodes = new ArrayList<S>(stateDirectory.keySet());

		if (moveUp && selectedIndex >= 1) selectedIndex--;
		if (moveDown && selectedIndex < nodes.size() - 1) selectedIndex++;

		S key = nodes.get(selectedIndex);
		selected = stateDirectory.get(key);

		if (selected != null) {
			selected.onHovered();
			
			if (clicked) {
				selected.onClick();
			}
		}
	}

	public void render(Bitmap bm) {
		int xScale = 2;
		int yScale = 2;
		int xom = (Game.WIDTH - bm.getCharWidth(title) * xScale) / 2;
		bm.setScale(xScale, yScale);
		bm.drawString(title, xom, xStart, 0xffffff);
		bm.setScale(1, 1);

		drawSelectables(bm, selectedIndex, stateDirectory);
	}

	public void add(S param, T event) {
		stateDirectory.put(param, event);
	}

	public void remove(S param) {
		stateDirectory.remove(param);
	}
}