package com.basementstudios.tag.component;

import com.basementstudios.tag.mob.Mob;

public class AIComponent implements Component {
	protected final Mob attachedMob;

	public AIComponent(Mob attachedMob) {
		this.attachedMob = attachedMob;
	}

	public void tick() {
		attachedMob.xa++;
		attachedMob.attemptMove();
		attachedMob.xa = 0;
	}
}