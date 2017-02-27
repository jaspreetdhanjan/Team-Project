package com.basementstudios.tag.particle;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * Generates a single smoke particle.
 * 
 * @author Jaspreet Dhanjan
 */

public class SmokeParticle extends Particle {
	private int lifeTime;

	public SmokeParticle(double x, double y, double z) {
		super(x, y, z);

		life = 30;
		lifeTime = life / 2;
		gravity = -0.01;

		xSpriteIndex = 0;
		ySpriteIndex = 1;
	}

	public void tick() {
		if (life / 10 == 0) {
			xSpriteIndex++;
			if (xSpriteIndex > 2) xSpriteIndex = 2;
		}

		xa += random.nextGaussian() * 0.01;
		super.tick();
	}

	public void collide(Entity otherEntity, double xxa, double yya, double zza) {
		remove();
	}

	public void render(Bitmap bm) {
		int tint = life * 127 / lifeTime + 1;
		int xp = (int) x;
		int yp = (int) (y - z);
		bm.render(getBitmap(), xp, yp, 0x101010 * tint);
	}
}