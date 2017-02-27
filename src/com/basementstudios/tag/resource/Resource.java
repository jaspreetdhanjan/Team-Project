package com.basementstudios.tag.resource;

import com.basementstudios.tag.ResourceManager;

public abstract class Resource {
	private final ResourceType resourceType;

	public Resource(ResourceType resourceType, ResourceManager resourceManager) {
		this.resourceType = resourceType;
		resourceManager.addResource(this);
	}

	public abstract void create();

	public abstract String getPath();

	public ResourceType getResourceType() {
		return resourceType;
	}
}