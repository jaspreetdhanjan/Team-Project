package com.basementstudios.tag.ui;

import com.basementstudios.tag.*;
import com.basementstudios.tag.graphics.Bitmap;

public abstract class Interface {
	protected final ScreenManager manager;

	public Interface(ScreenManager manager) {
		this.manager = manager;
	}

	public abstract void inputTick(Input input);

	public abstract void renderSelectables(Bitmap bm);
}