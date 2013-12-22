package com.akjava.gwt.webtestmaker.client.command;

import com.akjava.gwt.webtestmaker.client.AbstractTestCommand;
import com.akjava.lib.common.tag.Tag;

public class OpenUrlCommand extends AbstractTestCommand{
private String url;
	public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}



public OpenUrlCommand(String url){
	super();
	setUrl(url);
}

public OpenUrlCommand(String url,String description){
	super();
	setUrl(url);
	setDescription(description);
}

}
