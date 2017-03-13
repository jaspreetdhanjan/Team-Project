package com.basementstudios.tag.controller;

import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;

/**
 * Controls the 3 entities.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public abstract class Controller<T extends Mob> {
	public static final int NO_MOB = -1;
	public static final int MOB_1 = 0;
	public static final int MOB_2 = 1;
	public static final int MOB_3 = 2;

	protected final Level level;

	protected T p0, p1, p2;
	protected T selected = null;
	protected T lastSelected = null;

	private Mob[] mobs;

	public Controller(Level level) {
		this.level = level;
	}

	public final void select(int selectionIndex) {
		lastSelected = selected;

		if (selectionIndex == NO_MOB) selected = null;
		if (selectionIndex == MOB_1) selected = p0;
		if (selectionIndex == MOB_2) selected = p1;
		if (selectionIndex == MOB_3) selected = p2;

		if (selected != null) {
			onSelected();
		}
	}

	protected void onSelected() {
	}

	public void renderSelected(Bitmap bm) {
		if (selected == null) return;

		int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
		int xp = (int) (selected.getX() + 45);
		int yp = (int) (selected.getY() - 42) + yOffs;
		bm.render(ResourceManager.i.entitiesSpriteSheet.getSprites()[0][0], xp, yp, 0xffffff);
	}

	/*
	 * steps of attack
	 * 
	 * 1. Get speed value. Lower the speed, the less the players go
	 * 2. The player with the fastest speed goes first
	 * 
	 * 3. Let the player select the character
	 * 4. Turn time++
	 * 
	 * Magic does damage over time. spellDuration is the time variable
	 * Debuff damage is recovery
	 *  
	 *  Run James' old Mob damage methods
	 *  
	 *  No need to change weapons, characters are initialised with weapons
	 *  
	 * 5. if(attacking character) if he has 5 defence, 10 attack on other character
	 * 
	 * 
	 *  
	 */
	
	public <S extends Mob> void attack(Controller<S> enemyController) {
	}

	public T getSelected() {
		return selected;
	}

	public T getLastSelected() {
		return lastSelected;
	}

	public Mob[] getMobs() {
		if (mobs == null) {
			mobs = new Mob[3];
			mobs[0] = p0;
			mobs[1] = p1;
			mobs[2] = p2;
		}
		return mobs;
	}
}