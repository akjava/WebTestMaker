package com.akjava.gwt.webtestmaker.client.command;

import com.akjava.gwt.webtestmaker.client.AbstractTestCommand;

public class CompareTitleCommand extends CompareCommand{
private String title;

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}
public CompareTitleCommand(String title){
	this.title=title;
}
public CompareTitleCommand(String title,boolean not){
	this.title=title;
	setNot(not);
}
}
