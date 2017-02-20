package com.basementstudios.tag.mob;

import com.basementstudios.tag.component.*;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.network.*;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Player extends Mob {
	private final CharacterData characterData;

	private AIAttackComponent attackComponent = new AIAttackComponent(this);
	private int shootTime = 0;
	public boolean isAttacking = false;
	public boolean isRetracting = false;
	public int maxAttackFrame;

	public Player(double x, double y, CharacterData characterData) {
		super(x, y);
		this.characterData = characterData;

		xSpriteIndex = 0;
		ySpriteIndex = 0;

		xs = 13 + 16;
		ys = 26 + 16;

		addComponent(attackComponent);
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
		bm.render(SpriteSheet.chars[xSpriteIndex][ySpriteIndex], xp, yp, colour);
	}

	public CharacterData getCharacterData() {
		return characterData;
	}

	public void startAttack(int maxAttackFrame) {
		isAttacking = true;
		isRetracting = false;
		this.maxAttackFrame = maxAttackFrame;

	}
}