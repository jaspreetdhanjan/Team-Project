package com.basementstudios.tag.mob;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.resource.SpriteSheet;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Player extends Mob {
	private int shootTime = 0;

	public Player(double x, double y, CharacterData characterData) {
		super(x, y, 128, 128, characterData);
		xSpriteIndex = 0;
		ySpriteIndex = 0;
	}

	public void tick() {
		super.tick();
		
		if (shootTime > 0) {
			shootTime--;
		}
	}

	public void render(Bitmap bm) {
		if (xa == 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 0;
		} else if (xa < 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 1;

			if (isMoving()) {
				xSpriteIndex = (walkDist / 7) % 5;
			}
		} else if (xa > 0) {
			xSpriteIndex = 0;
			ySpriteIndex = 0;

			if (isMoving()) {
				xSpriteIndex = (walkDist / 7) % 5;
			}
		}

		super.render(bm);
	}

	public SpriteSheet getSpriteSheet() {
		return ResourceManager.i.playerSpriteSheet;
	}
}