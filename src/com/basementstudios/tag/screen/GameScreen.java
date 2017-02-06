package com.basementstudios.tag.screen;

import java.awt.event.KeyEvent;

import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Player;
import com.basementstudios.tag.mob.TestEnemy;

public class GameScreen extends Screen {
	private Level currentLevel;
	private Player player = new Player(50, 250);

	public void init() {
		//currentLevel = new Level(SpriteSheet.loadBitmap("/maps/l0.png", 40, 20)[0][0]);
		currentLevel = new Level(new Bitmap(40, 20));
		currentLevel.add(player);

		currentLevel.add(new TestEnemy(150, 250));
	}

	public void tick(Input input) {
		currentLevel.tick();

		player.xa = 0;
		if (input.isDown(KeyEvent.VK_A)) player.xa--;
		if (input.isDown(KeyEvent.VK_D)) player.xa++;
		if (input.isDown(KeyEvent.VK_P)) player.attemptShoot();

		player.attemptMove();
		if (player.isRemoved()) {
			game.setScreen(new DeathScreen(currentLevel));
		}
	}

	public void render(Bitmap bm) {
		bm.fill(0, 0, bm.width, bm.height, bm.hex(153, 204, 255));
		currentLevel.render(bm);
	}
}