package com.basementstudios.tag;

import java.util.*;

import com.basementstudios.tag.component.Component;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.level.Level;

public class Entity {
	public static final Random random = new Random();

	public Level level;
	private boolean removed = false;

	public int xSpriteIndex, ySpriteIndex;
	public double x, y;
	public double xa, ya;
	public double xr0, yr0, xr1, yr1;

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

	public boolean intersects(double x0, double y0, double x1, double y1) {
		double xr = xr1 - xr0;
		double yr = yr1 - yr0;
		if (x1 <= x - xr || x0 > x + xr || y1 <= y - yr || y0 > y + yr) return false;
		return true;
	}

	public boolean intersects(Entity e) {
		double xr = e.xr1 - e.xr0;
		double yr = e.yr1 - e.yr0;
		return intersects(e.x, e.y, e.x + xr, e.y + yr);
	}

	public boolean blocks(Entity e) {
		return false;
	}
}