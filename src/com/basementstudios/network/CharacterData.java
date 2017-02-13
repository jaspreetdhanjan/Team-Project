package com.basementstudios.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class CharacterData {
	private int id, currentHelth, maxHealth;
	private String name;
	private ArrayList<CharacterStat> stats = new ArrayList<CharacterStat>();

	public CharacterData(int id, String name, int currentHealth, int maxHealth) {
		this.id = id;
		this.name = name;
		this.currentHelth = currentHealth;
		this.maxHealth = maxHealth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCurrentHelth() {
		return currentHelth;
	}

	public void setCurrentHelth(int currentHelth) {
		this.currentHelth = currentHelth;
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

	public ArrayList<CharacterStat> getStats() {
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
			HashMap<String, String> arguments = new HashMap();
			arguments.put("CharID", String.valueOf(id));
			JSONObject charaData;
			try {
				charaData = poster.send("http://tag.yarbsemaj.com/api/chara/getStat.php", arguments);
				if ((boolean) charaData.get("success")) {
					JSONObject charaData1 = (JSONObject) charaData.get("Starts");
					JSONArray charaArray = (JSONArray) charaData1.get("stat");
					for (Object charaObject : charaArray) {
						JSONObject chara = (JSONObject) charaObject;
						stats.add(Integer.parseInt((String) chara.get("CharacteristicID")),
								new CharacterStat(Integer.parseInt((String) chara.get("CharacteristicID")),
										Integer.parseInt((String) chara.get("Value")), (String) chara.get("Name")));
					}
				}
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
