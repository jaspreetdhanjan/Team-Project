package com.basementstudios.tag.particle;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.resource.SpriteSheet;

/**
 * Generates a random particle.
 * 
 * @author Jaspreet Dhanjan
 */

public class Particle extends Entity {
	public double x, y, z;
	public double xa, ya, za;
	public int life;
	public double bounce = 0.6;
	public double gravity = 0.15;
	public double drag = 0.998;

	public Particle(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		life = random.nextInt(20) + 40;

		do {
			xa = random.nextDouble() * 2 - 1;
			ya = random.nextDouble() * 2 - 1;
			za = random.nextDouble() * 2 - 1;
		} while (xa * xa + ya * ya + za * za > 1);
		double dd = Math.sqrt(xa * xa + ya * ya + za * za);
		double speed = 2;
		xa = xa / dd * speed;
		ya = (ya / dd * speed);
		za = (za / dd + 1.0) * speed;
	}

	public void tick() {
		if (--life < 0) {
			remove();
		}

		boolean onGround = z <= 1;
		if (onGround) {
			xa *= 0.85;
			ya *= 0.85;
		} else {
			xa *= drag;
			ya *= drag;
		}

		za -= gravity;
		tryMove();
	}

	private void tryMove() {
		double xxa = x + xa;
		double yya = y + ya;
		double zza = z + za;

		x = xxa;
		y = yya;
		z = zza;
	}

	public void render(Bitmap bm) {
		int xp = (int) x;
		int yp = (int) (y - z);
		bm.render(getBitmap(), xp, yp, 0xffffff);
	}
	
	public SpriteSheet getSpriteSheet() {
		return ResourceManager.i.particlesSpriteSheet;
	}

	public void collide(Entity otherEntity, double xxa, double yya, double zza) {
		if (xxa != 0) xa *= -bounce;
		if (yya != 0) ya *= -bounce;
		if (zza != 0) za *= -bounce;
	}
}