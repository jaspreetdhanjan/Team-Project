package com.basementstudios.tag;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.mob.Player;
import com.basementstudios.tag.screen.EndScreen;
import com.basementstudios.network.*;

/**
 * Controls the game flow
 * @author James Bray
 *
 */
public class GameController {

	private int turn = 0;
	private int maxSpd = 0;
	private Level level = null;
	private PlayerController playerController = new PlayerController();
	private EnemyController enemyController = new EnemyController();
	private boolean playerAtack = false;
	private boolean playerFinished = true;

	private boolean enemyFinished = true;
	private Random rand = new Random();
	private Game game;

	public GameController(Level level, Game game) {
		this.level = level;
		this.game = game;
	}

	/**
	 * Handles input and processing and handles mob attack completion
	 * @param input
	 */
	public void tick(Input input) {
		playerController.tick();
		enemyController.tick();
		if (playerAtack) {
			if (input.pressedOnce(KeyEvent.VK_W))
				enemyController.selectAtack(1);
			if (input.pressedOnce(KeyEvent.VK_S))
				enemyController.selectAtack(-1);

			if (input.pressedOnce(KeyEvent.VK_ENTER) && !playerController.getSelectedMob().isAttacking) {
				playerController.getSelectedMob().startAttack(60, enemyController.attackMob);
				playerFinished = false;
			}
			if (!playerController.getSelectedMob().isAttacking && !playerFinished) {
				playerAtack = false;
				playerFinished = true;
				getNext();
			}
		}

		if (!enemyFinished && !enemyController.getSelectedMob().isAttacking) {
			enemyFinished = true;
			getNext();
		}
	}

	/**
	 * Starts the game
	 */
	public void gameLoop() {
		for (Mob player : playerController.getCharaList()) {
			if (player.getSpd() > maxSpd)
				maxSpd = player.getSpd();
		}
		for (Mob enemy : enemyController.getCharaList()) {
			if (enemy.getSpd() > maxSpd)
				maxSpd = enemy.getSpd();
		}

		maxSpd++;
		turn++;
		getNext();
	}

	/**
	 * Gets the next action to be performed
	 */
	public void getNext() {
		if (playerController.getCharaList().size() == 0)
			game.setScreen(new EndScreen(false,null));
		else if (enemyController.getCharaList().size() == 0)
			game.setScreen(new EndScreen(true,playerController.getCharaList()));
		else {
			Player nextPlayer = nextPlayer();
			if (nextPlayer == null) {
				Mob nextEnemy = nextEnemy();
				if (nextEnemy == null) {
					turn++;
					resetEntity();
					getNext();
					playerController.turnTick();
					enemyController.turnTick();
				}
			}
		}
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
	 * @return
	 */
	public Player nextPlayer() {
		int i = 0;
		for (Mob player : playerController.getCharaList()) {
			System.out.println(turn % (maxSpd - ((Player) player).getCharacterData().getSpd()));
			if (turn % (maxSpd - player.getSpd()) == 0 && !player.hasGone) {
				player.hasGone = true;
				playerAtack = true;
				playerController.select(i);
				enemyController.defending();
				playerController.atack();
				return (Player) player;
			}
			i++;
		}
		return null;
	}

	/**
	 * Gets the next enemy and starts there attack
	 * @return
	 */
	public Mob nextEnemy() {
		int i = 0;
		for (Mob enemy : enemyController.getCharaList()) {
			if (turn % (maxSpd - enemy.getSpd()) == 0 && !enemy.hasGone) {
				enemy.hasGone = true;
				enemyFinished = false;
				enemyController.select(i);
				enemyController.atack();
				playerController.defending();
				playerController.selectAtack(rand.nextInt(3));
				enemyController.getSelectedMob().startAttack(60, playerController.attackMob);
				return enemy;
			}
			i++;
		}
		return null;
	}

	/**
	 * Adds players
	 * @param selectedCharas
	 */
	public void addPlayers(List<CharacterData> selectedCharas) {
		playerController.addPlayers(level, 50, 50, selectedCharas);
	}

	/**
	 * Adds enemys
	 * @param seed
	 */
	public void addEnemys(int seed) {
		enemyController.addEnemy(level, 200, 50, seed);
	}

	public int getTurn() {
		return turn;
	}

	public PlayerController getPlayerController() {
		return playerController;
	}

	public EnemyController getEnemyController() {
		return enemyController;
	}

	public boolean isPlayerTurn(){
		return playerAtack;
	};
}
