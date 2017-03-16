package com.basementstudios.tag;

import com.basementstudios.network.CharacterLoader;
import com.basementstudios.tag.graphics.Bitmap;
import com.basementstudios.tag.screen.LoadingScreen;
import com.basementstudios.tag.screen.TitleScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Entry-point for the main application.
 * 
 * @author Jaspreet Dhanjan
 */

public class Game extends Canvas implements Runnable {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "The Adventurers' Guild";
	public static final String VERSION = "Prototype 3";
	public static final int HUD_WIDTH = WIDTH;
	public static final int HUD_HEIGHT = 175;
	public static final int VIEWPORT_WIDTH = WIDTH;
	public static final int VIEWPORT_HEIGHT = HEIGHT - HUD_HEIGHT;
	public static final String URL = "theadventurersguild.co.uk";
	private static final long serialVersionUID = 1L;
	private boolean stop = false;
    private boolean debug = false;
    private boolean fpsLock = true;
	private String fpsString = "";

	private BufferedImage screenImg;
	private int[] pixels;
	private Bitmap viewportBitmap, hudBitmap;

	private ScreenManager screenManager;

	public Game() {
		Dimension d = new Dimension(WIDTH, HEIGHT);
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

			if (render || !fpsLock) {
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
				ResourceManager.i.loadAll();
				Characters.setAvailable(new CharacterLoader().getCharacters());
				Characters.load("doc/chara.ser");
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

		screenRender();
		hudRender();

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(screenImg, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();
	}

	private void screenRender() {
		screenManager.renderScreen(viewportBitmap);
		renderToScreen();

		for (int y = 0; y < VIEWPORT_HEIGHT; y++) {
			for (int x = 0; x < VIEWPORT_WIDTH; x++) {
				pixels[x + y * VIEWPORT_WIDTH] = viewportBitmap.pixels[x + y * VIEWPORT_WIDTH];
			}
		}
	}

	private void renderToScreen() {
        if (debug) {
            viewportBitmap.drawStringShadowed(VERSION, 6, 6, 0xffffff);
            viewportBitmap.drawStringShadowed(fpsString, 6, 6 + 22, 0xffffff);
        }
    }

	private void hudRender() {
		screenManager.renderHud(hudBitmap);

		for (int y = 0; y < HUD_HEIGHT; y++) {
			int toffs = y + VIEWPORT_HEIGHT;
			for (int x = 0; x < HUD_WIDTH; x++) {
				pixels[x + toffs * HUD_WIDTH] = hudBitmap.pixels[x + y * HUD_WIDTH];
			}
		}
	}
}