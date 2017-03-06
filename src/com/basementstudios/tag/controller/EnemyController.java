package com.basementstudios.tag.controller;

import java.util.*;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Enemy;

/**
 * Controls the 3 enemies.
 * 
 * @author Jaspreet Dhanjan
 * @author James Bray
 */

public class EnemyController extends Controller<Enemy> {
	private static final String[] names = { "James", "Nick", "Hugh Osborne", "Mamadu", "Heston", "Jaspreet", "Sean", "Ben" };

	private int difficulty;
	private Enemy e0, e1, e2;
	private double[] battlePos = new double[2];

	private Random random = new Random();
	private List<String> usedNames = new ArrayList<String>();

	public EnemyController(Level level, double x, double y, int difficulty) {
		super(level);
		this.difficulty = difficulty;
		battlePos[0] = x - 128;
		battlePos[1] = y + 135*3.0/2.0;

		CharacterData[] characterData = generateData();
		e0 = new Enemy(battlePos[0], battlePos[1], characterData[0]);
		e1 = new Enemy(x, y + 135 * 1, characterData[1]);
		e2 = new Enemy(x, y + 135 * 2, characterData[2]);

		level.add(e0);
		level.add(e1);
		level.add(e2);
	}

	private CharacterData[] generateData() {
		CharacterData[] result = new CharacterData[3];
		for (int i = 0; i < 3; i++) {
			result[i] = new CharacterData(i + 3, getRandomName(), 100, 100);
		}

		return result;
	}

	private String getRandomName() {
		/*		int index = 0;
				String name = "";
				do {
					index = random.nextInt(names.length);
					name = names[index];
				} while (!usedNames.contains(usedNames));
		
				usedNames.add(name);*/
		return names[random.nextInt(names.length)];
	}

	public int getDifficulty() {
		return difficulty;
	}

	protected double[] getBattlePos() {
		return battlePos;
	}
}