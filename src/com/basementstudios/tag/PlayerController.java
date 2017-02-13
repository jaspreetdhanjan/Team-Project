package com.basementstudios.tag;

import java.util.List;

import com.basementstudios.network.CharacterData;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.graphics.SpriteSheet;
import com.basementstudios.tag.level.Level;
import com.basementstudios.tag.mob.Player;

public class PlayerController {
	public static final int PLAYER_NONE = 0;
	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;
	public static final int PLAYER_3 = 3;

	private Player p0, p1, p2;
	private Level level;

	private Player selectedPlayer = null;
	private int selectionIndex = PLAYER_NONE;

	public void addPlayers(Level level, double x, double y, List<CharacterData> selectedCharas) {
		this.level = level;

		p0 = new Player(x, y + 30 * 0, selectedCharas.get(PLAYER_1 - 1));
		p1 = new Player(x, y + 30 * 1, selectedCharas.get(PLAYER_2 - 1));
		p2 = new Player(x, y + 30 * 2, selectedCharas.get(PLAYER_3 - 1));

		this.level.add(p0);
		this.level.add(p1);
		this.level.add(p2);
	}

	public void select(int selectionIndex) {
		this.selectionIndex = selectionIndex;

		if (selectionIndex == PLAYER_NONE) selectedPlayer = null;
		if (selectionIndex == PLAYER_1) selectedPlayer = p0;
		if (selectionIndex == PLAYER_2) selectedPlayer = p1;
		if (selectionIndex == PLAYER_3) selectedPlayer = p2;
	}

	public void render(Bitmap bm) {
		if (selectedPlayer != null) {
			int yOffs = (int) (Math.sin(System.currentTimeMillis() % 250.0 / 100.0) * 5.0);
			int xp = (int) (selectedPlayer.x + 8);
			int yp = (int) (selectedPlayer.y - 20) + yOffs;
			bm.render(SpriteSheet.entities[0][0], xp, yp, 0xffffff);
		}
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public int getSelected() {
		return selectionIndex;
	}

	public boolean attemptMove(double xa, double ya) {
		if (selectedPlayer == null) return false;

		selectedPlayer.xa = xa;
		selectedPlayer.ya = ya;
		selectedPlayer.attemptMove();
		return true;
	}
}