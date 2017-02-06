package com.basementstudios.tag.pickup;

import java.util.List;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.mob.Mob;

public class Pickup extends Entity {
	public int xPickupRange = 10;
	public int yPickupRange = 5;

	public Pickup(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void tick() {
		double xr = xr1 - xr0;
		double yr = yr1 - yr0;
		double x0 = x + xr - xPickupRange;
		double y0 = y + yr - yPickupRange;
		double x1 = x - xr + xPickupRange;
		double y1 = y - yr + yPickupRange;

		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e == this) continue;

			if (e instanceof Mob) addEnhancements((Mob) e);
			System.out.println(e);
		}
	}

	public void addEnhancements(Mob mob) {
	}

	public void render(Bitmap bm) {
		double yBob = Math.sin(System.currentTimeMillis() / 100.0 % 100.0) * 3.0;
		int xp = (int) x;
		int yp = (int) (y + yBob);
		bm.render(SpriteSheet.entities[xSpriteIndex][ySpriteIndex], xp, yp, 0xffffff);
	}
}