package com.version80.gwt.pdfViewer.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * This view consists of the PDF navigation controls.
 * 
 * @author fangchen
 *
 */
public class PdfDisplayControl extends Composite implements HasText {

	private static pdfDisplayControlUiBinder uiBinder = GWT
			.create(pdfDisplayControlUiBinder.class);

	interface pdfDisplayControlUiBinder extends
			UiBinder<Widget, PdfDisplayControl> {
	}
			
	@UiField Button prevButton;
	@UiField Button nextButton;
	@UiField TextBox goToText;
	@UiField Button goToButton;
	@UiField Label numberOfPages;
	@UiField Button zoomIn;
	@UiField Button originalSize;

	public PdfDisplayControl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "PDF Display Control";
	}
	public Button getPrevButton() {
		return prevButton;
	}

	public void setPrevButton(Button prevButton) {
		this.prevButton = prevButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public void setNextButton(Button nextButton) {
		this.nextButton = nextButton;
	}
	
	public TextBox getGoToText() {
		return goToText;
	}

	public String getGoToPage() {
		return goToText.getText();
	}

	public void setGoToText(int goToPage) {
		this.goToText.setText(String.valueOf(goToPage));
	}

	public Button getGoToButton() {
		return goToButton;
	}

	public void setGoToButton(Button goToButton) {
		this.goToButton = goToButton;
	}

	public Label getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages.setText("/" + numberOfPages);
	}

	public Button getZoomIn() {
		return zoomIn;
	}

	public void setZoomIn(Button zoomIn) {
		this.zoomIn = zoomIn;
	}

	public Button getOriginalSize() {
		return originalSize;
	}

	public void setOriginalSize(Button originalSize) {
		this.originalSize = originalSize;
	}

}
