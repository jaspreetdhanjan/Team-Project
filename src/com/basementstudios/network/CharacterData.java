package com.basementstudios.network;

import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class CharacterData {
	private int id, currentHealth, maxHealth;
	private String name;

	private List<Stat> stats = new ArrayList<Stat>();
	private List<Item> items = new ArrayList<Item>();

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

	public List<Stat> getStats() {
		return stats;
	}

	public void setStats(ArrayList<Stat> stats) {
		this.stats = stats;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
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
						stats.add(Integer.parseInt((String) chara.get("CharacteristicID")),
								new Stat(Integer.parseInt((String) chara.get("CharacteristicID")),
										Integer.parseInt((String) chara.get("Value")), (String) chara.get("Name")));
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
				charaData = poster.send("http://tag.yarbsemaj.com/api/chara/getItems.php", arguments);
				System.out.println(charaData);
				if ((boolean) charaData.get("success")) {
					JSONObject charaData1 = (JSONObject) charaData.get("items");
					JSONArray charaArray = (JSONArray) charaData1.get("item");
					for (Object charaObject : charaArray) {
						JSONObject chara = (JSONObject) charaObject;
						Item item = new Item(Integer.parseInt((String) chara.get("ItemID")),
								((String) chara.get("ItemName")), Integer.parseInt((String) chara.get("SlotID")),
								(String) chara.get("SlotName"), Integer.parseInt((String) chara.get("TypeID")), (String)chara.get("TypeName"));
						item.addStats();
						items.add(item);
					}
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
