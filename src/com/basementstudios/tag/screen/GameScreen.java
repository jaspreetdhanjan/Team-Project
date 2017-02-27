package com.basementstudios.tag.screen;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.*;
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
	private GameController gameController;

	public GameScreen(Level level) {
		this.level = level;
		gameController = new GameController(level);
		gameController.addPlayers();
		gameController.addEnemys(1);
		gameController.startGame();
	}

	public void init() {
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
		if (gameController.getGameState() == GameController.STATE_PLAYER_ATACK
				|| gameController.getGameState() == GameController.STATE_PLAYER_ATACKING) {
			Mob player = gameController.getPlayerController().getSelectedMob();
			Mob enemy = gameController.getEnemyController().getAttackMob();
			mobHud(bm, player, 0, 0);
			mobHud(bm, enemy, 200, 0);

			bm.drawString("Use to W and S to cycle though a character to attack.", 0, 75, 0xffffff);
			bm.drawString("Then use enter to do so.", 0, 85, 0xffffff);
		} else {
			bm.drawString("Please wait while the enemy takes its turn.", 0, 70, 0xffffff);
		}
	}

	private void mobHud(Bitmap bm, Mob player, int xStart, int yStart) {
		if (player != null) {
			bm.drawString("Damage " + player.getDmg(), xStart, yStart, 0xffffff);
			bm.drawString("Defence " + player.getDef(), xStart, yStart + 10, 0xffffff);
			bm.drawString("Speed " + player.getSpd(), xStart, yStart + 20, 0xffffff);

			String weponType;
			switch (player.getWepponType()) {
			case CharacterData.NO_WEAPON:
				weponType = "Fists";
				break;
			case CharacterData.MELEE_WEAPON:
				weponType = "Melle";
				break;
			case CharacterData.RANGED_WEAPON:
				weponType = "Ranged";
				break;
			case CharacterData.MAGIC_WEAPON:
				weponType = "Magic";
				break;
			default:
				weponType = "Im not gonna ask";
				break;
			}

			bm.drawString("Wepon Type " + weponType, xStart, yStart + 35, 0xffffff);
			if (player.getSpellDuration() != 0) {
				bm.drawString("Spell Duration " + player.getSpellDuration(), xStart, yStart + 45, 0xffffff);
			}

			if (player.getDebuffDuration() != 0) {
				bm.drawString("Debuff", xStart, yStart + 55, 0x85d19b);
				bm.drawString(player.getDebuffDamage() + " damage for " + player.getDebuffDuration() + " turns", xStart,
						yStart + 65, 0x85d19b);
			}
		}
	}

	public boolean isLive() {
		return true;
	}
}