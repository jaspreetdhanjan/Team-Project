package com.basementstudios.tag.mob;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Entity;
import com.basementstudios.tag.particle.TextParticle;

/**
 * A moving and dynamic character within the game.
 * 
 * @author Jaspreet Dhanjan
 */

// Combat will eventually be replaced and fix, these are some placeholder classes for when Nick is finished...
public class Mob extends Entity {
	private final CharacterData characterData;

	public int hitTime = 0;
	public int lastWalkDist, walkDist;

	public Mob(double x, double y, CharacterData characterData) {
		this.x = x;
		this.y = y;
		this.characterData = characterData;
	}

	public void attemptMove() {
		double xxa = x + xa;
		double yya = y + ya;
		move(xxa, yya);
	}

	private void move(double xxa, double yya) {
		if (isRemoved()) return;

		lastWalkDist = walkDist;

		double x0 = bb.x;
		double y0 = bb.y;
		double x1 = bb.x + bb.xSize;
		double y1 = bb.y + bb.ySize;

		if (x0 < 0 || y0 < 0 || x1 >= level.getWidth() || y1 >= level.getHeight()) {
			collide(null, xxa, yya);
			return;
		}

		x = xxa;
		y = yya;
		walkDist++;
		bb.set(x, y, xs, ys);
	}

	public boolean isMoving() {
		return walkDist != lastWalkDist;
	}

	public boolean blocks(Entity e) {
		return true;
	}

	public void hurt(Entity hurtBy, int dmg) {
		int colour = 0xff0000;

		hitTime = 15;
		knockback(dmg);
		level.add(new TextParticle("-" + dmg, x, y, 2, colour));
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

	public CharacterData getCharacterData() {
		return characterData;
	}
}