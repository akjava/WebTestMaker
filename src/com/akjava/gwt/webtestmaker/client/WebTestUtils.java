package com.akjava.gwt.webtestmaker.client;

import java.util.ArrayList;
import java.util.List;

import com.akjava.lib.common.tag.Tag;
import com.akjava.lib.common.tag.TagToStringConverter;

/**
 * temporary TODO create generating string class with more options
 * @author aki
 *
 */
public class WebTestUtils {
public static Tag testToTag(List<List<TestCommand>> testsCommand,String baseUrl) throws InvalidCsvException{
	Tag rootTag=new Tag("project");
	rootTag.setAttribute("default","web_test");
	
	Tag webtest=new Tag("target");
	webtest.setAttribute("name","web_test");
	rootTag.addChild(webtest);
	
	Tag tstamp=new Tag("tstamp");
	Tag format=new Tag("format").attr("property", "now.time").attr("pattern", "yyyyMMddhhmmssSSS");//for uniq value
	tstamp.addChild(format);
	webtest.addChild(tstamp);
	
	
	List<String> testNames=new ArrayList<String>();
	for(List<TestCommand> eachCommand:testsCommand){
		Tag tag=testToEachTag(eachCommand,baseUrl);
		rootTag.addChild(tag);
		Tag antcall=new Tag("antcall");
		String name=tag.getAttribute("name");
		antcall.setAttribute("target",name);
		webtest.addChild(antcall);
		if(testNames.contains(name)){
			throw new InvalidCsvException("duplicate test name:"+name);
		}
		testNames.add(name);
		webtest.addChild(new Tag("sleep").attr("seconds", "5"));//for local delay
	}
	return rootTag;
}

private static Tag testToEachTag(List<TestCommand> commands,String baseUrl){
	WebTest test=new WebTest("webtest","");
	if(baseUrl!=null && !baseUrl.isEmpty()){
		test.setBaseUrl(baseUrl);
	}
	for(TestCommand command:commands){
		test.addTestCommand(command);
	}
	
	Tag tag=WebTestDto.getWebTestToTestListFunction().apply(test);
	return tag;
}

public static String testToXmlText(Tag tag){
	return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+TagToStringConverter.convert(tag);
}
}
