package com.version80.gwt.pdfViewer.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Popup panel to display error messages
 * 
 * @author fangchen
 *
 */
public class WarningPopup extends PopupPanel{
	FlexTable body;
	Label warningMessage = new Label();
	Button okButton;
	
	public WarningPopup(){
		super();
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		
		body = new FlexTable();
		this.setWidget(createContent());
	}
	
	private FlexTable createContent(){
		warningMessage = new Label();
		okButton = new Button("OK");
		
		body.setWidget(0, 0, warningMessage);
		body.setWidget(1, 0, okButton);
		
		bind();
		
		return body;
	}
	
	private void bind(){
		okButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				WarningPopup.this.hide();
			}
		});
	}
	
	public void setWarningMessage(String message){
		warningMessage.setText(message);
	}
	
	public void clearWarningMessage(){
		warningMessage.setText("");
	}
	
	public void setTimedWarningMessage(String message){
		warningMessage.setText(message);
	}
	
	 @Override
	public void center() {
		super.center();
		
		okButton.setFocus(true);
	}

}
