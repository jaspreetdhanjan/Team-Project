package com.basementstudios.tag.graphics;

public class FontBitmap {
	public static final String CHARS = "" + //
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
}