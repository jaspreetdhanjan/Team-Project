package com.basementstudios.tag.mob;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.particle.TextParticle;

public class Mob extends Entity {
	public int health = 10;
	public int hitTime = 0;
	public int walkDist = 0;

	public boolean moving = false;

	public Mob(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void attemptMove() {
		double xxa = x + xa;
		double yya = y + ya;
		move(xxa, yya);
	}

	private void move(double xxa, double yya) {
		if (isRemoved()) return;
		if (moving) moving = false;

		double x0 = xxa + xr0;
		double y0 = yya + yr0;
		double x1 = xxa + xr1;
		double y1 = yya + yr1;

		if (x0 < 0 || y0 < 0 || x1 >= (level.width << 4) || y1 >= (level.height << 4)) {
			collide(null, xxa, yya);
			return;
		}

		x = xxa;
		y = yya;
		walkDist++;
		moving = true;
	}

	public boolean blocks(Entity e) {
		return true;
	}

/*	private boolean checkBlock(Block nextBlock, int xb, int yb, double xxa, double yya) {
		if (nextBlock.getDamage() != 0) {
			hurt(null, nextBlock.getDamage());
			return true;
		}
		if (nextBlock.blocks(this)) {
			collide(null, xxa, yya);
			return true;
		}
		return false;
	}*/

	public void hurt(Entity hurtBy, int dmg) {
		int colour = 0xff0000;

		health -= dmg;
		hitTime = 15;
		knockback(dmg);
		level.add(new TextParticle("-" + dmg, x, y, 2, colour));

		if (health <= 0) {
			onDied();
		}
	}

	public void onDied() {
		remove();
	}

	private void knockback(int dmg) {
		double m = dmg * 15;

		double xKnockback = (-xa * m);
		double yKnockback = (-ya * m);
		if (xKnockback != 0 || yKnockback != 0) {
			xa = xKnockback * 15.0;
			ya = yKnockback * 15.0;
			attemptMove();
		}
	}

	public void collide(Entity otherEntity, double xxa, double yya) {
		if (xxa != 0) xa = 0;
		if (yya != 0) ya = 0;
	}
}