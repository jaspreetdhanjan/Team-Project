package com.basementstudios.tag.level;

import java.util.*;

import com.basementstudios.tag.entity.Entity;
import com.basementstudios.tag.phys.AxisAlignedBB;

public class Level {
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> resultCache = new ArrayList<Entity>();

	public void add(Entity e) {
		entities.add(e);
		e.init(this);
	}

	public void remove(Entity e) {
		e.onRemoved();
		entities.remove(e);
	}

	public void tick(float delta) {
		entities.forEach(e -> e.tick(delta));
	}

	public void render() {
		entities.forEach(e -> e.render());
	}

	public List<Entity> getEntities(AxisAlignedBB bb) {
		resultCache.clear();

		for (Entity entity : entities) {
			if (bb.contains(entity.getBB())) {
				resultCache.add(entity);
				continue;
			}
		}
		return resultCache;
	}
}