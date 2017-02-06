package com.basementstudios.tag.screen;

import java.awt.event.KeyEvent;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;
import com.basementstudios.tag.level.Level;

public class DeathScreen extends Screen {
	private Level level;

	public DeathScreen(Level level) {
		this.level = level;
	}

	public void tick(Input input) {
		if (input.isDown(KeyEvent.VK_SPACE)) {
			game.setScreen(new MenuScreen());
			return;
		}
	}

	public void render(Bitmap bm) {
		int xx = 0;
		int yy = 40;
		int bob = (int) (Math.sin(System.currentTimeMillis() / 100.0 % 100.0) * 5) - 10;

		yy += 20;
		renderMsg(bm, "You died!", xx, yy + bob, 0xff0000);

		yy += 20;
		renderMsg(bm, "You spent: " + (level.getPlayTime() / 60 / 60) + " minutes playing!", xx, yy, 0xffffff);

		yy += 20;
		renderMsg(bm, "Press space to return to the menu.", xx, yy, 0xffffff);
	}

	private void renderMsg(Bitmap bm, String msg, int xp, int yp, int colour) {
		int xx = (Game.WIDTH - Font.instance.getCharWidth(msg)) / 2;
		int shade = 0x111111;
		Font.instance.render(bm, msg, xx + 1, yp + 1, shade);
		Font.instance.render(bm, msg, xx, yp, colour);
	}
}