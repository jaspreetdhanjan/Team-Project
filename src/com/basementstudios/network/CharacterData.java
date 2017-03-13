package com.basementstudios.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the character attributes.
 * 
 * @author James Bray
 */

public class CharacterData implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int NO_WEAPON = -1;
	public static final int MAGIC_WEAPON = 1;
	public static final int RANGED_WEAPON = 2;
	public static final int MELEE_WEAPON = 3;

	private final int id;
	private final String name;
	private final int type;

	private int currentHealth, maxHealth;
	private int dmg, def, spd, spellDuration, weaponType;
	private List<Stat> stats = new ArrayList<Stat>();
	private List<Item> items = new ArrayList<Item>();

	public CharacterData(int id, String name, int type, int currentHealth, int maxHealth) {
		this.id = id;
		this.name = name;
		this.type = type;
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

	public void setWeaponType(int weaponType) {
		this.weaponType = weaponType;
	}

	public int getID() {
		return id;
	}

	public void setCurrentHealth(int currentHealth) {
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

	public void addItem(Item item) {
		items.add(item);
	}

	public void addStat(Stat stat) {
		stats.add(stat);
	}

	public void addDef(int def) {
		this.def += def;
	}

	public void addSpd(int spd) {
		this.spd += spd;
	}

	public void addSpellDuration(int spellDuration) {
		this.spellDuration += spellDuration;
	}

	public void addDmg(int dmg) {
		this.dmg += dmg;
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
		return name;
	}

	public void reduceHealth(int dmg) {
		if (dmg < 0) dmg = 0;
		currentHealth -= dmg;
	}

	public int getType() {
		return type;
	}
}