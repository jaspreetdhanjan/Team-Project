package com.basementstudios.tag.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * Handles all external data associated with a level.
 * 
 * Note: the level image MUST be equal to the Game's viewport dimensions.
 * 
 * @author Jaspreet Dhanjan
 */

public class LevelData extends Resource {
	private final String levelName;
	private final String path;

	private Bitmap levelImage;

	public LevelData(ResourceManager resourceManager, String levelName, String path) {
		super(ResourceType.LEVEL_DATA, resourceManager);
		this.levelName = levelName;
		this.path = "/level/" + path;
	}

	public void create() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int w = img.getWidth();
		int h = img.getHeight();
		levelImage = new Bitmap(w, h, img.getRGB(0, 0, w, h, null, 0, w));
	}

	public String getPath() {
		return path;
	}

	public Bitmap getLevelImage() {
		return levelImage;
	}

	public String getLevelName() {
		return levelName;
	}
}