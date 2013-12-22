package com.akjava.gwt.webtestmaker.client.command;

import com.akjava.gwt.webtestmaker.client.AbstractTestCommand;

public class SetCheckboxValueCommand extends AbstractTestCommand{
public SetCheckboxValueCommand(String name,boolean checked){
	this.name=name;
	this.checked=checked;
}
private boolean checked;
public boolean isChecked() {
	return checked;
}
public void setChecked(boolean checked) {
	this.checked = checked;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
private String name;
}
