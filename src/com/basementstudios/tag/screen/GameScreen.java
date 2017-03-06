package com.basementstudios.tag.screen;

import com.basementstudios.network.CharacterData;

import com.basementstudios.tag.*;
import com.basementstudios.tag.controller.*;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.level.*;
import com.basementstudios.tag.mob.Mob;

/**
 * The main screen. Is a screen representation for the game.
 *
 * @author Jaspreet Dhanjan
 */

public class GameScreen extends Screen {
	private Level level;
	private Bitmap hudBitmap;

	public GameScreen(Level level) {
		this.level = level;
	}

	public void init() {
		level.init();
		hudBitmap = Bitmap.load("/level/hud.png");
	}

	public void tick(Input input) {
		if (input.esc.isClicked()) {
			screenManager.setScreen(new PauseScreen());
		}
		level.tick();

		PlayerController player = level.getPlayer();
		if (input.num1.isClicked()) player.select(Controller.MOB_1);
		if (input.num2.isClicked()) player.select(Controller.MOB_2);
		if (input.num3.isClicked()) player.select(Controller.MOB_3);

		EnemyController enemy = level.getEnemy();
		if (input.num7.isClicked()) enemy.select(Controller.MOB_1);
		if (input.num8.isClicked()) enemy.select(Controller.MOB_2);
		if (input.num9.isClicked()) enemy.select(Controller.MOB_3);

		if (input.space.isClicked()) {
			level.fight();
		}
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		level.render(bm);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
		bm.render(hudBitmap, 0, 0, 0xffffff);
		/*		if (gameController.getGameState() == GameController.STATE_PLAYER_ATACK || gameController.getGameState() == GameController.STATE_PLAYER_ATACKING) {
					Mob player = gameController.getPlayerController().getSelectedMob();
					Mob enemy = gameController.getEnemyController().getAttackMob();
					mobHud(bm, player, 0, 0);
					mobHud(bm, enemy, 400, 0);
		
					bm.drawString("Use to W and S to select an enemy. Then press enter to atack.", 0, Game.HUD_HEIGHT - 16, 0xffffff);
				} else {
					bm.drawString("Please wait while the enemy takes its turn.", 0, Game.HUD_HEIGHT - 16, 0xffffff);
				}*/
	}

	private void mobHud(Bitmap bm, Mob player, int xStart, int yStart) {
		if (player != null) {
			bm.drawString("Name: " + player.getCharacterData().getName(), xStart, yStart, 0xffffff);
			bm.drawString("Damage " + player.getCharacterData().getDmg(), xStart, yStart + 16, 0xffffff);
			bm.drawString("Defence " + player.getCharacterData().getDef(), xStart, yStart + 32, 0xffffff);
			bm.drawString("Speed " + player.getCharacterData().getSpd(), xStart, yStart + 48, 0xffffff);

			String weaponType = "error";
			switch (player.getCharacterData().getWeaponType()) {
				case CharacterData.NO_WEAPON:
					weaponType = "Fists";
					break;
				case CharacterData.MELEE_WEAPON:
					weaponType = "Melle";
					break;
				case CharacterData.RANGED_WEAPON:
					weaponType = "Ranged";
					break;
				case CharacterData.MAGIC_WEAPON:
					weaponType = "Magic";
					break;
			}

			bm.drawString("Weapon Type " + weaponType, xStart, yStart + 64, 0xffffff);
			if (player.getCharacterData().getSpellDuration() != 0) {
				bm.drawString("Spell Duration " + player.getCharacterData().getSpellDuration(), xStart, yStart + 80, 0xffffff);
			}

			if (player.getDebuffDuration() != 0) {
				bm.drawString("Debuff", xStart, yStart + 96, 0x85d19b);
				bm.drawString(player.getDebuffDamage() + " damage for " + player.getDebuffDuration() + " turns", xStart, yStart + 112, 0x85d19b);
			}
		}
	}

	public boolean isLive() {
		return true;
	}
}