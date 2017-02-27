package com.basementstudios.tag.particle;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * Creates an explosion effect within the game with use of other particles.
 * 
 * @author Jaspreet Dhanjan
 */

public class Explosion extends Entity {
	public double x, y, z;
	public int life, maxLife;

	public Explosion(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		maxLife = life = 5;
	}

	public void tick() {
		super.tick();
		if (--life < 0) {
			remove();
			return;
		}

		int ps = (life * 40) / (maxLife + 1);
		double dd = (maxLife - life) / (double) maxLife + 0.2;
		for (int i = 0; i < ps; i++) {
			double dir = random.nextDouble() * Math.PI * 2;
			double dist = random.nextDouble() * 6 * dd;
			double xx = x + Math.cos(dir) * dist;
			double yy = y + Math.sin(dir) * dist;
			double zz = z + random.nextDouble() * 10;

			FlameParticle fd = new FlameParticle(xx, yy, zz);
			if (random.nextInt(2) == 0) fd.life = fd.maxLife / 2;
			fd.xa *= 0.1;
			fd.ya *= 0.1;
			fd.za *= 0.1;
			fd.xa += (xx - x) * 0.5;
			fd.ya += (yy - y) * 0.5;
			fd.gravity = -0.10;
			level.add(fd);
		}
	}

	public void render(Bitmap bm) {
	}
}