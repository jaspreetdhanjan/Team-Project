package com.basementstudios.tag;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles and updates input received from the keyboard. Additionally, clears input when focus is lost.
 * 
 * @author Jaspreet Dhanjan
 */

public class Input implements KeyListener, FocusListener {
	public class Key {
		private int[] keyCodes;
		private int presses, absorbs;
		private boolean down, clicked;

		public Key(int... keyCodes) {
			this.keyCodes = keyCodes;
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

		public boolean isDown() {
			return down;
		}

		public boolean isClicked() {
			return clicked;
		}
	}

	private List<Key> keys = new ArrayList<Key>();
	private boolean hasFocus = true;

	public Key up = new Key(KeyEvent.VK_W, KeyEvent.VK_UP);
	public Key down = new Key(KeyEvent.VK_S, KeyEvent.VK_DOWN);
	public Key left = new Key(KeyEvent.VK_A, KeyEvent.VK_LEFT);
	public Key right = new Key(KeyEvent.VK_D, KeyEvent.VK_RIGHT);
	public Key enter = new Key(KeyEvent.VK_ENTER);
	public Key space = new Key(KeyEvent.VK_SPACE);
	public Key num1 = new Key(KeyEvent.VK_1);
	public Key num2 = new Key(KeyEvent.VK_2);
	public Key num3 = new Key(KeyEvent.VK_3);
	public Key esc = new Key(KeyEvent.VK_ESCAPE);

	public Input(Component frame) {
		frame.addKeyListener(this);
		frame.addFocusListener(this);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		//System.out.println("Pressed -> " + e.getKeyChar());
		toggle(e, true);
	}

	public void keyReleased(KeyEvent e) {
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
		keys.forEach(key -> key.tick());
	}

	private void toggle(KeyEvent ke, boolean pressed) {
		for (Key key : keys) {
			for (int i = 0; i < key.keyCodes.length; i++) {
				if (key.keyCodes[i] == ke.getKeyCode()) {
					key.toggle(pressed);
				}
			}
		}
	}

	public void releaseAll() {
		keys.forEach(key -> key.release());
	}

	public boolean hasFocus() {
		return hasFocus;
	}
}