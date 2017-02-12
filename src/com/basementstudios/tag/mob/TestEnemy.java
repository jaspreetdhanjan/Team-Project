package com.basementstudios.tag.mob;

import com.basementstudios.tag.component.AIComponent;
import com.basementstudios.tag.graphics.*;

public class TestEnemy extends Mob {
	public TestEnemy(double x, double y) {
		super(x, y);

		xSpriteIndex = 0;
		ySpriteIndex = 0;

		xs = 13 + 16;
		ys = 26 + 16;

		addComponent(new AIComponent(this));
	}

	public void tick() {
		super.tick();

		attemptMove();
	}

	public void render(Bitmap bm) {
		int colour = 0xff11ff;
		if (hitTime > 0) {
			hitTime--;
			colour = 0xcc0000;
		}

		xSpriteIndex = 0;
		if (xa != 0) xSpriteIndex = 2;

		if (xa < 0) bm.xFlip = true;
		if (xa > 0) bm.xFlip = false;

		int xp = (int) x;
		int yp = (int) y;
		bm.render(SpriteSheet.entities[xSpriteIndex][ySpriteIndex + 0], xp, yp, colour);
		bm.render(SpriteSheet.entities[xSpriteIndex][ySpriteIndex + 1], xp, yp + 16, colour);
		if (bm.xFlip == true) bm.xFlip = false;
	}
}