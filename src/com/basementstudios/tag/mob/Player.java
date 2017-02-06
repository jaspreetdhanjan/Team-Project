package com.basementstudios.tag.mob;

import com.basementstudios.tag.component.*;
import com.basementstudios.tag.graphics.*;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Player extends Mob {
	private AIAttackComponent attackComponent = new AIAttackComponent(this);
	private int shootTime = 0;

	public Player(double x, double y) {
		super(x, y);
		xSpriteIndex = 0;
		ySpriteIndex = 0;

		xs = 13 + 16;
		ys = 26 + 16;

		addComponent(attackComponent);
	}

	public void tick() {
		super.tick();

		if (shootTime > 0) {
			shootTime--;
		}
	}

	public void attemptShoot() {
		if (shootTime != 0) return;
		shootTime = 10;

		attackComponent.tryAttack(240.0);
	}

	public void render(Bitmap bm) {
		int colour = 0xffffff;
		if (hitTime > 0) {
			hitTime--;
			colour = 0xcc0000;
		}

		xSpriteIndex = 0;
		if (xa != 0) xSpriteIndex = 2;

		if (xa < 0) bm.xFlip = true;
		if (xa > 0) bm.xFlip = false;

		int xp = (int) x;
		int yp = (int) y;
		bm.render(SpriteSheet.entities[xSpriteIndex][ySpriteIndex + 0], xp, yp, colour);
		bm.render(SpriteSheet.entities[xSpriteIndex][ySpriteIndex + 1], xp, yp + 16, colour);
		if (bm.xFlip == true) bm.xFlip = false;
	}
}