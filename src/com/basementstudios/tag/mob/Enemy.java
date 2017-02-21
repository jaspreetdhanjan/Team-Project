package com.basementstudios.tag.mob;

import com.basementstudios.tag.graphics.*;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Enemy extends Mob {

	public boolean isAttacking = false;
	public boolean isRetracting = false;
	public int maxAttackFrame;
	private Font font =Font.getInstance();

	public Enemy(double x, double y, int dmg, int def,int spd, int spellDuration,int wepponType, int health, String name) {
		super(x, y);
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
		
		font.draw(bm, health+"/"+maxHealth, xp, yp+32,  0xff0000);
		font.draw(bm, name, xp, yp-5,  0x000000e);
	}

}