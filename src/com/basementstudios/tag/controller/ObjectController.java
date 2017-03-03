package com.basementstudios.tag.controller;

import java.util.*;

import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;

/**
 * @author James Bray
 */
public class ObjectController<T extends Mob> {
	protected Level level;
	protected List<T> charaList = new ArrayList<T>();

	protected Mob selectedMob = null;
	protected Mob attackMob = null;
	private int attackIndex = 0;
	private boolean attacking = false;

	public ObjectController(Level level) {
		this.level = level;
	}

	public void select(int selectionIndex) {
		selectedMob = charaList.get(selectionIndex);
	}

	/**
	 * Sets the controller up for attack mode
	 */
	public void attack() {
		attacking = true;
		attackIndex = 0;
		attackMob = charaList.get(0);
	}

	/**
	 * Sets the controller to be attacked
	 */
	public void defending() {
		attacking = false;
		attackIndex = 0;
		attackMob = charaList.get(0);
	}

	/**
	 * Happens at the end of ever turn, updates all attached mobs
	 */
	public void turnTick() {
		for (T mob : charaList) {
			mob.turnTick();
		}
	}

	/**
	 * Renders the arrows above mobs heads, colour depended on the mode the Controller is in
	 *
	 * @param bm
	 */
	public void render(Bitmap bm) {
		if (selectedMob != null && attacking) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (selectedMob.getBB().xPos + 32);
			int yp = (int) (selectedMob.getBB().yPos - 20) + yOffs;
			bm.render(ResourceManager.i.entitiesSpriteSheet.getSprites()[0][0], xp, yp, 0xffffff);
		}

		if (attackMob != null && !attacking) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (attackMob.getBB().xPos + 32);
			int yp = (int) (attackMob.getBB().yPos - 20) + yOffs;
			bm.render(ResourceManager.i.entitiesSpriteSheet.getSprites()[1][0], xp, yp, 0xffffff);
		}
	}

	/**
	 * Returns the mob to do the attacking
	 *
	 * @return
	 */
	public Mob getSelectedMob() {
		return selectedMob;
	}

	public void removeDeadMob(Mob mob) {
		if (mob.getHealth() <= 0) {
			mob.onDied();
			charaList.remove(mob);
		}
	}

	/**
	 * Sets the Mob to be attacked
	 *
	 * @param idDelta
	 */
	public void selectAttack(int idDelta) {
		attackIndex += idDelta;
		attackMob = charaList.get(Math.abs(attackIndex % charaList.size()));
	}

	/**
	 * Returns the Mob to be attacked
	 *
	 * @return
	 */
	public Mob getAttackMob() {
		return attackMob;
	}

	public List<T> getCharaList() {
		return charaList;
	}

	public void tick() {
		for (int i = 0; i < charaList.size(); i++) {
			T player = charaList.get(i);
			player.movePlayer();
			removeDeadMob(player);
		}
	}

	public void addMob(T mob) {
		level.add(mob);
		charaList.add(mob);
	}
}