package com.basementstudios.tag.graphics;

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

	private Bitmap[][] fontBitmap;
	private int charWidth, charHeight;

	public FontBitmap(Bitmap[][] fontBitmap, int charWidth, int charHeight) {
		this.fontBitmap = fontBitmap;
		this.charWidth = charWidth;
		this.charHeight = charHeight;
	}

	public Bitmap[][] getBitmap() {
		return fontBitmap;
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