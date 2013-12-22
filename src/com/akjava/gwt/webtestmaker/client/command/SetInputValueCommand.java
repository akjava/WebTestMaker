package com.akjava.gwt.webtestmaker.client.command;

import com.akjava.gwt.webtestmaker.client.AbstractTestCommand;

/*
 * for input type=text,password,hidden
 */
public class SetInputValueCommand extends AbstractTestCommand{
	public SetInputValueCommand(String name,String value){
		this.name=name;
		this.value=value;
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
