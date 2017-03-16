package com.basementstudios.tag.level;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.resource.LevelData;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the entities within a level, also produces the level environment.
 * 
 * @author Jaspreet Dhanjan
 */

public abstract class Level {
	private final LevelData levelData;

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
	}

	public void init() {

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

    public abstract ArrayList<CharacterData> getEnemy();


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

	public LevelData getLevelData() {
		return levelData;
	}

	public String toString() {
		return levelData.getLevelName();
	}

}