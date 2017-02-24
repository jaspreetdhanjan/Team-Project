package com.basementstudios.tag.screen;

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
	private PlayerController playerController;

	public GameScreen(Level level) {
		this.level = level;
		playerController = new PlayerController(level, 50, 100);
	}

	public void init() {
	}

	public void tick(Input input) {
		if (input.esc.isClicked()) {
			screenManager.setScreen(new PauseScreen());
		}

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

	public void renderScreen(Bitmap bm) {
		bm.clear();

		level.render(bm);
		playerController.render(bm);
	}

	public void renderHud(Bitmap bm) {
		bm.clear();

		if (playerController.getSelectedPlayer() == null) return;
		bm.drawString("Name: " + playerController.getSelectedPlayer().getCharacterData().getName(), 0, 0 + 0 * 12, 0xffffff);
		bm.drawString("Health: " + playerController.getSelectedPlayer().getCharacterData().getCurrentHealth(), 0, 0 + 1 * 12, 0xffffff);

		/*	int i = 0;
			for (CharacterStat stats : playerController.getSelectedPlayer().getCharacterData().getStats()) {
				bm.drawString(stats.getName() + " : " + stats.getValue(), xStart + 200, yStart + i * 12, 0xffffff);
				i++;
			}*/
	}

	public boolean isLive() {
		return true;
	}
}