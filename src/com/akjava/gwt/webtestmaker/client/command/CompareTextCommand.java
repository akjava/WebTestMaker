package com.akjava.gwt.webtestmaker.client.command;

import com.akjava.gwt.webtestmaker.client.AbstractTestCommand;

public class CompareTextCommand  extends CompareCommand{
private String text;
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public CompareTextCommand(String text){
	this.text=text;
}

public CompareTextCommand(String text,boolean not){
	this.text=text;
	setNot(not);
}
}
