package com.basementstudios.tag.component;

import java.util.List;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.phys.AxisAlignedBB;

public class AttackComponent implements Component {
	private Mob attachedMob;
	private AxisAlignedBB bb = new AxisAlignedBB();

	public AttackComponent(Mob attachedMob) {
		this.attachedMob = attachedMob;
	}

	public void tick() {
	}

	public Mob getNearestMob(double radius) {
		double x = attachedMob.getBB().xPos;
		double y = attachedMob.getBB().yPos;
		bb.set(x - radius, y - radius, x + radius, y + radius);

		Mob result = null;
		List<Mob> mobs = attachedMob.getLevel().getMobs(bb);

		for (int i = 0; i < mobs.size(); i++) {
			Entity e = mobs.get(i);
			if (e == attachedMob) continue;
			if (e instanceof Mob) {
				result = (Mob) e;
			}
		}

		return result;
	}

	public void tryAttack(double radius) {
		double x = attachedMob.getBB().xPos;
		double y = attachedMob.getBB().yPos;

		Mob target = getNearestMob(radius);
		if (target == null) return;

		double dy = target.getBB().yPos - y;
		double dx = target.getBB().xPos - x;
		double dir = Math.atan2(dy, dx);

		// attachedMob.getLevel().add(new Bullet(attachedMob, x, y, dir));
	}
}