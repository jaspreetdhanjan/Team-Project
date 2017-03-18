package com.basementstudios.tag.controller;

import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author James Bray
 */
public class ObjectController<T extends Mob> {

	protected Level level;
	protected ArrayList<T> charaList = new ArrayList<T>();

	protected T selectedMob = null;
	protected T attackMob = null;
	private int attackIndex = 0;
	private boolean attacking = false;

	public ObjectController(Level level) {
		this.level = level;
	}

	public void select(T selectedMob) {
		this.selectedMob = selectedMob;
	}

	/**
	 * Sets the controller up for attack mode
	 */
	public void atack() {
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

	public Mob getActiveMob() {
		if (attacking) return selectedMob;
		return attackMob;
	}

	public T getNext(int turn, int maxSpd) {
		for (T player : charaList) {
			if (turn % (maxSpd - player.getCharacterData().getSpd()) == 0 && !player.hasGone) {
				player.hasGone = true;
				return player;
			}
		}
		return null;
	}

	public int getMaxSpeed() {
		int maxSpd = 0;
		for (Mob player : charaList) {
			if (player.getCharacterData().getSpd() > maxSpd) maxSpd = player.getCharacterData().getSpd();
		}
		return maxSpd;
	}

	/**
	 * Renders the arrows above mobs heads, colour depended on the mode the Controller is in
	 *
	 * @param bm
	 */
	public void render(Bitmap bm) {
		if (selectedMob != null && attacking) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (selectedMob.getX() + (128 - ResourceManager.i.entitiesSpriteSheet.getSpriteWidth()) / 2);
			int yp = (int) (selectedMob.getY() - 30) + yOffs;
			bm.render(ResourceManager.i.entitiesSpriteSheet.getSprites()[0][0], xp, yp, 0xffffff);
		}

		if (attackMob != null && !attacking) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (attackMob.getX() + (128 - ResourceManager.i.entitiesSpriteSheet.getSpriteWidth()) / 2);
			int yp = (int) (attackMob.getY() - 30) + yOffs;
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

	/**
	 * Sets the Mob to be attacked
	 *
	 * @param idDelta
	 */
	public void selectAtack(int idDelta) {
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

	public ArrayList<T> getCharaList() {
		return charaList;
	}

	public void tick() {
		for (T player : charaList) {
			player.movePlayer();
		}
	}

	public void checkDead() {
		for (Iterator<T> iterator = charaList.iterator(); iterator.hasNext();) {
			T mob = iterator.next();
			if (mob.getCharacterData().getCurrentHealth() <= 0) {
				mob.onDied();
				iterator.remove();
			}
		}
	}

	public void addMob(T mob) {
		level.add(mob);
		charaList.add(mob);
	}
}