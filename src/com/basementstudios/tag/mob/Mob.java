package com.basementstudios.tag.mob;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.phys.AxisAlignedBB;

/**
 * A moving and dynamic character within the game.
 * 
 * @since Version 1.0 combat removed.
 * 
 * @author Jaspreet Dhanjan
 */

public class Mob extends Entity {
	private final CharacterData characterData;

	protected AxisAlignedBB bb = new AxisAlignedBB();
	protected int lastWalkDist, walkDist;
	public double xa, ya;

	public Mob(double x, double y, double xSize, double ySize, CharacterData characterData) {
		bb.set(x, y, xSize, ySize);
		this.characterData = characterData;
	}

	public void attemptMove() {
		double xxa = bb.xPos + xa;
		double yya = bb.yPos + ya;
		AxisAlignedBB newBB = new AxisAlignedBB(xxa, yya, bb.xSize, bb.ySize);

		move(newBB);
	}

	private void move(AxisAlignedBB newBB) {
		if (isRemoved()) return;

		if (!level.bb.contains(newBB)) {
			collide(null, newBB);
			return;
		}

		lastWalkDist = walkDist;
		walkDist++;
		bb.set(newBB);
	}

	private void collide(Entity cause, AxisAlignedBB newBB) {
		xa = 0;
		ya = 0;
	}

	public boolean isMoving() {
		return walkDist != lastWalkDist;
	}

	public void collide(Entity otherEntity, double xxa, double yya) {
		if (xxa != 0) xa *= -10;
		if (yya != 0) ya *= -10;
	}

	public void render(Bitmap bm) {
		int xp = (int) bb.xPos;
		int yp = (int) bb.yPos;
		bm.render(getBitmap(), xp, yp, colour);
	}

	public CharacterData getCharacterData() {
		return characterData;
	}

	public AxisAlignedBB getBB() {
		return bb;
	}
}