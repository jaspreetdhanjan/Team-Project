package com.basementstudios.tag.screen;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Game;
import com.basementstudios.tag.GameController;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;

/**
 * The main screen. Is a screen representation for the game.
 *
 * @author Jaspreet Dhanjan
 */

public class GameScreen extends Screen {
	private Level level;
	private Bitmap hudBitmap;
	private GameController gameController;

	public GameScreen(Level level) {
		this.level = level;
		gameController = new GameController(level);
		gameController.addEnemys(1);
		gameController.addPlayers();
	}

	public void init() {
		level.init();
		hudBitmap = Bitmap.load("/level/hud.png");
		gameController.startGame();
		gameController.setScreenManager(screenManager);
	}

	public void tick(Input input) {
		if (input.esc.isClicked()) {
			screenManager.setScreen(new PauseScreen());
		}
		level.tick();
		gameController.tick(input);
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		level.render(bm);
		gameController.render(bm);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
		bm.render(hudBitmap, 0, 0, 0xffffff);

		if (gameController.getGameState() == GameController.STATE_PLAYER_ATACK || gameController.getGameState() == GameController.STATE_PLAYER_ATACKING) {
			Mob player = gameController.getPlayerController().getSelectedMob();
			Mob enemy = gameController.getEnemyController().getAttackMob();
			mobHud(bm, player, 12, 12);
			mobHud(bm, enemy, 417, 12);

			bm.drawString("Use to W and S to select an enemy. Then press enter to attack.", 12, Game.HUD_HEIGHT - 23, 0xffffff);
				} else {
			bm.drawString("Please wait while the enemy takes its turn.", 12, Game.HUD_HEIGHT - 23, 0xffffff);
		}
	}

	public void mobHud(Bitmap bm, Mob player, int xStart, int yStart) {
		if (player != null) {
			bm.drawString("Name: " + player.getCharacterData().getName(), xStart, yStart, 0xffffff);
			bm.drawString("Damage " + player.getCharacterData().getDmg(), xStart, yStart + 16, 0xffffff);
			bm.drawString("Defence " + player.getCharacterData().getDef(), xStart, yStart + 32, 0xffffff);
			bm.drawString("Speed " + player.getCharacterData().getSpd(), xStart, yStart + 48, 0xffffff);

			String weaponType = "error";
			switch (player.getCharacterData().getWeaponType()) {
				case CharacterData.NO_WEAPON:
					weaponType = "Paper Knife";
					break;
				case CharacterData.MELEE_WEAPON:
					weaponType = "Melee";
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