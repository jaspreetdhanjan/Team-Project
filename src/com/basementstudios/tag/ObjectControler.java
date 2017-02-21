package com.basementstudios.tag;

import java.util.ArrayList;

import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;

public class ObjectControler {

	protected Level level;
	protected ArrayList<Mob> charaList = new ArrayList<Mob>();

	protected Mob selectedMob = null;
	protected Mob attackMob = null;
	private int atackIndex = 0;
	private boolean atacking = false;

	public void select(int selectionIndex) {
		selectedMob = charaList.get(selectionIndex);
	}

	public void atack() {
		atacking = true;
		atackIndex = 0;
		attackMob = charaList.get(0);
	}

	public void defending() {
		atacking = false;
		atackIndex = 0;
		attackMob = charaList.get(0);

	}

	public void turnTick(){
		for (Mob mob: charaList){
			mob.turnTick();
		}
	}
	public void render(Bitmap bm) {
		if (selectedMob != null && atacking) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (selectedMob.x + 8);
			int yp = (int) (selectedMob.y - 20) + yOffs;
			bm.render(SpriteSheet.entities[0][0], xp, yp, 0xffffff);
		}

		if (attackMob != null && !atacking) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (attackMob.x + 8);
			int yp = (int) (attackMob.y - 20) + yOffs;
			bm.render(SpriteSheet.entities[1][0], xp, yp, 0xffffff);
		}
	}

	public Mob getSelectedMob() {
		return selectedMob;
	}

	public void selectAtack(int idDelta) {
		atackIndex += idDelta;
		System.out.println(atackIndex);
		attackMob = charaList.get(Math.abs(atackIndex % charaList.size()));
	}

	public Mob getAttackMob() {
		return attackMob;
	}
}
