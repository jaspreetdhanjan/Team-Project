package com.basementstudios.tag.level;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.ResourceManager;

import java.util.ArrayList;
import java.util.Random;

public class EasyLevel extends Level {
    public EasyLevel() {
        super(ResourceManager.i.testLevelData);
    }

    @Override
    public ArrayList<CharacterData> getEnemy() {
        int seed = 5;
        Random rand = new Random();
        ArrayList<CharacterData> characterDataArrayList = new ArrayList<>();
        ArrayList<String> names = new ArrayList<String>();
        names.add("Bret");
        names.add("Geoff");
        names.add("Simon");
        names.add("Alex");
        names.add("Sam");
        names.add("James");
        names.add("Ben");
        names.add("Nick");
        names.add("Heston");
        names.add("Jasp");
        for (int i = 0; i < 3; i++) {
            int dmg = (2 + rand.nextInt(5)) * seed;
            int def = (1 + rand.nextInt(3)) * seed;
            int spd = (1 + rand.nextInt(10)) * seed;
            int spellDuration = 0;
            int health = 50 * seed;
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
                    dmg = dmg / 2;
                    spellDuration = (1 + rand.nextInt(2)) * seed;
                    weponType = CharacterData.MAGIC_WEAPON;
            }


            String name = names.get(rand.nextInt(names.size()));
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
}