package com.basementstudios.tag.mob;

import com.basementstudios.tag.Entity;
import com.basementstudios.tag.graphics.Font;
import com.basementstudios.tag.particle.TextParticle;

/**
 * A moving and dynamic character within the game.
 * 
 * @author Jaspreet Dhanjan
 */

public class Mob extends Entity {
	public int hitTime = 0;
	public int lastWalkDist, walkDist;
	public boolean hasGone = false;
	protected int dmg, def, spd, spellDuration, wepponType, health, maxHealth;
	protected int debuffDamage, debuffDuration;
	protected String name;
	public boolean isAttacking = false;
	public boolean isRetracting = false;
	public int maxAttackFrame;
	protected Font font = Font.getInstance();
	protected Mob target = null;

	public Mob(double x, double y) {
		this.x = x;
		this.y = y;

		this.xStart = x;
		this.yStart = y;
	}

	public void attemptMove() {
		double xxa = x + xa;
		double yya = y + ya;
		move(xxa, yya);
	}

	private void move(double xxa, double yya) {
		if (isRemoved())
			return;

		lastWalkDist = walkDist;

		double x0 = bb.x;
		double y0 = bb.y;
		double x1 = bb.x + bb.xs;
		double y1 = bb.y + bb.ys;

		if (x0 < 0 || y0 < 0 || x1 >= level.getWidth() || y1 >= level.getHeight()) {
			collide(null, xxa, yya);
			return;
		}

		x = xxa;
		y = yya;
		walkDist++;
		bb.set(x, y, xs, ys);
	}

	public boolean isMoving() {
		return walkDist != lastWalkDist;
	}

	public boolean blocks(Entity e) {
		return true;
	}

	public void startAttack(int maxAttackFrame, Mob enemy) {
		isAttacking = true;
		isRetracting = false;
		this.maxAttackFrame = maxAttackFrame;
		target = enemy;
	}

	public void spellCast(int spellDamage, int speelDamageDuration) {
		this.debuffDamage = spellDamage;
		this.debuffDuration = speelDamageDuration;
	}

	public void turnTick() {
		spellHurt();
	}

	public void tick() {

	}

	public void spellHurt() {
		System.out.println("Spell Hurt");
		int colour = 0x0f5b00;

		if (debuffDuration > 0) {
			health -= debuffDamage;
			level.add(new TextParticle("-" + debuffDamage, x, y, 2, colour));
			debuffDuration--;
		}
	}
	
	public void hit(int dmg){
		int damage = dmg - def;

		if (damage < 0) {
			damage = 0;
		}
		health -= damage;
		
		hurt(damage);
	}

	public void hurt(int dmg) {
		int colour = 0xff0000;
		
		System.out.println(health);
		level.add(new TextParticle("-" + dmg, x, y, 2, colour));
	}

	public void onDied() {
		remove();
	}

	public void collide(Entity otherEntity, double xxa, double yya) {
		if (xxa != 0)
			xa = -1 * 20;
		if (yya != 0)
			ya = -1 * 20;
	}

	public int getDmg() {
		return dmg;
	}

	public int getDef() {
		return def;
	}

	public int getSpd() {
		return spd;
	}

	public int getSpellDuration() {
		return spellDuration;
	}

	public int getWepponType() {
		return wepponType;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public Mob getTarge() {
		return target;
	}

	public int getDebuffDamage() {
		return debuffDamage;
	}

	public int getDebuffDuration() {
		return debuffDuration;
	}

	public String getName() {
		return name;
	}

}
