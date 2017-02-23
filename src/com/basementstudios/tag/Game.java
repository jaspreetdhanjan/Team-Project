package com.basementstudios.tag;

import java.awt.*;
import java.awt.image.*;
import java.util.List;

import javax.swing.JFrame;

import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.screen.*;
import com.basementstudios.network.CharacterData;

/**
 * Entry-point for the main application.
 * 
 * @author Jaspreet Dhanjan
 */

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	private static final int SCALE = 2;
	private static final int SCALED_WIDTH = WIDTH * SCALE;
	private static final int SCALED_HEIGHT = HEIGHT * SCALE;

	private static final int HUD_WIDTH = WIDTH;
	private static final int HUD_HEIGHT = 100;
	private static final int VIEWPORT_WIDTH = WIDTH;
	private static final int VIEWPORT_HEIGHT = HEIGHT - HUD_HEIGHT;

	public static final String TITLE = "The Adventurers' Guild";
	public static final String VERSION = "Pre-Alpha 2.0";

	private boolean stop = false;
	private String fpsString = "";

	private BufferedImage viewportImg, hudImg;
	private int[] viewportPixels, hudPixels;
	private Bitmap viewportBitmap, hudBitmap;
	private Input input;

	private ScreenManager screenManager;

	public Game(List<CharacterData> availableCharacters) {
		PlayerController.availableCharacters = availableCharacters;

		Dimension d = new Dimension(SCALED_WIDTH, SCALED_HEIGHT);
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);

		JFrame frame = new JFrame(TITLE);
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		new Thread(this, "Game Thread").start();
	}

	public void run() {
		init();

		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();

		while (!stop) {
			long nowTime = System.nanoTime();
			unprocessed += (nowTime - lastTime) / nsPerTick;
			lastTime = nowTime;

			boolean render = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed--;
				render = true;
			}

			if (render) {
				render();
				frames++;
			}

			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				// System.out.println(ticks + " ticks, " + frames + " fps");
				fpsString = ticks + " ticks, " + frames + " fps";
				ticks = 0;
				frames = 0;
			}
		}
	}

	private void init() {
		requestFocus();

		viewportImg = new BufferedImage(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, BufferedImage.TYPE_INT_RGB);
		hudImg = new BufferedImage(HUD_WIDTH, HUD_HEIGHT, BufferedImage.TYPE_INT_RGB);

		viewportPixels = ((DataBufferInt) viewportImg.getRaster().getDataBuffer()).getData();
		hudPixels = ((DataBufferInt) hudImg.getRaster().getDataBuffer()).getData();

		viewportBitmap = new Bitmap(viewportImg);
		hudBitmap = new Bitmap(hudImg);

		input = new Input(this);

		screenManager = new ScreenManager(input, new TitleScreen());
	}

	private void tick() {
		input.tick();
		screenManager.tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screenRender();
		hudRender();

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(viewportImg, 0, 0, VIEWPORT_WIDTH * SCALE, VIEWPORT_HEIGHT * SCALE, null);
		g.drawImage(hudImg, 0, VIEWPORT_HEIGHT * SCALE, HUD_WIDTH * SCALE, HUD_HEIGHT * SCALE, null);
		g.dispose();
		bs.show();
	}

	// Draws the actual screen
	private void screenRender() {
		screenManager.renderScreen(viewportBitmap);

		renderToScreen();
		for (int y = 0; y < VIEWPORT_HEIGHT; y++) {
			for (int x = 0; x < VIEWPORT_WIDTH; x++) {
				viewportPixels[x + y * VIEWPORT_WIDTH] = viewportBitmap.pixels[x + y * VIEWPORT_WIDTH];
			}
		}
	}

	private void renderToScreen() {
		viewportBitmap.drawStringShadowed(VERSION, 6, 6, 0xffffff);
		viewportBitmap.drawStringShadowed(fpsString, 6, 6 + 12, 0xffffff);
	}

	// Draws the heads up display
	private void hudRender() {
		screenManager.renderHud(hudBitmap);

		for (int y = 0; y < HUD_HEIGHT; y++) {
			for (int x = 0; x < HUD_WIDTH; x++) {
				hudPixels[x + y * HUD_WIDTH] = hudBitmap.pixels[x + y * HUD_WIDTH];
			}
		}
	}
}