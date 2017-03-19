package com.basementstudios.tag.mob;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Player extends Mob {
	public Player(double x, double y, double xSize, double ySize, CharacterData characterData) {
		super(x, y, xSize, ySize, characterData);
		xSpriteIndex = 0;
		ySpriteIndex = 0;
	}

	public void tick() {
		super.tick();
	}

	public void movePlayer() {
		switch (attackState) {
			case Mob.FORWARD_ATTACK:
				xa = 2;
				if (x - xStart == maxAttackFrame) {
					attackState = Mob.ANIMATION_ATTACK;
				}
				break;
			case Mob.ANIMATION_ATTACK:
				xa = 0;
				if (animationFrame == 4) {
					animationFrame--;
					attackState = RETRACT_ATTACK;
					getTarget().hit(characterData.getDmg());
					getTarget().spellCast(characterData.getDmg(), characterData.getSpellDuration());
				}
				if (turn % 20 == 0) {
					animationFrame++;
					addArrows();
				}
				break;
			case Mob.RETRACT_ATTACK:
				xa = -2;
				if (x - xStart == 0) {
					attackState = Mob.NO_ATTACK;
					xa = 0;
				}
				break;
		}
		attemptMove();
	}

	private void addArrows() {
		// level.add(new Arrow(x, y, 2));
	}

	public void render(Bitmap bm) {
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
			ySpriteIndex = 0;

			if (isMoving()) {
				xSpriteIndex = (walkDist / 10) % 4;
			}
		}

		super.render(bm);
	}
}