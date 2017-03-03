package com.basementstudios.tag.mob;

import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.component.*;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.resource.SpriteSheet;
import com.basementstudios.network.*;
import com.basementstudios.network.CharacterData;

/**
 * The player representation within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Player extends Mob {
	private int shootTime = 0;
	private final CharacterData characterData;

	public Player(double x, double y, CharacterData characterData) {
		super(x, y, 64, 64);
		this.characterData = characterData;
		characterData.calculateBattleStats();
		dmg = characterData.getDmg();
		def = characterData.getDef();
		spd = characterData.getSpd();
		spellDuration = characterData.getSpellDuration();
		wepponType = characterData.getWeaponType();
		health = characterData.getCurrentHealth();
		maxHealth = characterData.getMaxHealth();
		name = characterData.getName();

		xSpriteIndex = 0;
		ySpriteIndex = 0;
	}

	public void tick() {
		super.tick();

		if (shootTime > 0) {
			shootTime--;
		}
	}

	public void movePlayer() {
		if (isAttacking) {
			if (bb.xPos - xStart == 0 && isRetracting) {
				isAttacking = false;
				xa = 0;
			} else if (bb.xPos - xStart == maxAttackFrame && !isRetracting) {
				isRetracting = true;
				getTarge().hit(getDmg());
				getTarge().spellCast(getDmg(), getSpellDuration());
			} else if (isRetracting)
				xa = -2;
			else
				xa = 2;
			attemptMove();
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
	
	public SpriteSheet getSpriteSheet() {
		return ResourceManager.i.newCharacterSpriteSheet;
	}
	
	public CharacterData getCharacterData() {
		return characterData;
	}

}