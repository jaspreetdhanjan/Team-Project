package com.basementstudios.tag.level;

import java.util.*;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.phys.AxisAlignedBB;
import com.basementstudios.tag.resource.LevelData;

/**
 * Tracks the entities within a level, also produces the level environment.
 * 
 * @author Jaspreet Dhanjan
 */

public abstract class Level {
	private final LevelData levelData;

	private AxisAlignedBB bb = new AxisAlignedBB();
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> toAdd = new ArrayList<Entity>();
	private List<Entity> toRemove = new ArrayList<Entity>();

	/**
	 * Constructs a new level.
	 * 
	 * @param levelData
	 *            the data specs of the level.
	 */
	public Level(LevelData levelData) {
		this.levelData = levelData;
		bb.set(0, 0, levelData.getLevelImage().width, levelData.getLevelImage().height);
	}

	public void add(Entity e) {
		toAdd.add(e);
	}

	public void remove(Entity e) {
		toRemove.remove(e);
	}

	public void render(Bitmap bm) {
		bm.render(levelData.getLevelImage(), 0, 0, 0xffffff);
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

	private List<Mob> tmpResult = new ArrayList<Mob>();

	public List<Mob> getMobs(AxisAlignedBB bb) {
		tmpResult.clear();
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e instanceof Mob) {
				Mob m = (Mob) e;
				if (bb.contains(m.getBB())) {
					tmpResult.add(m);
					continue;
				}
			}
		}
		return tmpResult;
	}

	public LevelData getLevelData() {
		return levelData;
	}

	public AxisAlignedBB getBB() {
		return bb;
	}
	
	public String toString() {
		return levelData.getLevelName();
	}
}