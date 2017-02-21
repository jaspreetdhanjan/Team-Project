package com.basementstudios.tag;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles and updates input received from the keyboard.
 * 
 * @author Jaspreet Dhanjan
 */

public class Input implements KeyListener, FocusListener {
	public class Key {
		public int presses, absorbs;
		public boolean down, clicked;

		public Key() {
			keys.add(this);
		}

		public void toggle(boolean pressed) {
			if (down != pressed) down = pressed;
			if (pressed) presses++;
		}

		public void tick() {
			if (absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}

		public void release() {
			down = false;
		}
	}

	private boolean[] keys_ = new boolean[255];
	private boolean[] locksKeys = new boolean[255];
	public List<Key> keys = new ArrayList<Key>();
	public List<Character> charsTyped = new ArrayList<Character>();

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key enter = new Key();
	public Key space = new Key();

	private boolean hasFocus = true;

	public Input(Game game) {
		game.addKeyListener(this);
		game.addFocusListener(this);

		charsTyped.clear();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		keys_[e.getKeyCode()] = true;
		toggle(e, true);
		handlePress(e);
	}

	public void keyReleased(KeyEvent e) {
		keys_[e.getKeyCode()] = false;
		locksKeys[e.getKeyCode()] = false;
		toggle(e, false);
	}

	public void focusGained(FocusEvent e) {
		hasFocus = true;
	}

	public void focusLost(FocusEvent e) {
		hasFocus = false;
		releaseAll();
	}

	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}

	private void handlePress(KeyEvent e) {
		charsTyped.add(e.getKeyChar());
	}

	private void toggle(KeyEvent ke, boolean pressed) {
		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD8) up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);

		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2) down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);

		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4) left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);

		if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6) right.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);

		if (ke.getKeyCode() == KeyEvent.VK_ENTER) enter.toggle(pressed);

		if (ke.getKeyCode() == KeyEvent.VK_SPACE) space.toggle(pressed);
	}

	private void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).release();
		}
	}

	public boolean isDown(int key) {
		return keys_[key];
	}
	
	public boolean pressedOnce(int key){
		if(keys_[key]&&!locksKeys[key]){
			locksKeys[key]=true;
			return true;
		}
		return false;
	}

	public boolean hasFocus() {
		return hasFocus;
	}
}