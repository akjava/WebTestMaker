package com.akjava.gwt.webtestmaker.client;

import java.util.ArrayList;
import java.util.List;

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
import com.akjava.lib.common.form.StaticValidators;
import com.akjava.lib.common.utils.ValuesUtils;
import com.google.common.base.Joiner;
import com.google.gwt.core.shared.GWT;

public class WebTestCsvConverter {
private static final int MODE_TEXT=0;
private static final int MODE_TITLE=1;
private static final int MODE_CHECKBOX=2;
private static final int MODE_INPUT=3;
private static final int MODE_SELECT=4;
private static final int MODE_TEXTAREA=5;
private static final int MODE_URL=6;
private static final int MODE_LINK=7;
private static final int MODE_BUTTON=8;
private static final int MODE_SUBMIT=9;

	public static List<TestCommand> linesToTestCommand(String text)throws InvalidCsvException{
		List<String> lines=ValuesUtils.toListLines(text);
		return linetoTestCommand(lines);
	}
	
	private static String[] validWords={"not","set","url","button","link","submit","text","title","input","textarea","checkbox","select","url","link","button","submit"};
	/*
	private static boolean doValidateWords(String[] words){
		for(String word:words){
			for(String v:validWords){
				if(word.equals(v)){
					return false;
				}
			}
		}
		
		return true;
	}
	**/

	private static List<TestCommand> linetoTestCommand(List<String> lines) throws InvalidCsvException{
		//initial data for empty data format
		List<TestCommand> testCommands=new ArrayList<TestCommand>();
		int lineNumber=0;
		for(String line:lines){
			lineNumber++;
			if(line.isEmpty()){
				continue;
			}
			String[] csv=line.split("\t");
			if(csv.length==0){
				//just tab case,usually happen on empty cell
				continue;
			}
			//parse first
			String words[]=csv[0].toLowerCase().split(" ");
			
			//parse special test
			if(csv[0].equals("test")){
				String name=csv.length>1?csv[1]:null;
				String description=csv.length>2?csv[2]:null;
				TestInfoCommand testInfo=new TestInfoCommand(name, description);
				testCommands.add(testInfo);
				
				String nameTest=WebTestMaker.renameToTargetName(name);
				if(!StaticValidators.asciiNumberAndCharAndUnderbarOnly().validate(nameTest)){
					throw new InvalidCsvException("invalid test name allowed(a-z,0-9, ,-,_) on line:"+line);
				}
				continue;
			}
			
			
			int mode=-1;
			boolean isNot=false;
			boolean isSet=false;
			for(String word:words){
				if(word.equals("not")){
					isNot=true;
				}else if(word.equals("set")){
					isSet=true;
				}else if(word.equals("text")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_TEXT;
					if(csv.length!=2){
						throw new InvalidCsvException("text need only value(total 2 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else if(word.equals("title")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_TITLE;
					if(csv.length!=2){
						throw new InvalidCsvException("title need only value(total 2 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else if(word.equals("input")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_INPUT;
					if(csv.length!=3){
						throw new InvalidCsvException("input need name and value(total 3 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else if(word.equals("checkbox")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_CHECKBOX;
					if(csv.length!=3){
						throw new InvalidCsvException("checkbox need name and value(total 3 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else if(word.equals("select")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_SELECT;
					if(csv.length!=3){
						throw new InvalidCsvException("select need name and value(total 3 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else if(word.equals("textarea")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_TEXTAREA;
					if(csv.length!=3){
						throw new InvalidCsvException("textarea need name and value(total 3 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else if(word.equals("url")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_URL;
					/*
					if(csv.length!=2){
						throw new InvalidCsvException("url need value only(total 2 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
					*/
					
					
				}else if(word.equals("link")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_LINK;
					if(csv.length!=2){
						throw new InvalidCsvException("link need label only(total 2 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else if(word.equals("button")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_BUTTON;
					if(csv.length!=2){
						throw new InvalidCsvException("button need label only(total 2 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else if(word.equals("submit")){
					if(mode!=-1){
						throw new InvalidCsvException("you can use only one of them on line "+lineNumber);
					}
					mode=MODE_SUBMIT;
					if(csv.length!=1){
						throw new InvalidCsvException("submit don't need any option(total 1 column).but on line "+lineNumber+" column "+csv.length+" "+Joiner.on(",").join(csv));
					}
				}else{
					throw new InvalidCsvException("using invalid phrase, "+word+" on line "+lineNumber+".you can use only:"+Joiner.on(",").join(validWords));	
				}
			}
			
			if(mode==-1){
				throw new InvalidCsvException("use one of them:text,title,input,textarea,checkbox,select,url,link,button,submit");
			}
			if(isNot&&isSet){
				throw new InvalidCsvException("use only not or set");
			}
			
			//validate set
			if(isSet){
				if(mode!=MODE_INPUT&&
				   mode!=MODE_TEXTAREA&&
				   mode!=MODE_CHECKBOX&&
				   mode!=MODE_SELECT
						){
					throw new InvalidCsvException("you can use 'set' only input or textarea or checkbox or select");
				}
			}
			//validate not
			if(isNot){
				if(mode!=MODE_INPUT&&
				   mode!=MODE_TEXTAREA&&
				   mode!=MODE_CHECKBOX&&
				   mode!=MODE_SELECT&&
				   mode!=MODE_TITLE&&
				   mode!=MODE_TEXT
						){
					throw new InvalidCsvException("you can use 'not' only input or textarea or checkbox or select");
				}
			}
			
			TestCommand command=null;
			if(isSet){
				if(mode==MODE_INPUT){
					command=new SetInputValueCommand(csv[1],csv[2]);
				}else if(mode==MODE_TEXTAREA){
					command=new SetTextAreaValueCommand(csv[1],csv[2]);
				}else if(mode==MODE_CHECKBOX){
					boolean bool=ValuesUtils.toBoolean(csv[2], false);
					command=new SetCheckboxValueCommand(csv[1],bool);
				}else if(mode==MODE_SELECT){
					command=new SetSelectValueCommand(csv[1],csv[2]);
				}
			}else{
			if(mode==MODE_TITLE){
				command=new CompareTitleCommand(csv[1],isNot);
			}else if(mode==MODE_TEXT){
				command=new CompareTextCommand(csv[1],isNot);
			}else if(mode==MODE_INPUT){
				command=new CompareInputValueCommand(csv[1],csv[2],isNot);
			}else if(mode==MODE_TEXTAREA){
				command=new CompareTextAreaValueCommand(csv[1],csv[2],isNot);
			}else if(mode==MODE_CHECKBOX){
				boolean bool=ValuesUtils.toBoolean(csv[2], false);
				command=new CompareCheckboxValueCommand(csv[1],bool,isNot);
			}else if(mode==MODE_SELECT){
				command=new CompareSelectValueCommand(csv[1],csv[2],isNot);
			}else if(mode==MODE_URL){
				if(csv.length==1){
				command=new OpenUrlCommand("");//same ad base
				}else{
					command=new OpenUrlCommand(csv[1]);	
				}
			}else if(mode==MODE_LINK){
				command=new ClickLinkCommand(csv[1]);
			}else if(mode==MODE_BUTTON){
				command=new ClickButtonCommand(csv[1]);
			}else if(mode==MODE_SUBMIT){
				command=new SubmitCommand();
			}
			
			}
			if(command==null){
				throw new InvalidCsvException("unknown error happend");
			}
			testCommands.add(command);
			
		}
		
		
		return testCommands;
	}
}
