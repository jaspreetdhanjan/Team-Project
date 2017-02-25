package com.basementstudios.tag.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.basementstudios.tag.graphics.Bitmap;

/**
 * SpriteSheets for fast image drawing.
 * 
 * @author Jaspreet Dhanjan
 */

public class SpriteSheet extends Resource {
	private final String path;
	private final int spriteWidth, spriteHeight;

	private int width, height;
	private Bitmap[][] sprites;

	public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
		super(ResourceType.SPRITE_SHEET);
		this.path = "/spritesheet/" + path;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	public void create() {
		BufferedImage spriteSheet = null;
		try {
			spriteSheet = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		width = spriteSheet.getWidth();
		height = spriteSheet.getHeight();

		int xSprites = width / spriteWidth;
		int ySprites = height / spriteHeight;

		sprites = new Bitmap[xSprites][ySprites];
		for (int y = 0; y < ySprites; y++) {
			int yy = spriteHeight * y;
			for (int x = 0; x < xSprites; x++) {
				int xx = spriteWidth * x;
				sprites[x][y] = new Bitmap(spriteWidth, spriteHeight, spriteSheet.getRGB(xx, yy, spriteWidth, spriteHeight, null, 0, spriteWidth));
			}
		}
	}

	public void delete() {
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Bitmap[][] getSprites() {
		return sprites;
	}

	public String getPath() {
		return path;
	}
}