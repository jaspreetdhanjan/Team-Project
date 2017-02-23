package com.basementstudios.tag.level;

public class TestLevel extends Level {
	public TestLevel(int width, int height) {
		super("Test Level", width, height);
	}

	public int getDifficulty() {
		return 12;
	}
}