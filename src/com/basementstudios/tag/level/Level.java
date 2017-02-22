package com.basementstudios.tag.level;

import java.util.*;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.phys.AxisAlignedBB;

public abstract class Level {
	private final String levelName;
	private final int width, height;
	public AxisAlignedBB bb = new AxisAlignedBB();

	private List<Entity> entities = new ArrayList<Entity>();

	private List<Entity> toAdd = new ArrayList<Entity>();
	private List<Entity> toRemove = new ArrayList<Entity>();

	public Level(String levelName, int width, int height) {
		this.levelName = levelName;
		this.width = width;
		this.height = height;
		bb.set(0, 0, width, height);
	}

	public void add(Entity e) {
		toAdd.add(e);
	}

	public void remove(Entity e) {
		toRemove.remove(e);
	}

	public void render(Bitmap bm) {
		bm.fill(0, 0, width, height, 0xe4cda2);
		entities.forEach(e -> e.render(bm));
	}

	public void tick() {
		for (Entity e : entities) {
			e.tick();

			if (e.isRemoved()) {
				toRemove.add(e);
			}
		}

		if (!toAdd.isEmpty()) {
			for (Entity e : toAdd) {
				entities.add(e);
				e.init(this);
			}
			toAdd.clear();
		}

		if (!toRemove.isEmpty()) {
			for (Entity e : toRemove) {
				e.onRemoved();
				entities.remove(e);
			}
			toRemove.clear();
		}
	}

	// TODO: Fix this!
	/*
		private List<Entity> tmpResult = new ArrayList<Entity>();
	
		public List<Entity> getEntities(AxisAlignedBB bb) {
			tmpResult.clear();
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
	
				AxisAlignedBB otherBB = e.getBB();
				if (bb.contains(otherBB)) {
					tmpResult.add(e);
				}
			}
			return tmpResult;
		}*/

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String toString() {
		return levelName;
	}
}