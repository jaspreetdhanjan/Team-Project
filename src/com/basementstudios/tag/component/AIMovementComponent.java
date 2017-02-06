package com.basementstudios.tag.component;

import com.basementstudios.tag.mob.Mob;

public class AIMovementComponent extends AIComponent {
	public AIMovementComponent(Mob attachedMob) {
		super(attachedMob);
	}

	public void tick() {
		attachedMob.xa++;
		attachedMob.attemptMove();
		attachedMob.xa = 0;
	}
}