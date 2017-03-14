package com.basementstudios.tag.screen;

import com.basementstudios.network.InvalidTokenException;
import com.basementstudios.network.PostRequest;
import com.basementstudios.network.Token;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.audio.AudioPlayer;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.mob.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndScreen extends Screen {
	private boolean win;
	private List<String> winData = new ArrayList<String>();
	private Bitmap background;

	public EndScreen(List<Player> mobs) {
		win = true;
		getWinData(mobs, 10, 200);
		AudioPlayer.stop(ResourceManager.i.soundtrackSound);
		AudioPlayer.play(ResourceManager.i.winSound);
	}

	public EndScreen() {
		win = false;
		AudioPlayer.stop(ResourceManager.i.soundtrackSound);
		AudioPlayer.play(ResourceManager.i.loseSound);
	}

	public void tick(Input input) {
		if (input.enter.isClicked()) {
			AudioPlayer.stop(ResourceManager.i.winSound);
			AudioPlayer.stop(ResourceManager.i.loseSound);
			screenManager.setScreen(new TitleScreen());
		}
	}

	public void init() {
		background = Bitmap.load("/level/endScreen.png");
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();
		bm.render(background, 0, 0, 0xffffff);
		int xo;
		int yo = 295;

		if (win) {
			bm.setScale(2, 2);

			bm.setScale(2, 2);
			xo = (bm.width - bm.getCharWidth("Congratulations Å")) / 2;

			bm.drawString("Congratulations Å", xo, yo, 0x94ff00);
			bm.setScale(1, 1);
			xo = (bm.width - bm.getCharWidth("You have won!")) / 2;
			bm.drawString("You have won!", xo, yo + 30, 0xffffff);

			int i = 3;
			for (String data : winData) {
				xo = (bm.width - bm.getCharWidth(data)) / 2;
				bm.drawString(data, xo, yo + 17 * i, 0xffffff);
				i++;
			}
			xo = (bm.width - bm.getCharWidth("and 200 gold")) / 2;
			bm.drawString("and 200 gold!", xo, yo + 17 * i, 0xfaff00);
		} else {
			bm.setScale(2, 2);
			xo = (bm.width - bm.getCharWidth("Commiseration Ö")) / 2;

			bm.drawString("Commiseration Ö", xo, yo, 0xff0000);
			bm.setScale(1, 1);
			xo = (bm.width - bm.getCharWidth("You have lost")) / 2;
			bm.drawString("You have lost", xo, yo + 30, 0xffffff);
		}
	}

	public void renderHud(Bitmap bm) {
		bm.clear();
		bm.setScale(2, 2);
		int xo = (bm.width - bm.getCharWidth("Press enter to continue")) / 2;

		bm.drawString("Press enter to continue", xo, 40, 0xffffff);
		bm.setScale(1, 1);

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
			for (Player player : mobs) {
				player.resetHealth();
				int id = player.getCharacterData().getID();
				Map<String, String> arguments1 = new HashMap<String, String>();
				arguments1.put("CharID", String.valueOf(id));
				arguments1.put("Token", token.getToken());
				arguments1.put("XP", String.valueOf(XP));
				charaData = poster.send("http://tag.yarbsemaj.com/api/chara/addXP.php", arguments1);
				if ((boolean) charaData.get("success")) {
					String newXP = String.valueOf(charaData.get("newXP"));
					String levelsGained = String.valueOf(charaData.get("levelChange"));
					winData.add(player.getCharacterData().getName() + ":XP (" + XP + ")" + levelsGained + " new levels");
				}
			}
		} catch (InvalidTokenException | IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}