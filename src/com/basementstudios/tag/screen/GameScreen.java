package com.basementstudios.tag.screen;

import java.awt.event.KeyEvent;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.network.CharacterStat;
import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.level.*;

/**
 * The main screen. Is a screen representation for the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class GameScreen extends Screen {
	private Level currentLevel;
	private PlayerController playerController = new PlayerController();
	
	public GameScreen(List<CharacterData> selectedCharas) {
		currentLevel = new Level(Game.WIDTH, Game.HEIGHT);
		playerController.addPlayers(currentLevel, 50, 100, selectedCharas);
	}

	public void init() {
	}

	public void tick(Input input) {
		currentLevel.tick();

		double xa = 0;
		if (input.isDown(KeyEvent.VK_A)) xa--;
		if (input.isDown(KeyEvent.VK_D)) xa++;
		playerController.attemptMove(xa, 0);

		if (input.isDown(KeyEvent.VK_1)) playerController.select(PlayerController.PLAYER_1);
		if (input.isDown(KeyEvent.VK_2)) playerController.select(PlayerController.PLAYER_2);
		if (input.isDown(KeyEvent.VK_3)) playerController.select(PlayerController.PLAYER_3);
	}

	public void renderScene(Bitmap bm) {
		bm.fill(0, 0, bm.width, bm.height, 0xf5deb3);

		currentLevel.render(bm);
		playerController.render(bm);
	}

	public void renderHud(Bitmap bm, Font font, int xStart, int yStart) {
		if (playerController.getSelected() == PlayerController.PLAYER_NONE) return;

		super.renderHud(bm, font, xStart, yStart);
		font.draw(bm, "Name: " + playerController.getSelectedPlayer().getCharacterData().getName(), xStart, yStart + 0 * 12, 0xffffff);
		font.draw(bm, "Health: " + playerController.getSelectedPlayer().getCharacterData().getCurrentHelth(), xStart, yStart + 1 * 12, 0xffffff);

		int i = 0;
		for (CharacterStat stats : playerController.getSelectedPlayer().getCharacterData().getStats()) {
			font.draw(bm, stats.getName() + " : " + stats.getValue(), xStart + 200, yStart + i * 12, 0xffffff);
			i++;
		}
	}
}