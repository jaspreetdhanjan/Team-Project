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

	private boolean move(AxisAlignedBB newBB) {
		if (isRemoved()) return false;

		// TODO: this is broken, check y clipping.
		// if (!level.getBB().contains(newBB)) {
		// collide(null, newBB);
		// return false;
		// }

		lastWalkDist = walkDist;
		walkDist++;
		bb.set(newBB);
		return true;
	}

	protected void collide(Entity cause, AxisAlignedBB newBB) {
		xa = 0;
		ya = 0;
	}

	public boolean isMoving() {
		return walkDist != lastWalkDist;
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