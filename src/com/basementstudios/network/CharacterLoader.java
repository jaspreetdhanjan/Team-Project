package com.basementstudios.network;

import com.basementstudios.tag.util.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.basementstudios.network.CharacterData.*;

import com.basementstudios.network.CharacterData;

/**
 * Retrieves CharacterData for each character created, server-side.
 *
 * @author James Bray
 */

public class CharacterLoader {
	private static final String STAT_URL = "http://tag.yarbsemaj.com/api/chara/getStat.php";
	private static final String ITEM_URL = "http://tag.yarbsemaj.com/api/chara/getItems.php";
	private static final String URL = "http://tag.yarbsemaj.com/api/chara/list.php";
	private static final String ITEM_STAT_URL = "http://tag.yarbsemaj.com/api/item/getStat.php";

	private String token;

	private CharacterData characterData;
	private Item item;

	public CharacterLoader() {
		try {
			Token tokenObj = new Token();
			token = tokenObj.getToken();
		} catch (InvalidTokenException e) {
			e.printStackTrace();
		}
	}

	public List<CharacterData> getCharacters() {
		List<CharacterData> result = new ArrayList<CharacterData>();

		PostRequest poster = new PostRequest();
		Map<String, String> arguments = new HashMap<String, String>();
		arguments.put("Token", token);
		JSONObject charaData;
		try {
			charaData = poster.send(URL, arguments);
			if ((boolean) charaData.get("success")) {
				JSONObject charaData1 = (JSONObject) charaData.get("char");
				JSONArray charaArray = (JSONArray) charaData1.get("char");
				for (Object charaObject : charaArray) {
					JSONObject chara = (JSONObject) charaObject;
                    characterData = new CharacterData(
                            Integer.parseInt((String) chara.get("CharacterID")),
                            (String) chara.get("Name"),
                            Integer.parseInt((String) chara.get("Type")),
                            Integer.parseInt((String) chara.get("CurrentHealth")),
                            Integer.parseInt((String) chara.get("MaxHealth")));
                    addStat();
					addItems();
					calculateBattleStats();
                    Logger.log("SERVER: Loaded -> " + characterData);
                    result.add(characterData);
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}


		return result;
	}

	public CharacterData getUpdateData(CharacterData characterData) {
		this.characterData = characterData;
		addStat();
		addItems();
		calculateBattleStats();
		return this.characterData;
	}

	public void addStat() {
		if (characterData.getStats().size() == 0) {
			PostRequest poster = new PostRequest();
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("CharID", String.valueOf(characterData.getID()));
			JSONObject charaData;
			try {
				charaData = poster.send(STAT_URL, arguments);
				if ((boolean) charaData.get("success")) {
					JSONObject charaData1 = (JSONObject) charaData.get("Starts");
					JSONArray charaArray = (JSONArray) charaData1.get("stat");
					for (Object charaObject : charaArray) {
						JSONObject chara = (JSONObject) charaObject;
						characterData.addStat(new Stat(Integer.parseInt((String) chara.get("CharacteristicID")), Integer.parseInt((String) chara.get("Value")), (String) chara.get("Name")));
					}
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void addItems() {
		if (characterData.getItems().size() == 0) {
			PostRequest poster = new PostRequest();
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("CharID", String.valueOf(characterData.getID()));
			JSONObject charaData;
			try {
				charaData = poster.send(ITEM_URL, arguments);
				if ((boolean) charaData.get("success")) {
					JSONObject charaData1 = (JSONObject) charaData.get("items");
					JSONArray charaArray = (JSONArray) charaData1.get("item");
					for (Object charaObject : charaArray) {
						JSONObject chara = (JSONObject) charaObject;
						item = new Item(Integer.parseInt((String) chara.get("ItemID")), ((String) chara.get("ItemName")), Integer.parseInt((String) chara.get("SlotID")), (String) chara.get("SlotName"), Integer.parseInt((String) chara.get("TypeID")), (String) chara.get("TypeName"));
						addStats();
						characterData.addItem(item);
					}
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void addStats() {
		PostRequest poster = new PostRequest();
		Map<String, String> arguments = new HashMap<String, String>();
		arguments.put("ItemID", String.valueOf(item.getID()));
		System.out.println("ItemData");

		JSONObject charaData;
		try {
			charaData = poster.send(ITEM_STAT_URL, arguments);
			if ((boolean) charaData.get("success")) {
				JSONObject charaData1 = (JSONObject) charaData.get("Starts");
				JSONArray charaArray = (JSONArray) charaData1.get("stat");
				for (Object charaObject : charaArray) {
					JSONObject chara = (JSONObject) charaObject;
					item.addStat(new Stat(Integer.parseInt((String) chara.get("StatID")), Integer.parseInt((String) chara.get("Value")), (String) chara.get("Name")));
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public void calculateBattleStats() {
		characterData.addDmg(characterData.getStats().get(2).getValue());
		characterData.addSpd(characterData.getStats().get(4).getValue());

		int damageMultiplier = 1;
		for (Item item : characterData.getItems()) {
			switch (item.getTypeID()) {
				case 1: {
					characterData.setWeaponType(MELEE_WEAPON);
					damageMultiplier = characterData.getStats().get(2).getValue();
					break;
				}
				case 2: {
					characterData.setWeaponType(RANGED_WEAPON);
					damageMultiplier = characterData.getStats().get(3).getValue();
					break;
				}
				case 3: {
					characterData.setWeaponType(MAGIC_WEAPON);
					damageMultiplier = characterData.getStats().get(5).getValue();
					break;
				}
			}

			for (Stat stat : item.getStats()) {
				System.out.println(stat.getID() + stat.getName());
				switch (stat.getID()) {
					case 1: {
						characterData.addSpd(-stat.getValue());
						break;
					}
					case 2: {
						characterData.addDmg(stat.getValue() * damageMultiplier);
						break;
					}
					case 4: {
						characterData.addSpellDuration(stat.getValue());
						break;
					}
					case 5: {
						characterData.addDef(stat.getValue());
						break;
					}
					case 8: {
						characterData.addSpd(stat.getValue());
						break;
					}
				}
			}
		}

		if (characterData.getSpd() < 0) {
			characterData.addSpd(1);
		}
	}
}