package com.basementstudios.tag;

import java.util.ArrayList;

import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.mob.Player;

/**
 * @author James Bray
 *
 */
public class ObjectControler<T extends Mob> {

	protected Level level;
	protected ArrayList<T> charaList = new ArrayList<T>();

	protected Mob selectedMob = null;
	protected Mob attackMob = null;
	private int atackIndex = 0;
	private boolean atacking = false;

	/**
	 * Select the mob to do the attacking
	 * 
	 * @param selectionIndex
	 */

	public ObjectControler(Level level) {
		this.level = level;

	}

	public void select(int selectionIndex) {
		selectedMob = charaList.get(selectionIndex);
	}

	/**
	 * Sets the controller up for attack mode
	 */
	public void atack() {
		atacking = true;
		atackIndex = 0;
		attackMob = charaList.get(0);
	}

	/**
	 * Sets the controller to be attacked
	 */
	public void defending() {
		atacking = false;
		atackIndex = 0;
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
	 * Renders the arrows above mobs heads, colour depended on the mode the
	 * Controller is in
	 * 
	 * @param bm
	 */
	public void render(Bitmap bm) {
		if (selectedMob != null && atacking) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (selectedMob.getBB().xPos + 8);
			int yp = (int) (selectedMob.getBB().yPos - 20) + yOffs;
			bm.render(ResourceManager.i.entitiesSpriteSheet.getSprites() [0][0], xp, yp, 0xffffff);
		}

		if (attackMob != null && !atacking) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (attackMob.getBB().xPos + 8);
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
	public void selectAtack(int idDelta) {
		atackIndex += idDelta;
		System.out.println(atackIndex);
		attackMob = charaList.get(Math.abs(atackIndex % charaList.size()));
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
