package com.basementstudios.tag.level;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.ResourceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DemoLevel extends Level {
	public DemoLevel() {
		super(ResourceManager.i.demoLevel);
	}

	@Override
	public List<CharacterData> getEnemy() {
		int seed = 50;
		List<String> names = new ArrayList<String>();
		List<CharacterData> characterDataArrayList = new ArrayList<CharacterData>();
		Random rand = new Random();
		names.add("AP and D");
		names.add("Logan Spoiler");
		names.add("C++ GUIs");

		for (int i = 0; i < 3; i++) {
			int dmg = (2 + rand.nextInt(10)) * seed / 2;
			int def = (25 + rand.nextInt(15)) * seed / 5;
			int spd = (2 + rand.nextInt(2)) * (seed / 7);
			int spellDuration = 0;
			int health = 100 * seed;
			int weponType = CharacterData.NO_WEAPON;

			int wT = (0 + rand.nextInt(4));

			switch (wT) {
				case 0:
					weponType = CharacterData.NO_WEAPON;
					break;
				case 1:
					weponType = CharacterData.MELEE_WEAPON;
					break;
				case 2:
					weponType = CharacterData.RANGED_WEAPON;
					break;
				case 3:
					dmg = dmg / 15;
					spellDuration = (1 + rand.nextInt(3)) * seed;
					weponType = CharacterData.MAGIC_WEAPON;
			}

			String name = names.get(i);
			int type = rand.nextInt(3);
			CharacterData data = new CharacterData(0, name, type, health, health);
			data.setDmg(dmg);
			data.setSpd(spd);
			data.setDef(def);
			data.setSpellDuration(spellDuration);
			data.setWeaponType(weponType);
			characterDataArrayList.add(data);
		}
		return characterDataArrayList;
	}
	
	public int getDifficulty() {
		return 12;
	}
}