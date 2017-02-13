package com.basementstudios.network;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;



public class CharaViewControler {

	private String token;
	public CharaViewControler(){
		try {
			Token tokenObj= new Token();
			token=tokenObj.getToken();
		} catch (InvalidTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public DefaultListModel<CharacterData> getModal(){
		DefaultListModel<CharacterData> model=new DefaultListModel();
		PostRequest poster=new PostRequest();
		HashMap<String, String> arguments = new HashMap();
		//System.out.println(token);
		arguments.put("Token", token);
		JSONObject charaData;
		try {
			charaData = poster.send("http://tag.yarbsemaj.com/api/chara/list.php", arguments);
			if((boolean) charaData.get("success")){
				JSONObject charaData1=(JSONObject) charaData.get("char");
				JSONArray charaArray=(JSONArray) charaData1.get("char");
				for(Object charaObject : charaArray){
					JSONObject chara= (JSONObject) charaObject;
					model.addElement(new CharacterData(
							Integer.parseInt((String) chara.get("CharacterID")),
							(String)chara.get("Name"),
							Integer.parseInt((String)chara.get("CurrentHealth")),
							Integer.parseInt((String)chara.get("MaxHealth"))));
				}		
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
		
		
}

