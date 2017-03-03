package com.basementstudios.tag.controller;

import java.util.*;

import com.basementstudios.tag.Game;
import com.basementstudios.tag.Input;
import com.basementstudios.tag.ResourceManager;
import com.basementstudios.tag.ScreenManager;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.*;
import com.basementstudios.tag.screen.EndScreen;
import com.basementstudios.network.CharacterData;

/**
 * Controls the game flow
 * 
 * @author James Bray
 *
 */
public class GameController {
	public static final int STATE_NULL = 0;
	public static final int STATE_PLAYER_ATACK = 1;
	public static final int STATE_PLAYER_ATACKING = 2;
	public static final int STATE_ENEMY_ATACK = 3;

	public static List<CharacterData> availableCharacters;
	public static ArrayList<CharacterData> selectedCharacters = new ArrayList<CharacterData>(3);

	private int turn = 0;
	private int maxSpd = 0;
	private ObjectController<Player> playerController;
	private ObjectController<Enemy> enemyController;
	private Random rand = new Random();

	private ScreenManager screenManager;
	private int gameState = 0;

	private static int charaGap = 130;

	public GameController(Level level) {
		playerController = new ObjectController<Player>(level);
		enemyController = new ObjectController<Enemy>(level);
	}

	/**
	 * Handles input and processing and handles mob attack completion
	 * 
	 * @param input
	 */
	public void tick(Input input) {
		playerController.tick();
		enemyController.tick();

		switch (gameState) {
			case STATE_PLAYER_ATACK:
				boolean moveUp = input.up.isClicked() || input.left.isClicked();
				boolean moveDown = input.down.isClicked() || input.right.isClicked();
				boolean clicked = input.enter.isClicked() || input.space.isClicked();

				if (moveUp) enemyController.selectAttack(1);
				if (moveDown) enemyController.selectAttack(-1);

				if (clicked && !playerController.getSelectedMob().isAttacking) {
					playerController.getSelectedMob().startAttack(200, enemyController.attackMob);
					gameState = STATE_PLAYER_ATACKING;
				}
				break;
			case STATE_PLAYER_ATACKING:
				if (!playerController.getSelectedMob().isAttacking) {
					gameState = STATE_NULL;
					getNext();
				}
				break;
			case STATE_ENEMY_ATACK:
				if (!enemyController.getSelectedMob().isAttacking) {
					gameState = STATE_NULL;
					getNext();
				}
				break;
		}
	}

	/**
	 * Starts the game
	 */

	public void render(Bitmap bm) {
		playerController.render(bm);
		enemyController.render(bm);
	}

	public void startGame() {
		for (Mob player : playerController.getCharaList()) {
			if (player.getSpd() > maxSpd) maxSpd = player.getSpd();
		}
		for (Mob enemy : enemyController.getCharaList()) {
			if (enemy.getSpd() > maxSpd) maxSpd = enemy.getSpd();
		}
		maxSpd++;
		turn++;
		getNext();
	}

	/**
	 * Gets the next action to be performed
	 */
	public void getNext() {
		if (playerController.getCharaList().size() == 0) screenManager.setScreen(new EndScreen(false, null));
		else if (enemyController.getCharaList().size() == 0) screenManager.setScreen(new EndScreen(true, playerController.getCharaList()));
		else {
			if (nextPlayer() == null) {
				if (nextEnemy() == null) {
					nextTurn();
				}
			}
		}
	}

	public void nextTurn() {
		turn++;
		resetEntity();
		playerController.turnTick();
		enemyController.turnTick();
		getNext();
	}

	/**
	 * Resets an entity at the end of a turn
	 */
	public void resetEntity() {
		for (Mob player : playerController.getCharaList()) {
			player.hasGone = false;
		}

		for (Mob enemy : enemyController.getCharaList()) {
			enemy.hasGone = false;
		}
	}

	/**
	 * Gets the next player and starts there attack
	 * 
	 * @return
	 */
	public Mob nextPlayer() {
		int i = 0;
		for (Mob player : playerController.getCharaList()) {
			if (turn % (maxSpd - player.getSpd()) == 0 && !player.hasGone) {
				player.hasGone = true;
				gameState = STATE_PLAYER_ATACK;
				playerController.select(i);
				enemyController.defending();
				playerController.attack();
				return player;
			}
			i++;
		}
		return null;
	}

	/**
	 * Gets the next enemy and starts there attack
	 * 
	 * @return
	 */
	public Mob nextEnemy() {
		int i = 0;
		for (Mob enemy : enemyController.getCharaList()) {
			if (turn % (maxSpd - enemy.getSpd()) == 0 && !enemy.hasGone) {
				enemy.hasGone = true;
				gameState = STATE_ENEMY_ATACK;
				enemyController.select(i);
				enemyController.attack();
				playerController.defending();
				playerController.selectAttack(rand.nextInt(3));
				enemyController.getSelectedMob().startAttack(200, playerController.attackMob);
				return enemy;
			}
			i++;
		}
		return null;
	}

	/**
	 * Adds players
	 *
	 */
	public void addPlayers() {
		int x = 50;
		int y = 40;

		if (availableCharacters == null) throw new RuntimeException("Characters not loaded!");
		int unlovedCharas = 3 - selectedCharacters.size();
		for (int i = 0; i < unlovedCharas; i++) {
			selectedCharacters.add(availableCharacters.get(i));
			System.out.println(selectedCharacters.size());
		}

		for (int i = 0; i < 3; i++) {
			Player player = new Player(x, y + charaGap * i, selectedCharacters.get(i));
			playerController.addMob(player);
		}
	}

	/**
	 * Adds enemy
	 * 
	 * @param seed
	 */
	public void addEnemys(int seed) {
		int x = Game.WIDTH - 100 - ResourceManager.i.enemySpriteSheet.getSpriteWidth();
		int y = 40;
		ArrayList<String> names = new ArrayList<String>();
		names.add("Bret");
		names.add("Geff");
		names.add("Simon");
		names.add("Alex");
		names.add("Sam");
		for (int i = 0; i < 3; i++) {
			int dmg = (1 + rand.nextInt(3)) * seed;
			int def = (1 + rand.nextInt(3)) * seed;
			int spd = (0 + rand.nextInt(4)) * seed;
			int spellDuration = 0;
			int health = 50 * seed;
			int weponType = CharacterData.NO_WEAPON;

			int wT = (0 + rand.nextInt(4)) * seed;

			switch (wT) {
				case 0:
					weponType = CharacterData.NO_WEAPON;
					break;
				case 1:
					weponType = CharacterData.MELEE_WEAPON;
					break;
				case 2:
					weponType = CharacterData.RANGED_WEAPON;
					break;
				case 3:
					dmg = dmg / 2;
					spellDuration = (1 + rand.nextInt(2)) * seed;
					weponType = CharacterData.MAGIC_WEAPON;
			}

			String name = names.get(rand.nextInt(names.size()));
			Enemy enemy = new Enemy(x, y + charaGap * i, dmg, def, spd, spellDuration, weponType, health, name);
			enemyController.addMob(enemy);
		}
		enemyController.selectAttack(0);
	}

	public int getTurn() {
		return turn;
	}

	public ObjectController<Player> getPlayerController() {
		return playerController;
	}

	public ObjectController<Enemy> getEnemyController() {
		return enemyController;
	}

	public int getGameState() {
		return gameState;
	}

	public void setScreenManager(ScreenManager screenManager) {
		this.screenManager = screenManager;
	}
}