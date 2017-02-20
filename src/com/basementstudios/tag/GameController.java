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
	private int maxSpd=0;
	private Level level = null;
	private PlayerController playerController = new PlayerController();
	private EnemyController enemyController = new EnemyController();
	
	public GameController(Level level){
		this.level = level;
	}
	
	public void tick(Input input) {
		playerController.tick();
		enemyController.tick();
		if (input.isDown(KeyEvent.VK_1)) enemyController.select(EnemyController.ENEMY_1);
		if (input.isDown(KeyEvent.VK_2)) enemyController.select(EnemyController.ENEMY_2);
		if (input.isDown(KeyEvent.VK_3)) enemyController.select(EnemyController.ENEMY_3);
	}
	
	public void gameLoop(){
		for(Player player: playerController.getCharaList()){
			if(player.getCharacterData().getSpd()>maxSpd) maxSpd = player.getCharacterData().getSpd();
		}
		for(Enemy enemy: enemyController.getCharaList()){
			if(enemy.getSpd()>maxSpd) maxSpd = enemy.getSpd();
		}
		
		maxSpd++;
		
		while(playerController.getCharaList().size()!=0 || enemyController.getCharaList().size()!=0 ){
			
		}
		
	}
	
	public void next(){
		turn++;
		for(Player player: playerController.getCharaList()){
			if(maxSpd-player.getCharacterData().getSpd() % turn ==0) {
				
			}
		}
		for(Enemy enemy: enemyController.getCharaList()){
			if(enemy.getSpd()>maxSpd) maxSpd = enemy.getSpd();
		}
	}
	
	public void addPlayers(List <CharacterData> selectedCharas){
		playerController.addPlayers(level, 50, 100, selectedCharas);
	}
	
	public void addEnemys(int seed){
		enemyController.addEnemy(level, 100, 100,seed);
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
