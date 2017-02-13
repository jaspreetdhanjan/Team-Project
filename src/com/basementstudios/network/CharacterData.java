package com.basementstudios.network;

import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class CharacterData {
	private int id, currentHealth, maxHealth;
	private String name;

	private List<CharacterStat> stats = new ArrayList<CharacterStat>();

	public CharacterData(int id, String name, int currentHealth, int maxHealth) {
		this.id = id;
		this.name = name;
		this.currentHealth = currentHealth;
		this.maxHealth = maxHealth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCurrentHelth() {
		return currentHealth;
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

	public List<CharacterStat> getStats() {
		return stats;
	}

	public void setStats(ArrayList<CharacterStat> stats) {
		this.stats = stats;
	}

	public String toString() {
		return name + " " + Integer.toString(getCurrentHelth()) + "/" + Integer.toString(getMaxHealth());
	}

	public void addStat() {
		if (stats.size() == 0) {
			PostRequest poster = new PostRequest();
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("CharID", String.valueOf(id));
			JSONObject charaData;
			try {
				charaData = poster.send("http://tag.yarbsemaj.com/api/chara/getStat.php", arguments);
				if ((boolean) charaData.get("success")) {
					JSONObject charaData1 = (JSONObject) charaData.get("Starts");
					JSONArray charaArray = (JSONArray) charaData1.get("stat");
					for (Object charaObject : charaArray) {
						JSONObject chara = (JSONObject) charaObject;
						stats.add(Integer.parseInt((String) chara.get("CharacteristicID")), new CharacterStat(Integer.parseInt((String) chara.get("CharacteristicID")), Integer.parseInt((String) chara.get("Value")), (String) chara.get("Name")));
					}
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
