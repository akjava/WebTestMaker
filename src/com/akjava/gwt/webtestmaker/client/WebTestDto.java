package com.akjava.gwt.webtestmaker.client;

import com.akjava.gwt.webtestmaker.client.command.ClickButtonCommand;
import com.akjava.gwt.webtestmaker.client.command.ClickLinkCommand;
import com.akjava.gwt.webtestmaker.client.command.CompareCheckboxValueCommand;
import com.akjava.gwt.webtestmaker.client.command.CompareInputValueCommand;
import com.akjava.gwt.webtestmaker.client.command.CompareSelectValueCommand;
import com.akjava.gwt.webtestmaker.client.command.CompareTextAreaValueCommand;
import com.akjava.gwt.webtestmaker.client.command.CompareTextCommand;
import com.akjava.gwt.webtestmaker.client.command.CompareTitleCommand;
import com.akjava.gwt.webtestmaker.client.command.OpenUrlCommand;
import com.akjava.gwt.webtestmaker.client.command.SetCheckboxValueCommand;
import com.akjava.gwt.webtestmaker.client.command.SetInputValueCommand;
import com.akjava.gwt.webtestmaker.client.command.SetSelectValueCommand;
import com.akjava.gwt.webtestmaker.client.command.SetTextAreaValueCommand;
import com.akjava.gwt.webtestmaker.client.command.SubmitCommand;
import com.akjava.gwt.webtestmaker.client.command.TestInfoCommand;
import com.akjava.lib.common.tag.Tag;
import com.google.common.base.Function;

public class WebTestDto {
public enum WebTestToCanooWebTestListFunction implements Function<WebTest,Tag>{
	INSTANCE;

