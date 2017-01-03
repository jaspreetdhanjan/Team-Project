package com.basementstudios.tag.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	private Vector2 position, rotation, scale;
	
	public abstract void tick(float delta);

	public abstract void render();

	public Vector2 getRotation() {
		return rotation;
	}

	public void setRotation(Vector2 rotation) {
		this.rotation = rotation;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getScale() {
		return scale;
	}

	public void setScale(Vector2 scale) {
		this.scale = scale;
	}
}