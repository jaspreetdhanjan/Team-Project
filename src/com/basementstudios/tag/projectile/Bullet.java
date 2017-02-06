package com.basementstudios.tag.projectile;

import com.basementstudios.tag.*;
import com.basementstudios.tag.mob.*;

// Largely unfinished
// Do not attempt to fix this

public class Bullet extends Entity {
	private Mob launchedBy;
//	private double xOrigin, yOrigin;

	private int life, dmg;
	private double speed;

	public Bullet(Mob launchedBy, double x, double y, double dir) {
		this.launchedBy = launchedBy;
		this.x = x;
		this.y = y;
//		xOrigin = x;
//		yOrigin = y;
		speed = 3.5;
		xa = Math.cos(dir) * speed;
		ya = Math.sin(dir) * speed;
		life = 100 - random.nextInt(20);
		dmg = random.nextInt(2) + 1;

		xs = 5;
		ys = 5;
	}

	public void tick() {
		super.tick();

		if (--life < 0) {
			remove();
			return;
		}
		attemptMove();
	}

	private void attemptMove() {
	/*	if (isRemoved()) return;
		
		

		double xxa = x + xa;
		double yya = y + ya;

		double x0 = xxa + xr0;
		double y0 = yya + yr0;
		double x1 = xxa + xr1;
		double y1 = yya + yr1;

		if (x0 < 0 || y0 < 0 || x1 >= (getLevel().getWidth() * 16) || y1 >= (getLevel().getWidth() * 16)) {
			remove();
			return;
		}*/
	/*	List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e == this || e == launchedBy) continue;

			if (e instanceof Mob) {
				Mob m = (Mob) e;
				if (m.intersects(this)) {
					m.hurt(this, dmg);
					remove();
				}
			}
		}

		x = xxa;
		y = yya;*/
	}
/*
	public void render(Bitmap bm) {
		int x0 = (int) (x + xr0);
		int y0 = (int) (y + yr0);
		int x1 = (int) (x + xr1);
		int y1 = (int) (y + yr1);

		bm.fill(x0, y0, x1, y1, 0xff0000);
	}*/

	public boolean blocks(Entity e) {
		return true;
	}
}