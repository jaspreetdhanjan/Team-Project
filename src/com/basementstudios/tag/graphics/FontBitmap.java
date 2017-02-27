package com.basementstudios.tag.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.basementstudios.tag.resource.SpriteSheet;

/**
 * A font bitmap (spritesheet) for fast font drawing.
 * 
 * @author Jaspreet Dhanjan
 */

public class FontBitmap {
	private static final String CHARS = "" + //
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + //
			"abcdefghijklmnopqrstuvwxyz_               " + //
			"0123456789+-=*:;ÖÅÄå                      " + //
			"";

	private final Bitmap[][] spriteSheet;
	private final int charWidth, charHeight;

	public FontBitmap(SpriteSheet fontSheet, int charWidth, int charHeight) {
		this.spriteSheet = fontSheet.getSprites();
		this.charWidth = fontSheet.getSpriteWidth();
		this.charHeight = fontSheet.getSpriteHeight();
	}

	public FontBitmap(String filename, int charWidth, int charHeight) {
		BufferedImage fontImg = null;
		try {
			fontImg = ImageIO.read(FontBitmap.class.getResourceAsStream("/font/" + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int w = fontImg.getWidth();
		int h = fontImg.getHeight();

		int xSprites = w / charWidth;
		int ySprites = h / charHeight;

		spriteSheet = new Bitmap[xSprites][ySprites];
		for (int y = 0; y < ySprites; y++) {
			int yy = charHeight * y;
			for (int x = 0; x < xSprites; x++) {
				int xx = charWidth * x;
				spriteSheet[x][y] = new Bitmap(charWidth, charHeight, fontImg.getRGB(xx, yy, charWidth, charHeight, null, 0, charWidth));
			}
		}

		this.charWidth = charWidth;
		this.charHeight = charHeight;
	}

	public Bitmap[][] getBitmap() {
		return spriteSheet;
	}

	public int getCharWidth() {
		return charWidth;
	}

	public int getCharHeight() {
		return charHeight;
	}

	public String getAlphabet() {
		return FontBitmap.CHARS;
	}
}