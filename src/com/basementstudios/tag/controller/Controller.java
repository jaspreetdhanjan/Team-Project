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

	public Controller(Level level) {
		this.level = level;
	}

	public void select(int selectionIndex) {
		lastSelected = selected;

		if (selectionIndex == NO_MOB) selected = null;
		if (selectionIndex == MOB_1) selected = p0;
		if (selectionIndex == MOB_2) selected = p1;
		if (selectionIndex == MOB_3) selected = p2;

		if (selected != null) {
			onSelected();
		}
	}

	private void onSelected() {
		double[] battlePos = getBattlePos();
		
		if(lastSelected!=null) {
			lastSelected.moveTo(selected.getX(), selected.getY());
		}
		selected.moveTo(battlePos[0], battlePos[1]);
	}

	protected abstract double[] getBattlePos();

	public void renderSelected(Bitmap bm) {
		if (selected == null) return;

		int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
		int xp = (int) (selected.getX() + 45);
		int yp = (int) (selected.getY() - 42) + yOffs;
		bm.render(ResourceManager.i.entitiesSpriteSheet.getSprites()[0][0], xp, yp, 0xffffff);
	}

	//
	// public boolean attemptMove(double xa, double ya) {
	// if (selected == null) return false;
	//
	// selected.setDelta(xa, ya);
	// selected.attemptMove();
	// return true;
	// }
	//
	public T getSelected() {
		return selected;
	}

	public T getLastSelected() {
		return lastSelected;
	}
}