package com.basementstudios.tag.pickup;

import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.particle.TextParticle;

public class HealthPickup extends Pickup {
	public int healthToAdd = 20;

	public HealthPickup(double x, double y) {
		super(x, y);
		xSpriteIndex = 0;
		ySpriteIndex = 2;

		xr0 = 2;
		yr0 = 4;
		xr1 = 13;
		yr1 = 14;
	}

	public void addEnhancements(Mob mob) {
		mob.health += healthToAdd;
		level.add(new TextParticle("+" + healthToAdd, x, y, 2, 0xff0000));
		remove();
	}
}