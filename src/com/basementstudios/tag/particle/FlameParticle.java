package com.basementstudios.tag.particle;

import com.basementstudios.tag.graphics.Bitmap;

/**
 * Generates a random flame particle.
 * 
 * @author Jaspreet Dhanjan
 */

public class FlameParticle extends Particle {
	public int maxLife;
	public boolean noSmoke;

	public FlameParticle(double x, double y, double z) {
		super(x, y, z);

		xSpriteIndex = 0;
		ySpriteIndex = 2;

		life = 60;
		maxLife = life /= 2;
		drag = 0.92;
		gravity = 0;
	}

	public void onRemoved() {
		if (random.nextInt(5) == 0 && !noSmoke) {
			Particle smoke = new SmokeParticle(x, y, 2);
			smoke.xa *= 0.1;
			smoke.ya *= 0.1;
			smoke.za *= 0.1;
			smoke.xa += xa * 1;
			smoke.ya += ya * 1;
			smoke.za += za * 1;
			level.add(smoke);
		}
	}

	public void render(Bitmap bm) {
		if (life % 10 == 0) {
			xSpriteIndex++;
			if (xSpriteIndex > 3) xSpriteIndex = 3;
		}
		super.render(bm);
	}
}