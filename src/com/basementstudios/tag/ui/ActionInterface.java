package com.basementstudios.tag.ui;

import java.util.*;

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

	private Map<S, T> stateDirectory = new LinkedHashMap<S, T>();
	private T selected;
	private int selectedIndex = 0;

	public ActionInterface(String title) {
		this.title = title;
	}

	protected void onChanged(boolean moveUp, boolean moveRight, boolean moveDown, boolean moveLeft, boolean clicked) {
		List<S> nodes = new ArrayList<S>(stateDirectory.keySet());

		if (moveUp) selectedIndex--;
		if (moveDown) selectedIndex++;
		if (selectedIndex < 0) selectedIndex = 0;
		if (selectedIndex >= nodes.size()) selectedIndex = nodes.size() - 1;

		S key = nodes.get(selectedIndex);
		selected = stateDirectory.get(key);

		if (selected != null) {
			if (clicked) {
				selected.onClick();
				
			} 
			if (moveRight) selected.onRight();
			if (moveLeft)  selected.onLeft();
		}
		
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
	}
	
	public void replaceEntry(S oldEntry, S newEntry){
		Map<S, T> cpy = new LinkedHashMap<S, T>();
		
		for (Map.Entry<S, T> entry : stateDirectory.entrySet()) {
			S key = entry.getKey();
			T val = entry.getValue();
			
			if(key.equals(oldEntry)){
				cpy.put(newEntry, val);
			} else {
				cpy.put(key, val);
			}
		}
		
		stateDirectory = cpy;
	}
	
	public T get(S param){
		return stateDirectory.get(param);
	}
	
	public void remove(S param) {
		stateDirectory.remove(param);
	}
}