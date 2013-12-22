package com.akjava.gwt.webtestmaker.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.akjava.gwt.lib.client.StorageControler;
import com.akjava.gwt.lib.client.StorageException;
import com.akjava.gwt.lib.client.widget.PasteValueReceiveArea;
import com.akjava.gwt.lib.client.widget.TabInputableTextArea;
import com.akjava.gwt.webtestmaker.client.command.TestInfoCommand;
import com.akjava.gwt.webtestmaker.client.resources.Bundles;
import com.akjava.lib.common.form.StaticValidators;
import com.akjava.lib.common.tag.Tag;
import com.akjava.lib.common.tag.TagToStringConverter;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebTestMaker implements EntryPoint {
	private TabInputableTextArea input;
	private TextArea output;
	private Label infoLabel;
	StorageControler storageControler=new StorageControler();
	public void onModuleLoad() {
		HorizontalPanel root=new HorizontalPanel();
		RootPanel.get().add(root);
		VerticalPanel leftVertical=new VerticalPanel();
		root.add(leftVertical);
		
		//
		HorizontalPanel baseUrlPanel=new HorizontalPanel();
		leftVertical.add(baseUrlPanel);
		baseUrlPanel.add(new Label("BaseUrl"));
		baseUrlBox = new TextBox();
		baseUrlBox.setWidth("250px");
		baseUrlPanel.add(baseUrlBox);
		baseUrlBox.setText(storageControler.getValue("baseUrl", "http://localhost:8888/"));
		
		
		HorizontalPanel infoPanel=new HorizontalPanel();
		leftVertical.add(infoPanel);
		infoLabel=new Label();
		infoPanel.add(infoLabel);
		
		//add controler
		HorizontalPanel addControlerBase=new HorizontalPanel();
		addControlerBase.setWidth("100%");
		HorizontalPanel addControler=new HorizontalPanel();
		addControlerBase.add(addControler);
		leftVertical.add(addControlerBase);
		
		notCheckbox = new CheckBox("not");
		addControler.add(notCheckbox);
		notCheckbox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(setCheckbox.getValue()){
					setCheckbox.setValue(false);
				}
			}
		});
		setCheckbox = new CheckBox("set");
		addControler.add(setCheckbox);
		setCheckbox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(notCheckbox.getValue()){
					notCheckbox.setValue(false);
				}
			}
		});
		
		
		addCommandList = new ListBox();
		addCommandList.addItem("test");
		addCommandList.addItem("url");
		addCommandList.addItem("link");
		addCommandList.addItem("button");
		addCommandList.addItem("submit");
		addCommandList.addItem("title");
		addCommandList.addItem("text");
		addCommandList.addItem("input");
		addCommandList.addItem("textarea");
		addCommandList.addItem("checkbox");
		addCommandList.addItem("select");
		addCommandList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String type=addCommandList.getItemText(addCommandList.getSelectedIndex());
				addItemSelectionChanged(type);
			}
		});
		
		addControler.add(addCommandList);
		
		
		value1Label = new Label("url");
		addControler.add(value1Label);
		value1Box = new TextBox();
		value1Box.setWidth("100px");
		addControler.add(value1Box);
		
		value2Label = new Label("");
		addControler.add(value2Label);
		
		value2Box = new TextBox();
		value2Box.setWidth("100px");
		addControler.add(value2Box);
		
		Button addButton=new Button("add");
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				addItem();
			}

			
		});
		
		
		HorizontalPanel spacer=new HorizontalPanel();
		spacer.setWidth("100%");
		spacer.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		addControlerBase.add(spacer);
		spacer.add(addButton);
		
		PasteValueReceiveArea test=new PasteValueReceiveArea();
		 test.setStylePrimaryName("readonly");
		 test.setText("Click(Focus) & Paste Here");
		 leftVertical.add(test);
		 test.setSize("600px", "60px");
		 test.setFocus(true);
		 test.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				input.setText(event.getValue());
				doConvert();
			}
			 
		});
		 
		 leftVertical.add(new Label("Csv(tab only)"));
		 input = new TabInputableTextArea();
		 //input.setText(storageControler.getValue("inputCsv", ""));
		 //GWTHTMLUtils.setPlaceHolder(input, "className,package,servletName,path");
		 input.setSize("600px","200px");
		 leftVertical.add(input);
		 Button convert=new Button("Convert",new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doConvert();
			}
		});
		 leftVertical.add(convert);
		 HorizontalPanel controler=new HorizontalPanel();
		 leftVertical.add(controler);
		 controler.add(convert);
		 controler.add(new Label("Preset"));
		 
		 presetList=new ValueListBox<TextResource>(new Renderer<TextResource>() {

			@Override
			public String render(TextResource object) {
				if(object==null){
					return null;
				}
				return object.getName();
			}

			@Override
			public void render(TextResource object, Appendable appendable)
					throws IOException {
			}
		});
		 List<TextResource> resources=new ArrayList<TextResource>();
		 resources.add(Bundles.INSTANCE.simple());
		 resources.add(Bundles.INSTANCE.addtest());
		 presetList.setValue(Bundles.INSTANCE.simple());
		 presetList.setAcceptableValues(resources);
		 presetList.addValueChangeHandler(new ValueChangeHandler<TextResource>() {

			@Override
			public void onValueChange(ValueChangeEvent<TextResource> event) {
				input.setText(event.getValue().getText());
			}
			 
		});
		 controler.add(presetList);
		 
		 VerticalPanel rightVertical=new VerticalPanel();
		 root.add(rightVertical);
		 output = new TextArea();
		 output.setSize("600px","400px");
		 rightVertical.add(output);
		 
		 //TODO restore from last data
		 input.setText(storageControler.getValue("webtest", Bundles.INSTANCE.simple().getText()));
		 
		 addItemSelectionChanged("test");
	}
	
	ValueListBox<TextResource> presetList;
	private ListBox addCommandList;
	private CheckBox notCheckbox;
	private CheckBox setCheckbox;
	private Label value1Label;
	private TextBox value1Box;
	private Label value2Label;
	private TextBox value2Box;
	private TextBox baseUrlBox;

	private void addItem() {
		String type=addCommandList.getItemText(addCommandList.getSelectedIndex());
		//TODO error check
		String error="";
		
		if(type.equals("test")){
			String value=renameToTargetName(value1Box.getText());
			boolean valid=StaticValidators.asciiNumberAndCharAndUnderbarOnly().validate(value);
			if(!valid){
				error+="name character must be a-z,0-9,space,_ or -";
			}
		}
		
		if(value1Box.isEnabled()&&value1Box.getText().isEmpty()){
			if(!type.equals("url")){//url allow empty
			error+=value1Label.getText()+" is empty\n";
			}
		}
		if(value2Box.isEnabled()&&value2Box.getText().isEmpty()){
			
			if(!type.equals("test")){
				if(type.equals("checkbox")){
					value2Box.setText("false");
				}else{
				error+=value2Label.getText()+" is empty\n";
				}
			}
		}
		
		if(!error.isEmpty()){
			Window.alert("[Error]\n"+error);
			return;
		}
		
		String firstColumn="";
		if(notCheckbox.isEnabled()&&notCheckbox.getValue()){
			firstColumn="not ";
		}
		
		if(setCheckbox.isEnabled()&&setCheckbox.getValue()){
			firstColumn="set ";
		}
		firstColumn+=type;
		String csv=firstColumn;
		
		if(!value1Box.getText().isEmpty()){
			csv+="\t"+value1Box.getText();
		}
		
		if(!value2Box.getText().isEmpty()){
			csv+="\t"+value2Box.getText();
		}
		String lastText=input.getText();
		if(!lastText.endsWith("\n")){
			lastText+="\n";
		}
		input.setText(lastText+csv+"\n");
	}
	
	private void addItemSelectionChanged(String type){
		
		if(type.equals("url")){
			value1Label.setText("url");
			value2Label.setText("");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(false);
			notCheckbox.setEnabled(false);
			setCheckbox.setEnabled(false);
			infoLabel.setText("url:open url");
		}else if(type.equals("link")){
			value1Label.setText("label");
			value2Label.setText("");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(false);
			notCheckbox.setEnabled(false);
			setCheckbox.setEnabled(false);
			infoLabel.setText("link:open link");
		}else if(type.equals("button")){
			value1Label.setText("label");
			value2Label.setText("");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(false);
			notCheckbox.setEnabled(false);
			setCheckbox.setEnabled(false);
			infoLabel.setText("button:just click button.if you'd like to open url.use link command");
		}else if(type.equals("submit")){
			value1Label.setText("");
			value2Label.setText("");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(false);
			value2Box.setEnabled(false);
			notCheckbox.setEnabled(false);
			setCheckbox.setEnabled(false);
			infoLabel.setText("submit:submit first one");
		}else if(type.equals("title")){
			value1Label.setText("title");
			value2Label.setText("");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(false);
			notCheckbox.setEnabled(true);
			setCheckbox.setEnabled(false);
			infoLabel.setText("title:compare exact title or not title");
		}else if(type.equals("text")){
			value1Label.setText("htmltext");
			value2Label.setText("");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(false);
			notCheckbox.setEnabled(true);
			setCheckbox.setEnabled(false);
			infoLabel.setText("text:contain html text or not.(if you wish compare input-field use input)");
		}else if(type.equals("input")){
			value1Label.setText("name");
			value2Label.setText("value");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(true);
			notCheckbox.setEnabled(true);
			setCheckbox.setEnabled(true);
			infoLabel.setText("input:set or compare input-field");
		}else if(type.equals("textarea")){
			value1Label.setText("name");
			value2Label.setText("value");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(true);
			notCheckbox.setEnabled(true);
			setCheckbox.setEnabled(true);
			infoLabel.setText("textarea:set or compare textarea-field");
		}else if(type.equals("checkbox")){
			value1Label.setText("name");
			value2Label.setText("checked(true/false)");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(true);
			notCheckbox.setEnabled(true);
			setCheckbox.setEnabled(true);
			infoLabel.setText("checkbox:set or compare checkbox-field");
		}else if(type.equals("select")){
			value1Label.setText("name");
			value2Label.setText("value");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(true);
			notCheckbox.setEnabled(true);
			setCheckbox.setEnabled(true);
			infoLabel.setText("select:set or compare select-field");
		}if(type.equals("test")){
			value1Label.setText("name");
			value2Label.setText("description");
			value1Box.setText("");
			value2Box.setText("");
			value1Box.setEnabled(true);
			value2Box.setEnabled(true);
			notCheckbox.setEnabled(false);
			setCheckbox.setEnabled(false);
			infoLabel.setText("test:split test");
		}
		value1Box.setVisible(value1Box.isEnabled());
		value2Box.setVisible(value2Box.isEnabled());
	}
	
	private List<List<TestCommand>> splitByTest(List<TestCommand> commands){
		List<List<TestCommand>> tests=new ArrayList<List<TestCommand>>();
		List<TestCommand> test=new ArrayList<TestCommand>();
		for(TestCommand command:commands){
			if(command instanceof TestInfoCommand){
				//split
				if(test.isEmpty()){
					test.add(command);
					tests.add(test);
				}else{
					test=new ArrayList<TestCommand>();
					tests.add(test);
					test.add(command);
				}
			}else{
				
				if(test.isEmpty()){
					tests.add(test);
				}
				test.add(command);
			}
		}
		return tests;
	}
	

	
	public static String renameToTargetName(String name){
		String value=name.replace(" ", "");
		 value=value.replace("-", "_");
		return value;
	}
	protected void doConvert() {
		//TODO
		//parse from csv 
		try {
			//csv to command
			List<TestCommand> rawcommands=WebTestCsvConverter.linesToTestCommand(input.getText());
			//split by test
			List<List<TestCommand>> testsCommand=splitByTest(rawcommands);
			
			//convert text
			String outputText=WebTestUtils.testToXmlText(WebTestUtils.testToTag(testsCommand, baseUrlBox.getText()));
			
			output.setText(outputText);
			
			
			
			//stora last values.
			if(!baseUrlBox.getText().isEmpty()){
				try {
					storageControler.setValue("baseUrl", baseUrlBox.getText());
				} catch (StorageException e) {
					e.printStackTrace();
				}
			}
			
			if(!input.getText().isEmpty()){
				try {
					storageControler.setValue("webtest",input.getText());
				} catch (StorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (InvalidCsvException e) {
			output.setText(e.getMessage());
			e.printStackTrace();
		}
		
		
		/*
		WebTest test=new WebTest("test1","test1 desc");
		
		test.addTestCommand(new OpenUrlCommand("test.html","test1"));
		test.addTestCommand(new CompareTitleCommand("TestTitle"));
		test.addTestCommand(new CompareTextCommand("Hello Html"));
		test.addTestCommand(new CompareInputValueCommand("text","inputtext"));
		test.addTestCommand(new CompareTextAreaValueCommand("textarea","text"));
		
		test.addTestCommand(new CompareCheckboxValueCommand("checkbox",true));
		test.addTestCommand(new CompareSelectValueCommand("select","v2"));
		
		test.addTestCommand(new ClickLinkCommand("Link"));
		test.addTestCommand(new CompareTitleCommand("test2"));
		
		test.addTestCommand(new OpenUrlCommand("test.html","test1"));
		
		test.addTestCommand(new CompareTitleCommand("TestTitle2",true));
		test.addTestCommand(new SetInputValueCommand("text","text1"));
		test.addTestCommand(new SetTextAreaValueCommand("textarea","text2"));
		test.addTestCommand(new SetCheckboxValueCommand("checkbox",false));
		test.addTestCommand(new SetSelectValueCommand("select","v1"));
		
		test.addTestCommand(new SubmitCommand());
		test.addTestCommand(new CompareTitleCommand("test2"));
		
		Tag tag=WebTestDto.getWebTestToTestListFunction().apply(test);
		output.setText(TagToStringConverter.convert(tag));
		*/
	}
}
