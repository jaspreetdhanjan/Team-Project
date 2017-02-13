package com.basementstudios.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class CharaViewControler {
	private String token;

	public CharaViewControler() {
		try {
			Token tokenObj = new Token();
			token = tokenObj.getToken();
		} catch (InvalidTokenException e) {
			e.printStackTrace();
		}
	}

	public DefaultListModel<CharacterData> getModel() {
		DefaultListModel<CharacterData> model = new DefaultListModel<CharacterData>();
		PostRequest poster = new PostRequest();
		Map<String, String> arguments = new HashMap<String, String>();
		arguments.put("Token", token);
		JSONObject charaData;
		try {
			charaData = poster.send("http://tag.yarbsemaj.com/api/chara/list.php", arguments);
			if ((boolean) charaData.get("success")) {
				JSONObject charaData1 = (JSONObject) charaData.get("char");
				JSONArray charaArray = (JSONArray) charaData1.get("char");
				for (Object charaObject : charaArray) {
					JSONObject chara = (JSONObject) charaObject;
					model.addElement(new CharacterData(Integer.parseInt((String) chara.get("CharacterID")), (String) chara.get("Name"), Integer.parseInt((String) chara.get("CurrentHealth")), Integer.parseInt((String) chara.get("MaxHealth"))));
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return model;
	}
}