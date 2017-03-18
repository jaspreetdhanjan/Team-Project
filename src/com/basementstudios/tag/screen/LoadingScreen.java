package com.basementstudios.tag.screen;

import com.basementstudios.tag.Input;
import com.basementstudios.tag.graphics.Bitmap;

/**
 * Waits for resources to be loaded.
 * 
 * @author Jaspreet Dhanjan
 */

public class LoadingScreen extends Screen {
	private static final int MIN_WAIT_TIME = 20 * 60;

	public static String status = "Loading";

	private final Screen whenFinished;
	private Thread task;
	private int tickCount = 0;
	private long lastTime;

	public LoadingScreen(Screen whenFinished, Runnable target) {
		this.whenFinished = whenFinished;
		task = new Thread(target);
	}

	public void init() {
		task.start();
		lastTime = System.currentTimeMillis();
	}

	public void tick(Input input) {
		tickCount++;

		if (!task.isAlive() && (System.currentTimeMillis() - lastTime) > MIN_WAIT_TIME) {
			screenManager.setScreen(whenFinished);
		}
	}

	public void renderScreen(Bitmap bm) {
		bm.clear();

		for (int i = 0; i < bm.pixels.length; i++) {
			bm.pixels[i] = ((i + tickCount) % 256);
		}

		int scale = 2;
		int xBob = (int) ((Math.sin(tickCount / 10.0) * 10.0) % 50.0);
		int yBob = (int) ((Math.cos(tickCount / 10.0) * 10.0) % 50.0);

		int xo = (bm.width - bm.getCharWidth(status) * scale) / scale;
		int yo = bm.height / 2;
		xo += xBob;
		yo += yBob;

		bm.setScale(scale, scale);
		bm.drawString(status, xo, yo, 0xffffff);

		scale = 1;
		xo = (bm.width - bm.getCharWidth(status) / 2) / 2;
		yo = (bm.height / 2) + 50;
		xo += xBob;
		yo += yBob;

		bm.setScale(scale, scale);
		bm.drawString(status, xo, yo, 0xffffff);
		bm.setScale(1, 1);
	}

	public void renderHud(Bitmap bm) {
		for (int i = 0; i < bm.pixels.length; i++) {
			bm.pixels[i] = ((i + tickCount) % 256);
		}
	}

	public boolean fullscreenDraw() {
		return true;
	}
}