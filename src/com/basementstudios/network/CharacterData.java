package com.basementstudios.network;

import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Defines the character attributes.
 * 
 * @author James Bray
 */

public class CharacterData {
	private static final String STAT_URL = "http://tag.yarbsemaj.com/api/chara/getStat.php";
	private static final String ITEM_URL = "http://tag.yarbsemaj.com/api/chara/getItems.php";

	public static final int NO_WEAPON = -1;
	public static final int MAGIC_WEAPON = 1;
	public static final int RANGED_WEAPON = 2;
	public static final int MELEE_WEAPON = 3;

	private final int id;

	private int currentHealth, maxHealth;
	private String name;
	private int dmg, def, spd, spellDuration, weaponType;

	private List<Stat> stats = new ArrayList<Stat>();
	private List<Item> items = new ArrayList<Item>();

	public CharacterData(int id, String name, int currentHealth, int maxHealth) {
		this.id = id;
		this.name = name;
		this.currentHealth = currentHealth;
		this.maxHealth = maxHealth;
		dmg = 0;
		def = 0;
		spd = 0;
		spellDuration = 0;
		weaponType = NO_WEAPON;
	}

	public int getWeaponType() {
		return weaponType;
	}

	public int getID() {
		return id;
	}

	public void setCurrentHelth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Stat> getStats() {
		return stats;
	}

	public void setStats(List<Stat> stats) {
		this.stats = stats;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getDef() {
		return def;
	}

	public int getSpd() {
		return spd;
	}

	public int getSpellDuration() {
		return spellDuration;
	}

	public int getDmg() {
		return dmg;
	}

	public String toString() {
		return name + " " + Integer.toString(getCurrentHealth()) + "/" + Integer.toString(getMaxHealth());
	}

	public void addStat() {
		if (stats.size() == 0) {
			PostRequest poster = new PostRequest();
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("CharID", String.valueOf(id));
			JSONObject charaData;
			try {
				charaData = poster.send(STAT_URL, arguments);
				if ((boolean) charaData.get("success")) {
					JSONObject charaData1 = (JSONObject) charaData.get("Starts");
					JSONArray charaArray = (JSONArray) charaData1.get("stat");
					for (Object charaObject : charaArray) {
						JSONObject chara = (JSONObject) charaObject;
						stats.add(Integer.parseInt((String) chara.get("CharacteristicID")), new Stat(Integer.parseInt((String) chara.get("CharacteristicID")), Integer.parseInt((String) chara.get("Value")), (String) chara.get("Name")));
					}
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void addItems() {
		if (items.size() == 0) {
			PostRequest poster = new PostRequest();
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("CharID", String.valueOf(id));
			JSONObject charaData;
			try {
				charaData = poster.send(ITEM_URL, arguments);
				if ((boolean) charaData.get("success")) {
					JSONObject charaData1 = (JSONObject) charaData.get("items");
					JSONArray charaArray = (JSONArray) charaData1.get("item");
					for (Object charaObject : charaArray) {
						JSONObject chara = (JSONObject) charaObject;
						Item item = new Item(Integer.parseInt((String) chara.get("ItemID")), ((String) chara.get("ItemName")), Integer.parseInt((String) chara.get("SlotID")), (String) chara.get("SlotName"), Integer.parseInt((String) chara.get("TypeID")), (String) chara.get("TypeName"));
						item.addStats();
						items.add(item);
					}
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void calculateBattleStats() {
		dmg += stats.get(2).getValue();
		spd += stats.get(4).getValue();

		int damageMultiplier = 1;
		for (Item item : items) {
			switch (item.getTypeID()) {
				case 1: {
					weaponType = MELEE_WEAPON;
					damageMultiplier = stats.get(2).getValue();
					break;
				}
				case 2: {
					weaponType = RANGED_WEAPON;
					damageMultiplier = stats.get(3).getValue();
					break;
				}
				case 3: {
					weaponType = MAGIC_WEAPON;
					damageMultiplier = stats.get(5).getValue();
					break;
				}
			}

			for (Stat stat : item.getStats()) {
				System.out.println(stat.getID() + stat.getName());
				switch (stat.getID()) {
					case 1: {
						spd -= stat.getValue();
						break;
					}
					case 2: {
						dmg += stat.getValue() * damageMultiplier;
						break;
					}
					case 4: {
						spellDuration += stat.getValue();
						break;
					}
					case 5: {
						def += stat.getValue();
						break;
					}
					case 8: {
						spd += stat.getValue();
						break;
					}
				}
			}
		}

		if (spd < 0) {
			spd = 1;
		}
	}
}
