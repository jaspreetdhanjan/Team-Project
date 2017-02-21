package com.basementstudios.tag.screen;

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
	private Level level;
	private PlayerController playerController = new PlayerController();

	public GameScreen(List<CharacterData> selectedCharas, Level level) {
		this.level = level;
		playerController.addPlayers(level, 50, 100, selectedCharas);
	}

	public void init() {
	}

	public void tick(Input input) {
		level.tick();

		double xa = 0;
		double ya = 0;
		if (input.left.isDown()) xa--;
		if (input.right.isDown()) xa++;
		playerController.attemptMove(xa, ya);

		if (input.num1.isDown()) playerController.select(PlayerController.PLAYER_1);
		if (input.num2.isDown()) playerController.select(PlayerController.PLAYER_2);
		if (input.num3.isDown()) playerController.select(PlayerController.PLAYER_3);
	}

	public void renderScene(Bitmap bm) {
		bm.clear();

		level.render(bm);
		playerController.render(bm);
	}

	public void renderHud(Bitmap bm, Font font, int xStart, int yStart) {
		if (playerController.getSelectedPlayer() == null) return;

		super.renderHud(bm, font, xStart, yStart);
		font.draw(bm, "Name: " + playerController.getSelectedPlayer().getCharacterData().getName(), xStart, yStart + 0 * 12, 0xffffff);
		font.draw(bm, "Health: " + playerController.getSelectedPlayer().getCharacterData().getCurrentHealth(), xStart, yStart + 1 * 12, 0xffffff);

		int i = 0;
		for (CharacterStat stats : playerController.getSelectedPlayer().getCharacterData().getStats()) {
			font.draw(bm, stats.getName() + " : " + stats.getValue(), xStart + 200, yStart + i * 12, 0xffffff);
			i++;
		}
	}

	public boolean isLive() {
		return false;
	}
}