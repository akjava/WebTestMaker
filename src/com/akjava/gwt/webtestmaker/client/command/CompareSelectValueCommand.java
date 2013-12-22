package com.akjava.gwt.webtestmaker.client.command;

public class CompareSelectValueCommand extends CompareCommand{
	public CompareSelectValueCommand(String name,String value){
		this.name=name;
		this.value=value;
	}
	public CompareSelectValueCommand(String name,String value,boolean not){
		this(name,value);
		setNot(not);
	}
private String name;
private String value;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
}
