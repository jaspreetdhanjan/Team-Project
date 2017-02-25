package com.basementstudios.tag.resource;

public abstract class Resource {
	private final ResourceType resourceType;

	public Resource(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public abstract void create();

	public abstract String getPath();

	public ResourceType getResourceType() {
		return resourceType;
	}
}