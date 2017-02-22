package com.basementstudios.tag;

import java.util.*;

import com.basementstudios.tag.component.Component;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.level.Level;

/**
 * A base class for all entities in the game. These are tracked by Level.java
 * 
 * @author Jaspreet Dhanjan
 */

public abstract class Entity {
	public static final Random random = new Random();

	private boolean removed = false;

	protected Level level;
	protected int xSpriteIndex, ySpriteIndex;
	protected int colour = 0xffffff;

	private List<Component> components = new ArrayList<Component>();

	public final void init(Level level) {
		this.level = level;
	}

	public void addComponent(Component c) {
		components.add(c);
	}

	public void tick() {
		for (Component com : components) {
			com.tick();
		}
	}

	public abstract void render(Bitmap bm);

	public Bitmap getBitmap() {
		return SpriteSheet.entities[xSpriteIndex][ySpriteIndex];
	}

	public void onRemoved() {
	}

	public final void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public Level getLevel() {
		return level;
	}
}