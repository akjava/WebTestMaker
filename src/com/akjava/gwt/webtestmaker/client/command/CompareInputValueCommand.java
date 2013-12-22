package com.akjava.gwt.webtestmaker.client.command;

public class CompareInputValueCommand extends CompareCommand{
private String name;
private String value;
public CompareInputValueCommand(String name,String value){
	this.name=name;
	this.value=value;
}
public CompareInputValueCommand(String name,String value,boolean not){
	this.name=name;
	this.value=value;
	setNot(not);
}
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
