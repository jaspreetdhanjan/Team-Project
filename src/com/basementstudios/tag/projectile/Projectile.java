package com.basementstudios.tag.projectile;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.resource.SpriteSheet;

public class Projectile extends Entity {
	private double x, y;
	private double xa, ya;
	private double xStart, yStart;

	private int time;

	public Projectile(double x, double y, double dir) {
		this.x = x;
		this.y = y;

		xStart = x;
		yStart = y;
		xa = dir;
		time = 20 + random.nextInt(20);
		xSpriteIndex = 0;
		ySpriteIndex = 0;
	}

	public void tick() {
		if (--time < 0) {
			level.remove(this);
		}

		xa *= 1.01;
		x += xa;
	}

	public void render(Bitmap bm) {
		int xc = (int) x;
		int yc = (int) y;
		bm.render(getBitmap(), xc, yc, 0xffffff);
	}

	public SpriteSheet getSpriteSheet() {
		return ResourceManager.i.particlesSpriteSheet;
	}
}