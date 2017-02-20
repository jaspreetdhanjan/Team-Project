package com.basementstudios.tag.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public static final Bitmap[][] font = loadBitmap("/font.png", 6, 8);
	public static final Bitmap[][] particles = loadBitmap("/particles.png", 8, 8);
//	public static final Bitmap[][] blocks = loadBitmap("/blocks.png", 16, 16);
	public static final Bitmap[][] entities = loadBitmap("/entities.png", 16, 16);
	public static final Bitmap[][] chars = loadBitmap("/chars.png", 32, 32);
	public static final Bitmap[][] enemy = loadBitmap("/enemy.png", 32, 32);

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