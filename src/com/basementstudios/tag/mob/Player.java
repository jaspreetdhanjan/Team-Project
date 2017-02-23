package com.basementstudios.tag.mob;

import com.basementstudios.tag.graphics.*;
import com.basementstudios.network.*;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Player extends Mob {
	private final CharacterData characterData;

	public Player(double x, double y, CharacterData characterData) {
		super(x, y);
		this.characterData = characterData;
		dmg = characterData.getDmg();
		def = characterData.getDef();
		spd = characterData.getSpd();
		spellDuration = characterData.getSpellDuration();
		wepponType = characterData.getWepponType();
		health = characterData.getCurrentHealth();
		maxHealth = characterData.getMaxHealth();
		name = characterData.getName();

		xSpriteIndex = 0;
		ySpriteIndex = 0;

		xs = 13 + 16;
		ys = 26 + 16;

	}

	public void tick() {
		super.tick();
	}

	public CharacterData getCharacterData() {
		return characterData;
	}

	public void movePlayer() {
		if (isAttacking) {
			if (x - xStart == 0 && isRetracting) {
				isAttacking = false;
				xa = 0;
			} else if (x - xStart == maxAttackFrame && !isRetracting) {
				isRetracting = true;
				getTarge().hit(getDmg());
				getTarge().spellCast(getDmg(), getSpellDuration());
			} else if (isRetracting)
				xa = -1;
			else
				xa = 1;
			attemptMove();
		}
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
		bm.render(SpriteSheet.chars[xSpriteIndex][ySpriteIndex], xp, yp, colour);

		font.draw(bm, health + "/" + maxHealth, xp, yp + 32, 0xff0000);
		font.draw(bm, name, xp, yp - 5, 0x000000);
	}
}