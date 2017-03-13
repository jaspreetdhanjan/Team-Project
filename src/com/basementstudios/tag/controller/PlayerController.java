package com.basementstudios.tag.controller;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.Characters;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.mob.Player;

/**
 * Controls the 3 players.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public class PlayerController extends Controller<Player> {
	private static final double[] POS_FRON_CEN = new double[] { 100, 135 * 3.0 / 2.0 };
	private static final double[] POS_BACK_TOP = new double[] { 50, 135 };
	private static final double[] POS_BACK_BOT = new double[] { 50, 270 };

	private CharacterData[] selectedCharacters = new CharacterData[3];

	public PlayerController(Level level) {
		super(level);

		if (Characters.getAvailable().isEmpty()) throw new RuntimeException("Characters not loaded!");
		for (int i = 0; i < 3; i++) {
			selectedCharacters[i] = Characters.getAvailable().get(i);
		}

		p0 = new Player(POS_FRON_CEN[0], POS_FRON_CEN[1], selectedCharacters[MOB_1]);
		p1 = new Player(POS_BACK_TOP[0], POS_BACK_TOP[1], selectedCharacters[MOB_2]);
		p2 = new Player(POS_BACK_BOT[0], POS_BACK_BOT[1], selectedCharacters[MOB_3]);

		level.add(p0);
		level.add(p1);
		level.add(p2);
	}
	
	public CharacterData[] getSelectedCharacters() {
		return selectedCharacters;
	}

	public <S extends Mob> void attack(Controller<S> enemyController) {
	}
}