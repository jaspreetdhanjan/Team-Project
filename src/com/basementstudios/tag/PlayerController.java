package com.basementstudios.tag;

import java.util.ArrayList;
import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Mob;
import com.basementstudios.tag.mob.Player;

public class PlayerController extends ObjectControler {

	public void addPlayers(Level level, double x, double y, List<CharacterData> selectedCharas) {

		super.level = level;
		for (int i = 0; i < 3; i++) {
			Player p0 = new Player(x, y + 50 * i, selectedCharas.get(i));
			super.level.add(p0);
			super.charaList.add(p0);
		}
		attackMob=charaList.get(0);
	}

	public void tick() {
		for (int i=0; i< charaList.size(); i++) {
			Mob player = charaList.get(i);
			if (player.isAttacking) {
				if (player.x - player.xStart == 0 && player.isRetracting) {
					player.isAttacking = false;
					player.xa = 0;
				} else if (player.x - player.xStart == player.maxAttackFrame){
					player.isRetracting = true;
					player.getTarge().hurt(player, player.getDmg());
				}
				else if (player.isRetracting)
					player.xa = -1;
				else
					player.xa = 1;
				player.attemptMove();
			}
			removeDeadMob(player);
		}
	}

}