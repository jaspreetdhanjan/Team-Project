package com.basementstudios.tag;

import java.util.ArrayList;

import java.util.Random;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Enemy;
import com.basementstudios.tag.mob.Mob;

public class EnemyController extends ObjectControler {

	private Random rand = new Random();

	public void addEnemy(Level level, double x, double y, int seed) {
		super.level = level;
		ArrayList<String> names = new ArrayList<String>();
		names.add("Bret");
		names.add("Geff");
		names.add("Simon");
		names.add("Alex");
		names.add("Sam");
		for (int i = 0; i < 3; i++) {
			int dmg = (1 + rand.nextInt(3)) * seed;
			int def = (1 + rand.nextInt(3)) * seed;
			int spd = (0 + rand.nextInt(4)) * seed;
			int spellDuration = 0;
			int health = 50 * seed;
			int weponType = CharacterData.NO_WEPPON_ID;

			int wT = (0 + rand.nextInt(4)) * seed;

			switch (wT) {
			case 0:
				weponType = CharacterData.NO_WEPPON_ID;
				break;
			case 1:
				weponType = CharacterData.MELLE_ID;
				break;
			case 2:
				weponType = CharacterData.RANGED_ID;
				break;
			case 3:
				dmg = dmg / 2;
				spellDuration = (1 + rand.nextInt(2)) * seed;
				weponType = CharacterData.MAGIC_ID;
			}
			String name = names.get(rand.nextInt(names.size()));
			Enemy p0 = new Enemy(x, y + 50 * i, dmg, def, spd, spellDuration, weponType, health, name);
			super.level.add(p0);
			charaList.add(p0);
		}

		attackMob = charaList.get(0);

	}

	public void tick() {
		for (int i = 0; i < charaList.size(); i++) {
			Mob player = charaList.get(i);
			if (player.isAttacking) {
				if (player.xStart - player.x == 0 && player.isRetracting) {
					player.isAttacking = false;
					player.xa = 0;
				} else if (player.xStart - player.x == player.maxAttackFrame) {
					player.isRetracting = true;
					player.getTarge().hurt(player, player.getDmg());
					player.getTarge().spellCast(player.getDmg(), player.getSpellDuration());
				} else if (player.isRetracting)
					player.xa = 1;
				else
					player.xa = -1;
				player.attemptMove();
			}
			if (!player.isAlive()) {
				charaList.remove(player);
			}
		}
	}

	public ArrayList<Mob> getCharaList() {
		return charaList;
	}

}