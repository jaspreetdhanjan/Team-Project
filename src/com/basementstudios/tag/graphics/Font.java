package com.basementstudios.tag.graphics;

public class Font {
	public static final int CHAR_WIDTH = 6;
	public static final int CHAR_HEIGHT = 8;

	private static final String CHARS = "" + //
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + //
			"abcdefghijklmnopqrstuvwxyz_               " + //
			"0123456789+-=*:;ÖÅÄå                      " + //
			"";

	public static void render(Bitmap bm, String msg, int xp, int yp, int colour) {
		for (int i = 0; i < msg.length(); i++) {
			int ch = CHARS.indexOf(msg.charAt(i));
			if (ch < 0) continue;

			int xx = ch % 42;
			int yy = ch / 42;
			bm.render(SpriteSheet.font[xx][yy], xp + i * CHAR_WIDTH, yp, colour);
		}
	}

	public static int getWidth(String msg) {
		return msg.length() * CHAR_WIDTH;
	}
}