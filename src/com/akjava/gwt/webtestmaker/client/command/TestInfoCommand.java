package com.akjava.gwt.webtestmaker.client.command;

import com.akjava.gwt.webtestmaker.client.AbstractTestCommand;

public class TestInfoCommand extends AbstractTestCommand{
private String name;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
public TestInfoCommand(String name,String description){
	setName(name);
	setDescription(description);
}

}
