package com.basementstudios.tag;

import java.util.*;

import com.basementstudios.tag.component.Component;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.phys.AxisAlignedBB;

/**
 * A base class for all entities in the game. These are tracked by Level.java
 * 
 * @author Jaspreet Dhanjan
 */

public abstract class Entity {
	public static final Random random = new Random();

	private boolean removed = false;

	protected Level level;
	protected AxisAlignedBB bb = new AxisAlignedBB();

	public double x, y;
	public double xa, ya;
	public double xs, ys;
	public int xSpriteIndex, ySpriteIndex;

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

	public void render(Bitmap bm) {
		int xp = (int) x;
		int yp = (int) y;
		bm.render(getBitmap(), xp, yp, 0xffffff);
	}

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

	public boolean blocks(Entity e) {
		return false;
	}

	public Level getLevel() {
		return level;
	}

	public AxisAlignedBB getBB() {
		return bb;
	}
}