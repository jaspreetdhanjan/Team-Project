package com.basementstudios.tag.controller;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Characters;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Player;

/**
 * Controls the 3 players.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public class PlayerController extends Controller<Player> {
	public static final CharacterData[] selectedCharacters = new CharacterData[3];

	private double[] battlePos = new double[2];

	public PlayerController(Level level, double x, double y) {
		super(level);
		battlePos[0] = x + 128;
		battlePos[1] = x + 135 * 3.0 / 2.0;

		if (Characters.getAvailable().isEmpty()) throw new RuntimeException("Characters not loaded!");
		for (int i = 0; i < 3; i++) {
			selectedCharacters[i] = Characters.getAvailable().get(i);
		}

		p0 = new Player(battlePos[0], battlePos[1], selectedCharacters[MOB_1]);
		p1 = new Player(x, y + 135 * 1, selectedCharacters[MOB_2]);
		p2 = new Player(x, y + 135 * 2, selectedCharacters[MOB_3]);

		level.add(p0);
		level.add(p1);
		level.add(p2);
	}

	protected double[] getBattlePos() {
		return battlePos;
	}
}