package com.basementstudios.tag;

import com.basementstudios.tag.resource.Audio;
import com.basementstudios.tag.resource.LevelData;
import com.basementstudios.tag.resource.Resource;
import com.basementstudios.tag.resource.SpriteSheet;
import com.basementstudios.tag.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Localises and manages the resources loaded for the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class ResourceManager {
	public static final ResourceManager i = new ResourceManager();
	public final SpriteSheet particlesSpriteSheet = new SpriteSheet(this, "particles.png", 8, 8);
	public final SpriteSheet entitiesSpriteSheet = new SpriteSheet(this, "entities.png", 32, 32);
    public final SpriteSheet knightSpriteSheet = new SpriteSheet(this, "knight.png", 128, 128);
    public final SpriteSheet mageSpriteSheet = new SpriteSheet(this, "mage.png", 128, 128);
    public final SpriteSheet necromancerSpriteSheet = new SpriteSheet(this, "necromancer.png", 128, 128);
    public final SpriteSheet rougueSpriteSheet = new SpriteSheet(this, "rogue.png", 128, 128);
    public final LevelData testLevelData = new LevelData(this, "Test Level", "testLevel.png");
	public final Audio selectionSound = new Audio(this, "select.wav", false);
	public final Audio soundtrackSound = new Audio(this, "soundtrack_track_1.wav", false);
    private List<Resource> resources = new ArrayList<Resource>();

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