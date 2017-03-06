package com.basementstudios.tag;

import java.util.*;

import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.resource.SpriteSheet;

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

	public final void init(Level level) {
		this.level = level;
	}

	public void tick() {
	}

	public abstract void render(Bitmap bm);

	public Bitmap getBitmap() {
		return getSpriteSheet().getSprites()[xSpriteIndex][ySpriteIndex];
	}

	public SpriteSheet getSpriteSheet() {
		return ResourceManager.i.entitiesSpriteSheet;
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