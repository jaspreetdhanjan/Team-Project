package com.basementstudios.network;

import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Retrieves CharacterData for each character created, server-side.
 * 
 * @author James Bray
 */

public class CharacterRetriever {
	private static final String URL = "http://tag.yarbsemaj.com/api/chara/list.php";

	private String token;

	public CharacterRetriever() {
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
					result.add(new CharacterData(Integer.parseInt((String) chara.get("CharacterID")), (String) chara.get("Name"), Integer.parseInt((String) chara.get("CurrentHealth")), Integer.parseInt((String) chara.get("MaxHealth"))));
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return result;
	}
}