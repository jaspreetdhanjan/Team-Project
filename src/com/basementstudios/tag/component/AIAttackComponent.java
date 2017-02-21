package com.basementstudios.tag.component;

import java.util.List;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.phys.AxisAlignedBB;
import com.basementstudios.tag.projectile.Bullet;

public class AIAttackComponent extends AIComponent {
	private AxisAlignedBB bb = new AxisAlignedBB();

	public AIAttackComponent(Mob attachedMob) {
		super(attachedMob);
	}

	public void tick() {
	}

	public Mob getNearestMob(double radius) {
		double x = attachedMob.x;
		double y = attachedMob.y;
		bb.set(x - radius, y - radius, x + radius, y + radius);

		Mob result = null;
		List<Entity> entities = attachedMob.getLevel().getEntities(bb);

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e == attachedMob) continue;
			if (e instanceof Mob) {
				result = (Mob) e;
			}
		}

		return result;
	}

	public void tryAttack(double radius) {
		double x = attachedMob.x;
		double y = attachedMob.y;

		Mob target = getNearestMob(radius);
		if (target == null) return;

		double dy = target.y - y;
		double dx = target.x - x;
		double dir = Math.atan2(dy, dx);

		attachedMob.getLevel().add(new Bullet(attachedMob, x, y, dir));
	}
}