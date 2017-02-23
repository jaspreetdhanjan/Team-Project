package com.basementstudios.tag.screen;

import java.util.List;
import com.basementstudios.network.CharacterData;
import com.basementstudios.network.Item;
import com.basementstudios.network.Stat;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.level.*;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.mob.Player;

/**
 * The main screen. Is a screen representation for the game.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public class GameScreen extends Screen {
	private Level currentLevel;
	private GameController gameController;
	private Font font = Font.getInstance();

	public GameScreen(List<CharacterData> selectedCharas, Game game) {
		currentLevel = new Level(Game.WIDTH, Game.HEIGHT);
		gameController = new GameController(currentLevel, game);
		gameController.addPlayers(selectedCharas);
		gameController.addEnemys(1);
		gameController.startGame();
	}

	public void init() {
	}

	public void tick(Input input) {
		currentLevel.tick();
		gameController.tick(input);
	}

	public void renderScene(Bitmap bm) {
		bm.fill(0, 0, bm.width, bm.height, 0xf5deb3);

		currentLevel.render(bm);
		gameController.getPlayerController().render(bm);
		gameController.getEnemyController().render(bm);
		font.draw(bm, "Turn " + gameController.getTurn(), bm.width - 50, 10, 0x000000);
	}

	public void renderHud(Bitmap bm, Font font, int xStart, int yStart) {
		super.renderHud(bm, font, xStart, yStart);
		if (gameController.getGameState()==GameController.STATE_PLAYER_ATACK||gameController.getGameState()==GameController.STATE_PLAYER_ATACKING) {
			Mob player = gameController.getPlayerController().getSelectedMob();
			Mob enemy = gameController.getEnemyController().getAttackMob();
			mobHud(bm, player, xStart, yStart);
			mobHud(bm, enemy, xStart + 200, yStart);

			font.draw(bm, "Use to W and S to cycle though a character to attack.", xStart, yStart + 75, 0xffffff);
			font.draw(bm, "Then use enter to do so.", xStart, yStart + 85, 0xffffff);
		} else {
			font.draw(bm, "Please wait while the enemy takes its turn.", xStart, yStart + 70, 0xffffff);
		}
	}

	private void mobHud(Bitmap bm, Mob player, int xStart, int yStart) {
		if (player != null) {
			font.draw(bm, "Damage " + player.getDmg(), xStart, yStart, 0xffffff);
			font.draw(bm, "Defence " + player.getDef(), xStart, yStart + 10, 0xffffff);
			font.draw(bm, "Speed " + player.getSpd(), xStart, yStart + 20, 0xffffff);

			String weponType;
			switch (player.getWepponType()) {
			case CharacterData.NO_WEPPON_ID:
				weponType = "Fists";
				break;
			case CharacterData.MELLE_ID:
				weponType = "Melle";
				break;
			case CharacterData.RANGED_ID:
				weponType = "Ranged";
				break;
			case CharacterData.MAGIC_ID:
				weponType = "Magic";
				break;
			default:
				weponType = "Im not gonna ask";
				break;
			}

			font.draw(bm, "Wepon Type " + weponType, xStart, yStart + 35, 0xffffff);
			if (player.getSpellDuration() != 0) {
				font.draw(bm, "Spell Duration " + player.getSpellDuration(), xStart, yStart + 45, 0xffffff);
			}

			if (player.getDebuffDuration() != 0) {
				font.draw(bm, "Debuff", xStart, yStart + 55, 0x85d19b);
				font.draw(bm, player.getDebuffDamage() + " damage for " + player.getDebuffDuration() + " turns", xStart,
						yStart + 65, 0x85d19b);
			}
		}
	}
}
