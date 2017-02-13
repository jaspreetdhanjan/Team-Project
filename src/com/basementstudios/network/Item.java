package com.basementstudios.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Item {
	private String name, slot, type;
	private int id, slotID, typeID;
	private ArrayList<Stat> stats = new ArrayList<Stat>();

	public Item(int id, String name, int slotID, String slot, int typeID, String type) {
		this.id = id;
		this.name = name;
		this.slotID = slotID;
		this.slot = slot;
		this.typeID = typeID;
		this.type = type;
		addStats();

	}

	public void addStats() {
		PostRequest poster = new PostRequest();
		Map<String, String> arguments = new HashMap<String, String>();
		arguments.put("ItemID", String.valueOf(id));
		JSONObject charaData;
		try {
			charaData = poster.send("http://tag.yarbsemaj.com/api/item/getStat.php", arguments);
			if ((boolean) charaData.get("success")) {
				JSONObject charaData1 = (JSONObject) charaData.get("Starts");
				JSONArray charaArray = (JSONArray) charaData1.get("stat");
				for (Object charaObject : charaArray) {
					JSONObject chara = (JSONObject) charaObject;
					stats.add(new Stat(Integer.parseInt((String) chara.get("StatID")),
							Integer.parseInt((String) chara.get("Value")), (String) chara.get("Name")));
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSlotID() {
		return slotID;
	}

	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public ArrayList<Stat> getStats() {
		return stats;
	}

}