	@Override
	public Tag apply(WebTest test) {
		Tag tag=new Tag("target");
		
		
		
		Tag webtest=new Tag("webtest");

		tag.addChild(webtest);
		
		
		Tag config=new Tag("config");
		if(test.getConfig().getHost()!=null){
			config.setAttribute("host", test.getConfig().getHost());
		}
		if(test.getConfig().getHost()!=null){
			config.setAttribute("basePath", test.getConfig().getPage());
		}
		if(test.getConfig().getHost()!=null){
			config.setAttribute("protocol", test.getConfig().getProtocol());
		}
		if(test.getConfig().getHost()!=null){
			config.setAttribute("port", ""+test.getConfig().getPort());
		}
		
		
		
		
		//TODO set protocol,host,port,page
		
		Tag option=new Tag("option");
		option.setAttribute("name","ThrowExceptionOnScriptError");
		option.setAttribute("value", "false");
		config.addChild(option);
		webtest.addChild(config);
		
		for(TestCommand command:test.getTestCommands()){
			if(command instanceof TestInfoCommand){
				TestInfoCommand info=(TestInfoCommand)command;
				if(info.getName()!=null){
					test.setName(info.getName());
				}
				if(info.getDescription()!=null){
					test.setDescription(info.getDescription());
				}
			}
			
			else if(command instanceof OpenUrlCommand){
				webtest.addChild(convertOpenUrlCommand((OpenUrlCommand)command));
			}
			//click
			else if(command instanceof SubmitCommand){
				webtest.addChild(convertSubmitCommand((SubmitCommand)command));
			}else if(command instanceof ClickButtonCommand){
				webtest.addChild(convertClickButtonCommand((ClickButtonCommand)command));
			}else if(command instanceof ClickLinkCommand){
				webtest.addChild(convertClickLinkCommand((ClickLinkCommand)command));
			}
			//compare
			else if(command instanceof CompareTitleCommand){
				webtest.addChild(convertCompareTitleCommand((CompareTitleCommand)command));
			}
			else if(command instanceof CompareTextCommand){
				webtest.addChild(convertCompareTextCommand((CompareTextCommand)command));
			}
			else if(command instanceof CompareCheckboxValueCommand){
				webtest.addChild(convertCompareCheckboxValueCommand((CompareCheckboxValueCommand)command));
			}
			else if(command instanceof CompareInputValueCommand){
				webtest.addChild(convertCompareInputValueCommand((CompareInputValueCommand)command));
			}
			else if(command instanceof CompareSelectValueCommand){
				webtest.addChild(convertCompareSelectValueCommand((CompareSelectValueCommand)command));
			}
			else if(command instanceof CompareTextAreaValueCommand){
				webtest.addChild(convertCompareTextAreaValueCommand((CompareTextAreaValueCommand)command));
			}
			
			//set
			else if(command instanceof SetCheckboxValueCommand){
				webtest.addChild(convertSetCheckboxValueCommand((SetCheckboxValueCommand)command));
			}
			
			else if(command instanceof SetInputValueCommand){
				webtest.addChild(convertSetInputValueCommand((SetInputValueCommand)command));
			}
			
			else if(command instanceof SetSelectValueCommand){
				webtest.addChild(convertSetSelectValueCommand((SetSelectValueCommand)command));
			}
			
			else if(command instanceof SetTextAreaValueCommand){
				webtest.addChild(convertSetTextAreaValueCommand((SetTextAreaValueCommand)command));
			}
		}
		//do last,because test command maybe update it.
		tag.setAttribute("name",WebTestMaker.renameToTargetName(test.getName()));
		webtest.setAttribute("name",test.getName());
		if(test.getDescription()!=null){
			webtest.setAttribute("description", test.getDescription());
		}
		return tag;
	}	
}

private static Tag convertOpenUrlCommand(OpenUrlCommand command){
	Tag tag=new Tag("invoke");
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("url",command.getUrl());
	return tag;
}

private static Tag convertClickButtonCommand(ClickButtonCommand command){
	Tag tag=new Tag("clickButton");
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("label",command.getText());
	return tag;
}
private static Tag convertSubmitCommand(SubmitCommand command){
	Tag tag=new Tag("clickButton");
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("xpath","//*[@type='submit']");//because input or button
	return tag;
}

private static Tag convertClickLinkCommand(ClickLinkCommand command){
	Tag tag=new Tag("clickLink");
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("label",command.getText());
	return tag;
}

private static Tag convertCompareTitleCommand(CompareTitleCommand command){
	Tag root=null;
	Tag tag=null;
	if(command.isNot()){
		root=new Tag("not");
		tag=new Tag("verifyTitle");
		root.addChild(tag);
	}else{
		tag=new Tag("verifyTitle");
		root=tag;
	}
	
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("text",command.getTitle());
	return root;
}

private static Tag convertCompareTextCommand(CompareTextCommand command){
	Tag root=null;
	Tag tag=null;
	if(command.isNot()){
		root=new Tag("not");
		tag=new Tag("verifyText");
		root.addChild(tag);
	}else{
		tag=new Tag("verifyText");
		root=tag;
	}
	
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("text",command.getText());
	return root;
}

private static Tag convertCompareCheckboxValueCommand(CompareCheckboxValueCommand command){
	Tag root=null;
	Tag tag=null;
	if(command.isNot()){
		root=new Tag("not");
		tag=new Tag("verifyCheckbox");
		root.addChild(tag);
	}else{
		tag=new Tag("verifyCheckbox");
		root=tag;
	}
	
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("name",command.getName());
	tag.setAttribute("checked",""+command.isChecked());
	return root;
}

private static Tag convertCompareInputValueCommand(CompareInputValueCommand command){
	Tag root=null;
	Tag tag=null;
	if(command.isNot()){
		root=new Tag("not");
		tag=new Tag("verifyInputField");
		root.addChild(tag);
	}else{
		tag=new Tag("verifyInputField");
		root=tag;
	}
	
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("name",command.getName());
	tag.setAttribute("value",command.getValue());
	return root;
}

private static Tag convertCompareSelectValueCommand(CompareSelectValueCommand command){
	Tag root=null;
	Tag tag=null;
	if(command.isNot()){
		root=new Tag("not");
		tag=new Tag("verifySelectField");
		root.addChild(tag);
	}else{
		tag=new Tag("verifySelectField");
		root=tag;
	}
	
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("name",command.getName());
	tag.setAttribute("value",command.getValue());
	return root;
}

private static Tag convertCompareTextAreaValueCommand(CompareTextAreaValueCommand command){
	Tag root=null;
	Tag tag=null;
	if(command.isNot()){
		root=new Tag("not");
		tag=new Tag("verifyTextarea");
		root.addChild(tag);
	}else{
		tag=new Tag("verifyTextarea");
		root=tag;
	}
	
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("name",command.getName());
	tag.setAttribute("text",command.getText());
	return root;
}

private static Tag convertSetInputValueCommand(SetInputValueCommand command){
	Tag tag=new Tag("setInputField");
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("name",command.getName());
	tag.setAttribute("value",command.getValue());
	return tag;
}

private static Tag convertSetCheckboxValueCommand(SetCheckboxValueCommand command){
	Tag tag=new Tag("setCheckbox");
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("name",command.getName());
	tag.setAttribute("checked",""+command.isChecked());
	return tag;
}

private static Tag convertSetSelectValueCommand(SetSelectValueCommand command){
	Tag tag=new Tag("setSelectField");
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("name",command.getName());
	tag.setAttribute("value",command.getValue());
	return tag;
}

private static Tag convertSetTextAreaValueCommand(SetTextAreaValueCommand command){
	Tag tag=new Tag("setInputField");
	tag.setSingleTag(true);
	if(command.getDescription()!=null){
		tag.setAttribute("description",command.getDescription());
	}
	tag.setAttribute("name",command.getName());
	tag.setAttribute("value",command.getValue());
	return tag;
}

public static WebTestToCanooWebTestListFunction getWebTestToTestListFunction(){
	return WebTestToCanooWebTestListFunction.INSTANCE;
}
}
