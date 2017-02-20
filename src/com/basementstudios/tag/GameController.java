package com.basementstudios.tag;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Enemy;
import com.basementstudios.tag.mob.Player;
import com.basementstudios.network.*;

public class GameController {

	private int turn = 0;
	private int maxSpd = 0;
	private Level level = null;
	private PlayerController playerController = new PlayerController();
	private EnemyController enemyController = new EnemyController();
	private boolean playerAtack = false;
	private boolean playerFinished = true;
	
	private boolean enemyAtack = false;

	public GameController(Level level) {
		this.level = level;
	}

	public void tick(Input input) {
		playerController.tick();
		enemyController.tick();
		if (playerAtack) {
			if (input.isDown(KeyEvent.VK_1))
				enemyController.select(EnemyController.ENEMY_1);
			if (input.isDown(KeyEvent.VK_2))
				enemyController.select(EnemyController.ENEMY_2);
			if (input.isDown(KeyEvent.VK_3))
				enemyController.select(EnemyController.ENEMY_3);
			if (input.isDown(KeyEvent.VK_ENTER)&& !playerController.getSelectedPlayer().isAttacking) {
				playerController.getSelectedPlayer().startAttack(60);
				playerFinished = false;
			}
			if (!playerController.getSelectedPlayer().isAttacking && !playerFinished) {
				playerFinished = true;
				getNext();
			}
		}
		
		if(enemyAtack&&!enemyController.getSelectedEnemy().isAttacking){
			enemyAtack=false;
			getNext();
		}
	}

	public void gameLoop() {
		for (Player player : playerController.getCharaList()) {
			if (player.getCharacterData().getSpd() > maxSpd)
				maxSpd = player.getCharacterData().getSpd();
		}
		for (Enemy enemy : enemyController.getCharaList()) {
			if (enemy.getSpd() > maxSpd)
				maxSpd = enemy.getSpd();
		}

		maxSpd++;
		turn++;
		
		System.out.println("Max SPD "+maxSpd);
		getNext();

		// while (playerController.getCharaList().size() != 0 ||
		// enemyController.getCharaList().size() != 0) {

		// }

	}

	public void getNext() {
		System.out.println("Turn: "+turn);
		Player nextPlayer = nextPlayer();
		if (nextPlayer == null) {
			Enemy nextEnemy = nextEnemy();
			if (nextEnemy == null) {
				turn++;
				resetEntity();
				getNext();
			} else{
				nextEnemy.startAttack(60);
			}
		}
	}
	
	public void resetEntity(){
		for (Player player : playerController.getCharaList()) {
			player.hasGone=false;
		}
		
		for (Enemy enemy : enemyController.getCharaList()) {
			enemy.hasGone=false;
		}
	}

	public Player nextPlayer() {
		int i = 0;
		for (Player player : playerController.getCharaList()) {
			i++;
			//System.out.println("Player");
			System.out.println(turn % (maxSpd - player.getCharacterData().getSpd()));
			if (turn % (maxSpd - player.getCharacterData().getSpd()) == 0 && !player.hasGone) {
				player.hasGone=true;
				//System.out.println("hit");
				playerAtack = true;
				playerController.select(i);
				return player;
			}
		}
		return null;
	}

	public Enemy nextEnemy() {
		int i = 0;
		for (Enemy enemy : enemyController.getCharaList()) {
			i++;
			//System.out.println("enemy");
			if (turn % (maxSpd-enemy.getSpd()) == 0 && !enemy.hasGone){
				enemy.hasGone=true;
				//System.out.println("hit");
				enemyAtack = true;
				enemyController.select(i);
				enemy.startAttack(60);
				return enemy;
			}
		}

		return null;
	}

	public void addPlayers(List<CharacterData> selectedCharas) {
		playerController.addPlayers(level, 50, 100, selectedCharas);
	}

	public void addEnemys(int seed) {
		enemyController.addEnemy(level, 200, 100, seed);
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

}
