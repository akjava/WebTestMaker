package com.akjava.gwt.webtestmaker.client.command;

public class CompareCheckboxValueCommand extends CompareCommand{
public CompareCheckboxValueCommand(String name,boolean checked){
	this.name=name;
	this.checked=checked;
}
public CompareCheckboxValueCommand(String name,boolean checked,boolean not){
	this(name,checked);
	setNot(not);
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
