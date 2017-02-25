package com.basementstudios.tag.graphics;

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

	private Bitmap[][] spriteSheet;
	private int charWidth, charHeight;

	public FontBitmap(SpriteSheet fontSpriteSheet, int charWidth, int charHeight) {
		this.spriteSheet = fontSpriteSheet.getSprites();
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