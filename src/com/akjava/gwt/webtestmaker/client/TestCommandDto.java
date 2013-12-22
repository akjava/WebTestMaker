package com.akjava.gwt.webtestmaker.client;


import com.akjava.gwt.lib.client.LogUtils;
import com.akjava.gwt.webtestmaker.client.command.SetCheckboxValueCommand;
import com.akjava.gwt.webtestmaker.client.command.SetInputValueCommand;
import com.akjava.gwt.webtestmaker.client.command.SetSelectValueCommand;
import com.akjava.gwt.webtestmaker.client.command.SetTextAreaValueCommand;
import com.akjava.lib.common.form.FormFieldData;
import com.akjava.lib.common.form.StaticValidators;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class TestCommandDto {

	
	
	public static AvaiableSetFormFieldDataFilter getAvaiableSetFormFieldDataFilter(){
		return AvaiableSetFormFieldDataFilter.INSTANCE;
	}
	public enum AvaiableSetFormFieldDataFilter implements Predicate<FormFieldData>{
		INSTANCE;

		@Override
		public boolean apply(FormFieldData data) {
			boolean avaiable=true;
			switch(data.getType()){
			case FormFieldData.TYPE_TEXT_SHORT:
			case FormFieldData.TYPE_TEXT_LONG:
			case FormFieldData.TYPE_CHECK:
			case FormFieldData.TYPE_SELECT_SINGLE:
			case FormFieldData.TYPE_SELECT_MULTI:
			case FormFieldData.TYPE_NUMBER:
				break;
			default:
				avaiable=false;
			}
			
			return avaiable;
		}
		
	}
	public static FormFieldDataToSimpleSetWebTest getFormFieldDataToSimpleSetWebTest(){
		return FormFieldDataToSimpleSetWebTest.INSTANCE;
	}
	public enum FormFieldDataToSimpleSetWebTest implements Function<FormFieldData,TestCommand>{
		INSTANCE;
		@Override
		public TestCommand apply(FormFieldData data) {
			TestCommand command=null;
			switch(data.getType()){
			case FormFieldData.TYPE_TEXT_SHORT:
				//TODO more check
				if(data.getValidators().contains(StaticValidators.asciiNumberOnly())){
					command=new SetInputValueCommand(data.getKey(), "0");
				}else{
					command=new SetInputValueCommand(data.getKey(), "Value-"+data.getName());
				}
				break;
			case FormFieldData.TYPE_NUMBER:	
				command=new SetInputValueCommand(data.getKey(), "0");
				break;	
			case FormFieldData.TYPE_TEXT_LONG:
				command=new SetTextAreaValueCommand(data.getKey(), "Value-"+data.getName());
				break;
			case FormFieldData.TYPE_CHECK:
				command=new SetCheckboxValueCommand(data.getKey(), true);
				break;
			case FormFieldData.TYPE_SELECT_SINGLE:
			case FormFieldData.TYPE_SELECT_MULTI:
				if(data.getOptionValues().size()==0){
					LogUtils.log("error on getFormFieldDataToSimpleSetWebTest():need option value for select of "+data.getKey());
				}
				if(data.getOptionText().startsWith("@")){
				//maybe option just	
				command=new SetSelectValueCommand(data.getKey(), "0");//choose first one.
						
				}else{
				command=new SetSelectValueCommand(data.getKey(), data.getOptionValues().get(0).getValue());//choose first one.
				}
				break;
			default:
			}
			
			return command;
		}
		
	}
	
	public static FormFieldDataToSimpleEditWebTest getFormFieldDataToSimpleEditWebTest(){
		return FormFieldDataToSimpleEditWebTest.INSTANCE;
	}
	public enum FormFieldDataToSimpleEditWebTest implements Function<FormFieldData,TestCommand>{
		INSTANCE;
		@Override
		public TestCommand apply(FormFieldData data) {
			TestCommand command=null;
			switch(data.getType()){
			case FormFieldData.TYPE_TEXT_SHORT:
				if(data.getValidators().contains(StaticValidators.asciiNumberOnly())){
					command=new SetInputValueCommand(data.getKey(), "1");
				}else{
					command=new SetInputValueCommand(data.getKey(), "Edited-"+data.getName());
				}
				
				break;
			case FormFieldData.TYPE_NUMBER:	
				command=new SetInputValueCommand(data.getKey(), "1");
				break;
			case FormFieldData.TYPE_TEXT_LONG:
				command=new SetTextAreaValueCommand(data.getKey(), "Edited-"+data.getName());
				break;
			case FormFieldData.TYPE_CHECK:
				command=new SetCheckboxValueCommand(data.getKey(), false);
				break;
			case FormFieldData.TYPE_SELECT_SINGLE:
			case FormFieldData.TYPE_SELECT_MULTI:
				if(data.getOptionValues().size()==0){
					LogUtils.log("error on getFormFieldDataToSimpleSetWebTest():need option value for select of "+data.getKey());
				}
				if(data.getOptionText().startsWith("@")){
					command=new SetSelectValueCommand(data.getKey(), "1");
				}else{
				command=new SetSelectValueCommand(data.getKey(), data.getOptionValues().get(data.getOptionValues().size()-1).getValue());//choose first one.
				}
				break;
			default:
			}
			
			return command;
		}
		
	}
}
