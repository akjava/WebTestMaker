package com.akjava.gwt.webtestmaker.client.command;

import com.akjava.gwt.webtestmaker.client.AbstractTestCommand;

public class ClickButtonCommand extends AbstractTestCommand{
private String text;
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public ClickButtonCommand(String text){
	this.text=text;
}
}
