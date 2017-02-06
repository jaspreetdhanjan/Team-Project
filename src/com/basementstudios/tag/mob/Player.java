package com.basementstudios.tag.mob;

import java.util.List;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.projectile.Bullet;

public class Player extends Mob {
	private int shootTime = 0;

	public Player(double x, double y) {
		super(x, y);
		xSpriteIndex = 0;
		ySpriteIndex = 0;

		xr0 = 5;
		yr0 = 4;
		xr1 = 13 + 16;
		yr1 = 26 + 16;
	}

	public void tick() {
		super.tick();

		if (shootTime > 0) {
			shootTime--;
		}
	}

	public void attemptShoot() {
		if (shootTime != 0) return;
		shootTime = 10;

		double radius = 240.0;
		Mob target = getNearestMob(radius);
		if (target == null) return;

		double dy = (target.y + (target.yr1 - target.yr0) / 3) - y;
		double dx = (target.x + (target.xr1 - target.xr0) / 3) - x;
		double dir = Math.atan2(dy, dx);
		level.add(new Bullet(this, x, y, dir));
	}

	private Mob getNearestMob(double radius) {
		Mob result = null;
		List<Entity> entities = level.getEntities(x - radius, y - radius, x + radius, y + radius);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e == this) continue;
			if (e instanceof Mob) result = (Mob) e;
		}

		return result;
	}

	public void render(Bitmap bm) {
		int colour = 0xffffff;
		if (hitTime > 0) {
			hitTime--;
			colour = 0xcc0000;
		}

		xSpriteIndex = 0;
		if (xa != 0) xSpriteIndex = 2;

		if (xa < 0) bm.xFlip = true;
		if (xa > 0) bm.xFlip = false;

		int xp = (int) x;
		int yp = (int) y;
		bm.render(SpriteSheet.entities[xSpriteIndex][ySpriteIndex + 0], xp, yp, colour);
		bm.render(SpriteSheet.entities[xSpriteIndex][ySpriteIndex + 1], xp, yp + 16, colour);
		if (bm.xFlip == true) bm.xFlip = false;
	}
}