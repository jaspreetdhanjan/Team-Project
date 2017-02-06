package com.basementstudios.tag.level;

import java.util.*;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;

public class Level {
	public final int width, height;
	private int playTime;
	private List<Entity> entities = new ArrayList<Entity>();

	private boolean isDirty = false;
	private List<Entity> entitiesToRemove = new ArrayList<Entity>();
	private List<Entity> tmpResult = new ArrayList<Entity>();

	public Level(Bitmap map) {
		width = map.width;
		height = map.height;
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
		renderEntities(bm);
	}

	private void renderEntities(Bitmap bm) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(bm);
		}
	}

	public void tick() {
		playTime++;

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

	public List<Entity> getEntities(double x0, double y0, double x1, double y1) {
		tmpResult.clear();
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e.intersects(x0, y0, x1, y1)) {
				tmpResult.add(e);
			}
		}
		return tmpResult;
	}

	public int getPlayTime() {
		return playTime;
	}
}