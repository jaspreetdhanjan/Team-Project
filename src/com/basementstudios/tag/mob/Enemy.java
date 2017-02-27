package com.basementstudios.tag.mob;

import com.basementstudios.tag.graphics.*;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public class Enemy extends Mob {
	public Enemy(double x, double y, int dmg, int def,int spd, int spellDuration,int wepponType, int health, String name) {
		super(x, y,13 + 16, 26 + 16);
		super.dmg=dmg;
		super.def=def;
		super.spd=spd;
		super.spellDuration=spellDuration;
		super.wepponType=wepponType;
		super.health=health;
		super.maxHealth=health;
		super.name=name;
		xSpriteIndex = 0;
		ySpriteIndex = 0;

	}

	public void tick() {
		super.tick();
	}

	public void movePlayer() {
		if (isAttacking) {
			if (xStart - bb.xPos == 0 && isRetracting) {
				isAttacking = false;
				xa = 0;
			} else if (xStart - bb.xPos == maxAttackFrame && !isRetracting) {
				isRetracting = true;
				getTarge().hit(getDmg());
				getTarge().spellCast(getDmg(), getSpellDuration());
			} else if (isRetracting)
				xa = 1;
			else
				xa = -1;
			attemptMove();
		}
	}
	
	public void render(Bitmap bm) {
		int colour = 0xffffff;

		if (xa == 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 0;
		} else if (xa < 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 1;

			if (isMoving()) {
				xSpriteIndex = (walkDist / 10) % 4;
			}
		} else if (xa > 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 3;

			if (isMoving()) {
				xSpriteIndex = (walkDist / 10) % 4;
			}
		}
		super.render(bm);
	}
	
	public Bitmap getBitmap() {
		return SpriteSheet.enemy[xSpriteIndex][ySpriteIndex];
	}

}