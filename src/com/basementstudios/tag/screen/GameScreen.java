package com.basementstudios.tag.screen;

import java.awt.event.KeyEvent;

import java.util.List;
import java.util.Random;

import com.basementstudios.network.CharacterData;
import com.basementstudios.network.Item;
import com.basementstudios.network.Stat;
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
	private GameController gameController;
	private Random rand = new Random();
	
	public GameScreen(List<CharacterData> selectedCharas) {
		currentLevel = new Level(Game.WIDTH, Game.HEIGHT);
		gameController = new GameController(currentLevel);
		gameController.addPlayers(selectedCharas);
		gameController.addEnemys(10);
		//gameController.loop();
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
	}

	public void renderHud(Bitmap bm, Font font, int xStart, int yStart) {
		if (gameController.getPlayerController().getSelected() == PlayerController.PLAYER_NONE) return;

		super.renderHud(bm, font, xStart, yStart);
		font.draw(bm, "Name: " + gameController.getPlayerController().getSelectedPlayer().getCharacterData().getName(), xStart, yStart + 0 * 12, 0xffffff);
		font.draw(bm, "Health: " + gameController.getPlayerController().getSelectedPlayer().getCharacterData().getCurrentHelth(), xStart, yStart + 1 * 12, 0xffffff);

		int i = 0;
		for (Stat stats : gameController.getPlayerController().getSelectedPlayer().getCharacterData().getStats()) {
			font.draw(bm, stats.getName() + " : " + stats.getValue(), xStart + 200, yStart + i * 12, 0xffffff);
			i++;
		}
		i = 0;
		for(Item item: gameController.getPlayerController().getSelectedPlayer().getCharacterData().getItems()){
			font.draw(bm, item.getName(), xStart, yStart + i+2 * 12, 0xffffff);
			i++;
		}
	}
}