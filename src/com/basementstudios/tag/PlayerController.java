package com.basementstudios.tag;

import java.util.*;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Player;

/**
 * Controls the 3 players.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public class PlayerController {
	public static final int NO_PLAYER = -1;
	public static final int PLAYER_1 = 0;
	public static final int PLAYER_2 = 1;
	public static final int PLAYER_3 = 2;

	public static List<CharacterData> availableCharacters;
	public static CharacterData[] selectedCharacters = new CharacterData[3];

	private final Player p0, p1, p2;

	private Player selectedPlayer = null;

	public PlayerController(Level level, double x, double y) {
		if (availableCharacters == null) throw new RuntimeException("Characters not loaded!");
		for (int i = 0; i < 3; i++) {
			selectedCharacters[i] = availableCharacters.get(i);
		}

		p0 = new Player(x, y + 30 * 0, selectedCharacters[PLAYER_1]);
		p1 = new Player(x, y + 30 * 1, selectedCharacters[PLAYER_2]);
		p2 = new Player(x, y + 30 * 2, selectedCharacters[PLAYER_3]);

		level.add(p0);
		level.add(p1);
		level.add(p2);
	}

	public void select(int selectionIndex) {
		if (selectionIndex == NO_PLAYER) selectedPlayer = null;
		if (selectionIndex == PLAYER_1) selectedPlayer = p0;
		if (selectionIndex == PLAYER_2) selectedPlayer = p1;
		if (selectionIndex == PLAYER_3) selectedPlayer = p2;
	}

	public void render(Bitmap bm) {
		if (selectedPlayer != null) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (selectedPlayer.getBB().xPos + 8);
			int yp = (int) (selectedPlayer.getBB().yPos - 20) + yOffs;
			bm.render(SpriteSheet.entities[0][0], xp, yp, 0xffffff);
		}
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public boolean attemptMove(double xa, double ya) {
		if (selectedPlayer == null) return false;

		selectedPlayer.xa = xa;
		selectedPlayer.ya = ya;
		selectedPlayer.attemptMove();
		return true;
	}
}