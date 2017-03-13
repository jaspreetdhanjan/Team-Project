package com.basementstudios.tag.controller;

import java.util.*;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Enemy;
import com.basementstudios.tag.mob.Player;

/**
 * Controls the 3 enemies.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public class EnemyController extends Controller<Enemy> {
	private static final double[] POS_FRON_CEN = new double[] { 600, 135 * 3.0 / 2.0 };
	private static final double[] POS_BACK_TOP = new double[] { 650, 135 };
	private static final double[] POS_BACK_BOT = new double[] { 650, 270 };

	private static final String[] names = { "James", "Nick", "Hugh Osborne", "Mamadu", "Heston", "Jaspreet", "Sean", "Ben" };

	private int difficulty;

	private Random random = new Random();

	public EnemyController(Level level, int difficulty) {
		super(level);
		this.difficulty = difficulty;

		CharacterData[] characterData = generateData();
		p0 = new Enemy(POS_FRON_CEN[0], POS_FRON_CEN[1], characterData[MOB_1]);
		p1 = new Enemy(POS_BACK_TOP[0], POS_BACK_TOP[1], characterData[MOB_2]);
		p2 = new Enemy(POS_BACK_BOT[0], POS_BACK_BOT[1], characterData[MOB_3]);

		level.add(p0);
		level.add(p1);
		level.add(p2);
	}

	private CharacterData[] generateData() {
		CharacterData[] result = new CharacterData[3];
		for (int i = 0; i < result.length; i++) {
			result[i] = new CharacterData(-1, getRandomName(), random.nextInt(4), 100, 100);
		}

		return result;
	}

	private String getRandomName() {
		String name = names[random.nextInt(names.length)];
		return name;
	}

	public int getDifficulty() {
		return difficulty;
	}
}