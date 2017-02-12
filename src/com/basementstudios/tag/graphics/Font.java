package com.basementstudios.tag.graphics;

public class Font {
	private static Font singleton;

	public static Font getInstance() {
		if (singleton == null) {
			singleton = new Font(new FontBitmap(SpriteSheet.font, 6, 8));
		}
		return singleton;
	}

	private FontBitmap fontSheet;

	public Font(FontBitmap fontSheet) {
		this.fontSheet = fontSheet;
	}

	public void drawBg(Bitmap bm, int x, int y, int w, int h) {
		bm.fill(x, y, x + w, y + h, 0xffffff);
	}

	public void draw(Bitmap bm, String msg, int xp, int yp, int colour) {
		for (int i = 0; i < msg.length(); i++) {
			int ch = fontSheet.getAlphabet().indexOf(msg.charAt(i));
			if (ch < 0) continue;

			int xx = ch % 42;
			int yy = ch / 42;
			bm.render(fontSheet.getBitmap()[xx][yy], xp + i * fontSheet.getCharWidth(), yp, colour);
		}
	}

	public void drawShadowed(Bitmap bm, String msg, int xp, int yp, int colour) {
		draw(bm, msg, xp + 1, yp + 1, 0x111111);
		draw(bm, msg, xp + 0, yp + 0, colour);
	}

	public int getCharWidth(String msg) {
		return msg.length() * fontSheet.getCharWidth();
	}

	public int getCharHeight() {
		return fontSheet.getCharHeight();
	}
}