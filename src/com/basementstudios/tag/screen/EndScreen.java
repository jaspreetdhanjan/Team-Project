package com.basementstudios.tag.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.basementstudios.network.InvalidTokenException;
import com.basementstudios.network.Item;
import com.basementstudios.network.PostRequest;
import com.basementstudios.network.Token;
import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.Font;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.mob.Player;

/**
 * Screen representation for when the game is in a paused state.
 * 
 * @author Jaspreet Dhanjan
 */

public class EndScreen extends Screen {

	private boolean win;
	private ArrayList<Mob> mobs;
	private ArrayList<String> winData = new ArrayList<String>();

	public EndScreen(boolean win, ArrayList<Mob> mobs) {
		this.win = win;
		this.mobs = mobs;

		if (win) {
			getWinData(mobs, 10,200);
		}
	}

	public void tick(Input input) {

	}

	public void renderScene(Bitmap bm) {
		bm.fill(0, 0, bm.width, bm.height, 0xffffff);

		int xo = (Game.WIDTH - Font.getInstance().getCharWidth("Congratulations!!!")) / 2;
		int yo = (Game.HEIGHT - 8) / 2;
		if (win) {

			Font.getInstance().draw(bm, "Congratulations!!!", xo, yo, 0xff);
			Font.getInstance().draw(bm, "You have Won", xo, yo + 10, 0xff);
			Font.getInstance().draw(bm, "Reward: ", xo, yo + 30, 0xff);
			int i=4;
			for(String data: winData){
				Font.getInstance().draw(bm, data, xo, yo + 10*i, 0xff);
				i++;
			}
			
			Font.getInstance().draw(bm, "and 200 gold", xo, yo + 10*i, 0xff);
		} else {
			Font.getInstance().draw(bm, "Comisration :(", xo, yo, 0xff);
			Font.getInstance().draw(bm, "You have Lost :'(", xo, yo + 20, 0xff);
		}
	}

	public void getWinData(ArrayList<Mob> mobs, int XP,int gold) {
		try {
			Token token = new Token();
			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("Token", token.getToken());
			arguments.put("Amount", String.valueOf(gold));
			JSONObject charaData;
			PostRequest poster = new PostRequest();
			charaData = poster.send("http://tag.yarbsemaj.com/api/user/addGold.php", arguments);
			System.out.println(charaData);
			if ((boolean) charaData.get("success")) {
				System.out.println("added gold");
			}
			for (Mob mob : mobs) {
				Player player = (Player) mob;
				int id = player.getCharacterData().getId();
				Map<String, String> arguments1 = new HashMap<String, String>();
				arguments1.put("CharID", String.valueOf(id));
				arguments1.put("Token", token.getToken());
				arguments1.put("XP", String.valueOf(XP));
				charaData = poster.send("http://tag.yarbsemaj.com/api/chara/addXP.php", arguments1);
				System.out.println(charaData);
				if ((boolean) charaData.get("success")) {
					String newXP = String.valueOf((Long) charaData.get("newXP"));
					String levelsGained = String.valueOf((Long) charaData.get("levelChange"));
					
					winData.add(player.getName()+":XP "+newXP+"("+XP+")"+levelsGained+" new levels");

				}
			}
		} catch (InvalidTokenException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}