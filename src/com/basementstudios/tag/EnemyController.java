package com.basementstudios.tag;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Enemy;
import com.basementstudios.tag.mob.Player;
import com.basementstudios.network.CharacterData;

public class EnemyController {
	public static final int ENEMY_NONE = 0;
	public static final int ENEMY_1 = 1;
	public static final int ENEMY_2 = 2;
	public static final int ENEMY_3 = 3;

	private Level level;
	private ArrayList<Enemy> charaList = new ArrayList<Enemy>();
	private Random rand = new Random();

	private Enemy selectedEnemy = null;
	private int selectionIndex = ENEMY_NONE;

	public void addEnemy(Level level, double x, double y, int seed) {
		this.level = level;
		for (int i = 0; i < 3; i++) {
			int dmg = (1 + rand.nextInt(3))*seed;
			int def = (1 + rand.nextInt(3))*seed;
			int spd = (0 + rand.nextInt(4))*seed;
			int spellDuration = 0;
			int health = 50*seed;
			int weponType=CharacterData.NO_WEPPON_ID;
			
			int wT = (0 + rand.nextInt(4))*seed;
			
			switch (wT){
			case 0:
				weponType=CharacterData.NO_WEPPON_ID;
				break;
			case 1:
				weponType=CharacterData.MELLE_ID;
				break;
			case 2: 
				weponType=CharacterData.RANGED_ID;
				break;
			case 3:
				dmg=dmg/2;
				spellDuration= (0 + rand.nextInt(2))*seed;
				weponType=CharacterData.MAGIC_ID;
			}

			Enemy p0 = new Enemy(x, y + 30 * i, dmg,def, spd, spellDuration, weponType, health);
			this.level.add(p0);
			charaList.add(p0);
		}

	}

	public void render(Bitmap bm) {
		if (selectedEnemy != null) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (selectedEnemy.x + 8);
			int yp = (int) (selectedEnemy.y - 20) + yOffs;
			bm.render(SpriteSheet.entities[0][0], xp, yp, 0xffffff);
		}
	}

	public Enemy getSelectedEnemy() {
		return selectedEnemy;
	}

	public void select(int selectionIndex) {
		this.selectionIndex = selectionIndex;

		if (selectionIndex == ENEMY_NONE)
			selectedEnemy = null;
		if (selectionIndex == ENEMY_1)
			selectedEnemy = charaList.get(0);
		if (selectionIndex == ENEMY_2)
			selectedEnemy = charaList.get(1);
		if (selectionIndex == ENEMY_3)
			selectedEnemy = charaList.get(2);
	}

	public int getSelected() {
		return selectionIndex;
	}

	public boolean attemptMove(double xa, double ya) {
		if (selectedEnemy == null)
			return false;

		selectedEnemy.xa = xa;
		selectedEnemy.ya = ya;
		selectedEnemy.attemptMove();
		return true;
	}

	public void attack() {
		if (!selectedEnemy.isAttacking) {
			selectedEnemy.startAttack(60);
		}
	}

	public void tick() {
		for (Enemy player : charaList) {
			if (player.isAttacking) {
				//System.out.println(player.x);
				if (player.xStart - player.x == 0 && player.isRetracting) {
					player.isAttacking = false;
					player.xa = 0;
				} else if (player.xStart - player.x == player.maxAttackFrame)
					player.isRetracting = true;
				else if (player.isRetracting)
					player.xa = 1;
				else
					player.xa = -1;
				player.attemptMove();
			}
		}
	}

	public ArrayList<Enemy> getCharaList() {
		return charaList;
	}
}