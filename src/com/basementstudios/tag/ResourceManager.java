package com.basementstudios.tag;

import java.util.*;

import com.basementstudios.tag.resource.*;
import com.basementstudios.tag.util.Logger;

/**
 * Localises and manages the resources loaded for the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class ResourceManager {
	public static final ResourceManager i = new ResourceManager();

	private List<Resource> resources = new ArrayList<Resource>();

	public final SpriteSheet particlesSpriteSheet = new SpriteSheet(this, "particles.png", 8, 8);
	public final SpriteSheet entitiesSpriteSheet = new SpriteSheet(this, "entities.png", 32, 32);
	public final SpriteSheet playerSpriteSheet = new SpriteSheet(this, "player.png", 128, 128);
	public final SpriteSheet enemySpriteSheet = new SpriteSheet(this, "enemy.png", 128, 128);

	public final LevelData testLevelData = new LevelData(this, "Test Level", "testLevel.png");

	public final Audio selectionSound = new Audio(this, "select.wav", false);
	public final Audio soundtrackSound = new Audio(this, "soundtrack_track_1.wav", false);

	public void addResource(Resource resource) {
		resources.add(resource);
	}

	public void loadAll() {
		resources.forEach(resource -> {
			Logger.log("Loading -> " + resource.getResourceType() + " from path: " + resource.getPath());
			resource.create();
		});
	}
}