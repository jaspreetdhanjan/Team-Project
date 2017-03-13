package com.basementstudios.tag.screen;

import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.basementstudios.network.*;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.mob.Player;

public class EndScreen extends Screen {
	private boolean win;
	private List<Player> mobs;
	private List<String> winData = new ArrayList<String>();

	public EndScreen(boolean win, List<Player> mobs) {
		this.win = win;
		this.mobs = mobs;

		if (win) {
			getWinData(mobs, 10, 200);
		}
	}

	public void tick(Input input) {
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		bm.fill(0, 0, bm.width, bm.height, 0xffffff);

		int xo = 0;
		int yo = 0;

		if (win) {
			bm.drawStringShadowed("Congratulations!!!", xo, yo, 0xff);
			bm.drawStringShadowed("You have Won", xo, yo + 10, 0xff);
			bm.drawStringShadowed("Reward: ", xo, yo + 30, 0xff);
			int i = 4;
			for (String data : winData) {
				bm.drawStringShadowed(data, xo, yo + 10 * i, 0xff);
				i++;
			}

			bm.drawStringShadowed("and 200 gold", xo, yo + 10 * i, 0xff);
		} else {
			bm.drawStringShadowed("Comisration :(", xo, yo, 0xff);
			bm.drawStringShadowed("You have Lost :'(", xo, yo + 20, 0xff);
		}
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
	}

	public void getWinData(List<Player> mobs, int XP, int gold) {
		try {
			Token token = new Token();

			Map<String, String> arguments = new HashMap<String, String>();
			arguments.put("Token", token.getToken());
			arguments.put("Amount", String.valueOf(gold));
			JSONObject charaData;
			PostRequest poster = new PostRequest();
			charaData = poster.send("http://tag.yarbsemaj.com/api/user/addGold.php", arguments);
			// System.out.println(charaData);
			if ((boolean) charaData.get("success")) {
				// System.out.println("added gold");
			}
			for (Player player : mobs) {
				int id = player.getCharacterData().getID();
				Map<String, String> arguments1 = new HashMap<String, String>();
				arguments1.put("CharID", String.valueOf(id));
				arguments1.put("Token", token.getToken());
				arguments1.put("XP", String.valueOf(XP));
				charaData = poster.send("http://tag.yarbsemaj.com/api/chara/addXP.php", arguments1);
				// System.out.println(charaData);
				if ((boolean) charaData.get("success")) {
					String newXP = String.valueOf((Long) charaData.get("newXP"));
					String levelsGained = String.valueOf((Long) charaData.get("levelChange"));
					winData.add(player.getCharacterData().getName() + ":XP " + newXP + "(" + XP + ")" + levelsGained + " new levels");
				}
			}
		} catch (InvalidTokenException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}