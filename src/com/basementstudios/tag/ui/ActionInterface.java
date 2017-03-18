package com.basementstudios.tag.ui;

import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.audio.AudioPlayer;
import com.basementstudios.tag.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	private Map<S, T> stateDirectory = new LinkedHashMap<S, T>();
	private int selectedIndex = 0;
	private S selection;

	public ActionInterface(String title) {
		this.title = title;
	}

	protected void onChanged(boolean moveUp, boolean moveDown, boolean moveLeft, boolean moveRight, boolean clicked) {
		List<S> nodes = new ArrayList<S>(stateDirectory.keySet());

		if (moveUp) selectedIndex--;
		if (moveDown) selectedIndex++;
		if (selectedIndex < 0) {
			selectedIndex = 0;
			return;
		}
		if (selectedIndex >= nodes.size()) {
			selectedIndex = nodes.size() - 1;
			return;
		}

		T selected = stateDirectory.get(nodes.get(selectedIndex));

		AudioPlayer.play(ResourceManager.i.selectionSound);

		if (selected != null) {
			selected.onHover();
			if (clicked) {
				selected.onClick();
			}
		}
		if (selectedIndex < 0) selection = null;
		else selection = nodes.get(selectedIndex);
	}

	public void render(Bitmap bm) {
		int xScale = 2;
		int yScale = 2;
		int xom = (bm.width - bm.getCharWidth(title) * xScale) / 2;
		bm.setScale(xScale, yScale);
		bm.drawString(title, xom, xStart, 0xffffff);
		bm.setScale(1, 1);

		drawSelectables(bm, selectedIndex, stateDirectory);

	}

	public void add(S param, T event) {
		stateDirectory.put(param, event);
		onChanged(false, false, false, false, false);
	}

	public void remove(S param) {
		stateDirectory.remove(param);
		onChanged(false, false, false, false, false);
	}

	public S getSelected() {
		return selection;
	}

	public void replaceOption(S oldKey, S newKey) {
		T ob = stateDirectory.remove(oldKey);
		stateDirectory.put(newKey, ob);
	}
}