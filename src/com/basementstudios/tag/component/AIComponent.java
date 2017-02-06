package com.basementstudios.tag.component;

import com.basementstudios.tag.mob.Mob;

public class AIComponent implements Component {
	private Mob mob;

	public AIComponent(Mob mob) {
		this.mob = mob;
	}

	public void tick() {
		mob.xa = 1;
	}
}