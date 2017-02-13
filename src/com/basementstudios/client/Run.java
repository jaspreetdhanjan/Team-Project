package com.basementstudios.client;

import com.basementstudios.network.CharaSelect;
import com.basementstudios.network.InvalidTokenException;
import com.basementstudios.network.Token;
import com.basementstudios.tag.Game;

public class Run {

	public static void main(String[] args) {
		try {
			Token token  = new Token();
			new CharaSelect();
		} catch (InvalidTokenException e) {
			new Launcher();
		}

	}

}
