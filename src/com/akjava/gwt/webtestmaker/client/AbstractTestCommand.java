package com.akjava.gwt.webtestmaker.client;

public abstract class AbstractTestCommand implements TestCommand{
private String description;

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}
}
