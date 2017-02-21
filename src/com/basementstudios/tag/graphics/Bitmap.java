package com.basementstudios.tag.graphics;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * A fast drawing tool used to draw directly to the screen buffer.
 * 
 * @author Jaspreet Dhanjan
 */

public class Bitmap {
	public final int width, height;
	public final int pixels[];

	public boolean xFlip = false;
	public boolean yFlip = false;

	private FontBitmap fontBitmap = new FontBitmap(SpriteSheet.font, 6, 8);

	public Bitmap(int width, int height, int[] pixels) {
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public Bitmap(BufferedImage img) {
		width = img.getWidth();
		height = img.getHeight();
		pixels = img.getRGB(0, 0, width, height, null, 0, width);
	}

	public void render(Bitmap bm, int xp, int yp, int colour) {
		for (int y = 0; y < bm.height; y++) {
			int yPixel = y + yp;
			if (yPixel < 0 || yPixel >= height) continue;
			if (yFlip) yPixel = yp + bm.height - y - 1;

			for (int x = 0; x < bm.width; x++) {
				int xPixel = x + xp;
				if (xPixel < 0 || xPixel >= width) continue;
				if (xFlip) xPixel = xp + bm.width - x - 1;

				int src = bm.pixels[x + y * bm.width];
				if (src < 0) pixels[xPixel + yPixel * width] = src & colour;
			}
		}
	}

	public void clear() {
		Arrays.fill(pixels, 0);
	}

	public void fill(int x0, int y0, int x1, int y1, int colour) {
		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 >= width) x1 = width - 1;
		if (y1 >= height) y1 = height - 1;

		for (int y = y0; y < y1; y++) {
			if (y < 0 || y >= height) continue;
			for (int x = x0; x < x1; x++) {
				if (x < 0 || x >= width) continue;
				pixels[x + y * width] = colour;
			}
		}
	}

	public void setFontBitmap(FontBitmap fontBitmap) {
		this.fontBitmap = fontBitmap;
	}

	public void drawString(String msg, int xp, int yp, int colour) {
		for (int i = 0; i < msg.length(); i++) {
			int ch = fontBitmap.getAlphabet().indexOf(msg.charAt(i));
			if (ch < 0) continue;

			int xx = ch % 42;
			int yy = ch / 42;
			render(fontBitmap.getBitmap()[xx][yy], xp + i * fontBitmap.getCharWidth(), yp, colour);
		}
	}
	
	public void drawStringShadowed(String msg, int xp, int yp, int colour) {
		drawString(msg, xp + 1, yp + 1, 0x111111);
		drawString(msg, xp + 0, yp + 0, colour);
	}
	
	public int getCharWidth(String msg) {
		return msg.length() * fontBitmap.getCharWidth();
	}

	public int getCharHeight() {
		return fontBitmap.getCharHeight();
	}
}