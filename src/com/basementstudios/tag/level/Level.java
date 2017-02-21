package com.basementstudios.tag.level;

import java.util.*;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.phys.AxisAlignedBB;

public abstract class Level {
	private final String levelName;
	private final int width, height;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> entitiesToRemove = new ArrayList<Entity>();
	private boolean isDirty = false;

	public Level(String levelName, int width, int height) {
		this.levelName = levelName;
		this.width = width;
		this.height = height;
	}

	public void add(Entity e) {
		entities.add(e);
		e.init(this);
	}

	private void remove(Entity e) {
		e.onRemoved();
		entities.remove(e);
	}

	public void render(Bitmap bm) {
		bm.fill(0, 0, width, height, 0xe4cda2);

		renderEntities(bm);
	}

	private void renderEntities(Bitmap bm) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(bm);
		}
	}

	public void tick() {
		for (Entity entity : entities) {
			if (entity.isRemoved()) {
				isDirty = true;
				entitiesToRemove.add(entity);
			} else {
				entity.tick();
			}
		}

		if (isDirty) {
			for (Entity entity : entitiesToRemove) {
				remove(entity);
			}
			entitiesToRemove.clear();
		}
	}

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
	}

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