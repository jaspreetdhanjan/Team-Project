package com.basementstudios.tag.mob;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Entity;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.resource.SpriteSheet;

/**
 * A moving and dynamic character within the game.
 *
 * @author Jaspreet Dhanjan
 */

public class Mob extends Entity {
	private final CharacterData characterData;

	protected double x, y;
	protected double xa, ya;

	protected int lastWalkDist, walkDist;

	protected double xSize, ySize;
	protected double newX, newY;

	protected boolean hasGone = false;
	protected boolean isAttacking = false;
	protected boolean isRetracting = false;
	protected int maxAttackFrame;

	protected int debuffDamage, debuffDuration;
	protected Mob target = null;

	public Mob(double x, double y, double xSize, double ySize, CharacterData characterData) {
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
		this.characterData = characterData;
		newX = x;
		newY = y;
	}

	public void setDelta(double xa, double ya) {
		this.xa = xa;
		this.ya = ya;
	}

	public void moveTo(double newX, double newY) {
		this.newX = newX;
		this.newY = newY;
	}

	public void tick() {
		if (Math.abs(Math.floor(newX - x)) > 50 || Math.abs(Math.floor(newY - y)) > 50) {
			if (x < newX) xa++;
			if (x > newX) xa--;
			if (y < newY) ya++;
			if (y > newY) ya--;
		}

		if (xa != 0 || ya != 0) {
			attemptMove();
		}

	}

	public void attemptMove() {
		double xxa = x + xa;
		double yya = y + ya;

		move(xxa, yya);
		// move(0, yya);
	}

	private boolean move(double xxa, double yya) {
		if (isRemoved()) return false;

		lastWalkDist = walkDist;
		walkDist++;
		x = xxa;
		y = yya;
		return true;
	}

	protected void collide(Entity cause, double xxa, double yya) {
		xa = 0;
		ya = 0;
	}

	public boolean isMoving() {
		return walkDist != lastWalkDist;
	}

	public void render(Bitmap bm) {
		int xp = (int) x;
		int yp = (int) y;
		bm.render(getBitmap(), xp, yp, colour);

		// Draw the health bar
		bm.fill(xp + 40, yp - 4, xp + 32 + (characterData.getMaxHealth() / 2), yp, 0xff0000);
		bm.fill(xp + 40, yp - 4, xp + 32 + (characterData.getCurrentHealth() / 2), yp, 0xff00);
	}

	public void onDied() {
		remove();
	}

	public Mob getTarget() {
		return target;
	}

	public int getDebuffDamage() {
		return debuffDamage;
	}

	public int getDebuffDuration() {
		return debuffDuration;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public final CharacterData getCharacterData() {
		return characterData;
	}

	public SpriteSheet getSpriteSheet() {
		switch (getCharacterData().getType()) {
			case CharacterData.ROGUE_TYPE:
				return ResourceManager.i.rogueSpriteSheet;
			case CharacterData.MAGE_TYPE:
				return ResourceManager.i.mageSpriteSheet;
			case CharacterData.NECRO_TYPE:
				return ResourceManager.i.necromancerSpriteSheet;
			case CharacterData.KNIGHT_TYPE:
				return ResourceManager.i.knightSpriteSheet;
		}
		return ResourceManager.i.rogueSpriteSheet;
	}
}