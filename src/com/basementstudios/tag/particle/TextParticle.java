package com.basementstudios.tag.particle;

import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;

public class TextParticle extends Particle {
	private String msg;
	private int colour;

	public TextParticle(String msg, double x, double y, double z, int colour) {
		super(x, y, z);
		this.msg = msg;
		this.colour = colour;

		xa = random.nextGaussian() * 0.3;
		ya = random.nextGaussian() * 0.2;
		za = random.nextGaussian() * 0.7 + 2;
		life += random.nextInt(20);
	}

	public void tick() {
		if (z < 0) {
			z = 0;
			xa *= 0.6;
			ya *= 0.6;
			za *= -0.5;
		}
		super.tick();
	}

	public void render(Bitmap bm) {
		int xp = (int) x;
		int yp = (int) (y - z);
		Font.render(bm, msg, xp, yp, colour);
	}
}