package com.akjava.gwt.webtestmaker.client.command;

import com.akjava.gwt.webtestmaker.client.AbstractTestCommand;

public abstract class CompareCommand extends AbstractTestCommand{
private boolean not;

public boolean isNot() {
	return not;
}

public void setNot(boolean not) {
	this.not = not;
}
}
