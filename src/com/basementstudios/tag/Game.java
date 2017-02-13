package com.basementstudios.tag;

import java.awt.*;
import java.awt.image.*;
import java.util.List;

import javax.swing.JFrame;

import com.basementstudios.tag.graphics.*;
import com.basementstudios.tag.graphics.Font;
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
	public static final int SCALE = 2;
	public static final String TITLE = "The Adventurers' Guild";
	public static final String VERSION = "Pre-Alpha 2.0";

	private boolean stop = false;
	private String fpsString = "";

	private BufferedImage screenImage;
	private int[] pixels;
	private Bitmap screenBitmap;
	private Input input;
	private Screen screen;

	private List<CharacterData> selectedCharas;

	public Game(List<CharacterData> selectedCharas) {
		this.selectedCharas = selectedCharas;

		Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
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

	public void setScreen(Screen screen) {
		this.screen = screen;
		screen.init(this);
	}

	public void run() {
		init();

		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60.0;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

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

			if (render) {
				render();
				frames++;
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				// System.out.println(ticks + " ticks, " + frames + " fps");
				fpsString = ticks + " ticks, " + frames + " fps";
				ticks = 0;
				frames = 0;
			}
		}
	}

	private void init() {
		requestFocus();

		screenImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) screenImage.getRaster().getDataBuffer()).getData();
		screenBitmap = new Bitmap(screenImage);

		input = new Input(this);
		setScreen(new MenuScreen(selectedCharas));
	}

	private void tick() {
		if (!hasFocus() && (screen instanceof GameScreen)) {
			setScreen(new PauseScreen(screen));
			return;
		}

		input.tick();
		screen.tick(input);
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		int wr = getWidth();
		int hr = getHeight();
		int w = WIDTH;
		int h = HEIGHT;

		screen.render(screenBitmap);
		Font.getInstance().drawShadowed(screenBitmap, VERSION, 6, 6, 0xffffff);
		Font.getInstance().drawShadowed(screenBitmap, fpsString, 6, 6 + 12, 0xffffff);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				pixels[x + y * w] = screenBitmap.pixels[x + y * w];
			}
		}
		w *= SCALE;
		h *= SCALE;

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, wr, hr);
		g.drawImage(screenImage, (w - wr) / 2, (h - hr) / 2, wr, hr, null);
		g.dispose();
		bs.show();
	}
}