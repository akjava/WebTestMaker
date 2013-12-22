package com.akjava.gwt.webtestmaker.client.command;

public class CompareTextAreaValueCommand extends CompareCommand{
	public CompareTextAreaValueCommand(String name,String text){
		this.name=name;
		this.text=text;
	}
	public CompareTextAreaValueCommand(String name,String text,boolean not){
		this.name=name;
		this.text=text;
		setNot(not);
	}
private String name;
private String text;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
}
