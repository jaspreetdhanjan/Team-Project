package com.basementstudios.tag.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * SpriteSheets for fast image loading and drawing. Note: add res folder to as a class folder.
 * 
 * @author Jaspreet Dhanjan
 */

public class SpriteSheet {
	public static final Bitmap[][] font = loadBitmap("/spritesheet/font.png", 6, 8);
	public static final Bitmap[][] particles = loadBitmap("/spritesheet/particles.png", 8, 8);
	public static final Bitmap[][] entities = loadBitmap("/spritesheet/entities.png", 16, 16);
	public static final Bitmap[][] character = loadBitmap("/spritesheet/character.png", 64, 64);
	public static final Bitmap[][] enemy = loadBitmap("/spritesheet/enemy.png", 32, 32);

	public static Bitmap testLevelImg = loadBitmap("/level/testLevel.png");

	public static Bitmap loadBitmap(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int w = img.getWidth();
		int h = img.getHeight();
		return new Bitmap(w, h, img.getRGB(0, 0, w, h, null, 0, w));
	}

	public static Bitmap[][] loadBitmap(String path, final int spriteWidth, final int spriteHeight) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int xSprites = img.getWidth() / spriteWidth;
		int ySprites = img.getHeight() / spriteHeight;
		Bitmap[][] result = new Bitmap[xSprites][ySprites];

		for (int y = 0; y < ySprites; y++) {
			int yy = spriteHeight * y;
			for (int x = 0; x < xSprites; x++) {
				int xx = spriteWidth * x;
				result[x][y] = new Bitmap(spriteWidth, spriteHeight, img.getRGB(xx, yy, spriteWidth, spriteHeight, null, 0, spriteWidth));
			}
		}
		return result;
	}
}