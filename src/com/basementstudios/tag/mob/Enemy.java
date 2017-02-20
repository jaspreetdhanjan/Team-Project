package com.basementstudios.tag.mob;

import com.basementstudios.tag.component.*;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.network.*;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Enemy extends Mob {

	public boolean isAttacking = false;
	public boolean isRetracting = false;
	public int maxAttackFrame;
	private int dmg, def, spd, spellDuration, wepponType,health;

	public Enemy(double x, double y, int dmg, int def,int spd, int spellDuration,int wepponType, int health) {
		super(x, y);
		this.dmg=dmg;
		this.def=def;
		this.spd=spd;
		this.spellDuration=spellDuration;
		this.wepponType=wepponType;
		this.health=health;
		xSpriteIndex = 0;
		ySpriteIndex = 0;

		xs = 13 + 16;
		ys = 26 + 16;

	}

	public void tick() {
		super.tick();
	}


	public void render(Bitmap bm) {
		int colour = 0xffffff;
		if (hitTime > 0) {
			hitTime--;
			colour = 0xcc0000;
		}

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

		int xp = (int) x;
		int yp = (int) y;
		bm.render(SpriteSheet.enemy[xSpriteIndex][ySpriteIndex], xp, yp, colour);
	}


	public void startAttack(int maxAttackFrame) {
		isAttacking = true;
		isRetracting = false;
		this.maxAttackFrame = maxAttackFrame;

	}
	
	public int getDmg() {
		return dmg;
	}

	public int getDef() {
		return def;
	}

	public int getSpd() {
		return spd;
	}

	public int getSpellDuration() {
		return spellDuration;
	}

	public int getWepponType() {
		return wepponType;
	}

	public int getHealth() {
		return health;
	}
}