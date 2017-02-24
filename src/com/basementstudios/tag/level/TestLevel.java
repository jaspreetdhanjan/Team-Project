package com.basementstudios.tag.level;

import com.basementstudios.tag.graphics.SpriteSheet;

public class TestLevel extends Level {
	public TestLevel() {
		super("Test Level", SpriteSheet.testLevelImg);
	}

	public int getDifficulty() {
		return 12;
	}
}