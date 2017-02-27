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

	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;
	private static final int SCALE = 2;

	private static final int SCALED_WIDTH = WIDTH * SCALE;
	private static final int SCALED_HEIGHT = HEIGHT * SCALE;

	private static final int HUD_WIDTH = WIDTH;
	private static final int HUD_HEIGHT = 100;
	private static final int VIEWPORT_WIDTH = WIDTH;
	private static final int VIEWPORT_HEIGHT = HEIGHT - HUD_HEIGHT;

	public static final String TITLE = "The Adventurers' Guild";
	public static final String VERSION = "Pre-Alpha 2.0";

	public static final String URL = "theadventurersguild.co.uk";

	private boolean stop = false;
	private boolean fpsLock = true;
	private String fpsString = "";

	private BufferedImage screenImg;
	private int[] pixels;
	private Bitmap viewportBitmap, hudBitmap;

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

			boolean render = false;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed--;
				render = true;
			}

			if (render && fpsLock) {
				render();
				frames++;
			}

			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				fpsString = ticks + " ticks, " + frames + " fps";
				ticks = 0;
				frames = 0;
			}
		}
	}

	private void init() {
		screenImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) screenImg.getRaster().getDataBuffer()).getData();
		viewportBitmap = new Bitmap(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		hudBitmap = new Bitmap(HUD_WIDTH, HUD_HEIGHT);

		LoadingScreen loadingScreen = new LoadingScreen(new TitleScreen(), new Runnable() {
			public void run() {
				// TODO: Ideally put all the network (character) loading stuff here.
				// Just use the launcher for login verification.
				ResourceManager.i.loadAll();
			}
		});

		screenManager = new ScreenManager(new Input(this), loadingScreen);
		requestFocus();
	}

	private void tick() {
		screenManager.tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

//		if (!screenManager.getCurrentScreen().fullscreenDraw()) {
			screenRender(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
			hudRender(HUD_WIDTH, HUD_HEIGHT);
//		} else {
//			screenRender(WIDTH, HEIGHT);
//		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(screenImg, 0, 0, SCALED_WIDTH, SCALED_HEIGHT, null);
		g.dispose();
		bs.show();
	}

	private void screenRender(int w, int h) {
		screenManager.renderScreen(viewportBitmap);
		renderToScreen();

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				pixels[x + y * w] = viewportBitmap.pixels[x + y * w];
			}
		}
	}

	private void renderToScreen() {
		viewportBitmap.drawStringShadowed(VERSION, 6, 6, 0xffffff);
		viewportBitmap.drawStringShadowed(fpsString, 6, 6 + 12, 0xffffff);
	}

	private void hudRender(int w, int h) {
		screenManager.renderHud(hudBitmap);

		for (int y = 0; y < h; y++) {
			int toffs = y + VIEWPORT_HEIGHT;
			for (int x = 0; x < w; x++) {
				pixels[x + toffs * w] = hudBitmap.pixels[x + y * w];
			}
		}
	}
}