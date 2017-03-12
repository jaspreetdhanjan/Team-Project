package com.basementstudios.tag.mob;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * The enemy representation within the game.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public class Enemy extends Mob {
	public Enemy(double x, double y, CharacterData characterData) {
		super(x, y, 128, 128, characterData);

		xSpriteIndex = 0;
		ySpriteIndex = 0;
	}

	public void tick() {
		super.tick();
	}

	public void render(Bitmap bm) {
		colour = 0xffffff;
		if (xa == 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 1;
		} else if (xa < 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 1;

			if (isMoving()) {
				xSpriteIndex = (walkDist / 10) % 5;
			}
		} else if (xa > 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 0;

			if (isMoving()) {
				xSpriteIndex = (walkDist / 10) % 5;
			}
		}
		super.render(bm);
	}

}