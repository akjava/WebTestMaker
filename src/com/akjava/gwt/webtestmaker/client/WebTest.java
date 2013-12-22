package com.akjava.gwt.webtestmaker.client;

import java.util.ArrayList;
import java.util.List;

import com.akjava.gwt.lib.client.ValueUtils;
import com.akjava.lib.common.utils.ValuesUtils;

public class WebTest {
public WebTest(String name,String description){
	this.name=name;
	this.description=description;
}
private String name;
private String description;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

List<TestCommand> testCommands=new ArrayList<TestCommand>();
public List<TestCommand> getTestCommands() {
	return testCommands;
}
public void addTestCommand(TestCommand command){
	testCommands.add(command);
}
private Config config=new Config();

public Config getConfig() {
	return config;
}
public void setConfig(Config config) {
	this.config = config;
}


//TODO url tool
public void setBaseUrl(String url){
	int pend=url.indexOf("://");
	String protocol=null;
	if(pend!=-1){
		protocol=url.substring(0,pend);
	}
	url=url.substring(pend+3);
	int pageStart=url.indexOf("/");
	String page=null;
	String domainAndPort=null;
	if(pageStart!=-1){
		page=url.substring(pageStart);
		//TODO support query&hash
		domainAndPort=url.substring(0,pageStart);
	}else{
		domainAndPort=url;
	}
	String domain=null;
	String port=null;
	int portStart=domainAndPort.indexOf(":");
	if(portStart!=-1){
		port=domainAndPort.substring(portStart+1);
		domain=domainAndPort.substring(0,portStart);
	}else{
		domain=domainAndPort;
	}
	
	getConfig().setProtocol(protocol);
	getConfig().setHost(domain);
	getConfig().setPort(ValuesUtils.toInt(port, 80));
	getConfig().setPage(page);
	
	
}

public static class Config{

	private int port;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	private String host;
	private String protocol;
	private String page;
}
}
