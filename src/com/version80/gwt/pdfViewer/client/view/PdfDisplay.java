package com.version80.gwt.pdfViewer.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * PdfDisplay aggregates the PDF page rendering panel and the 
 * PDF navigation controls.
 * 
 * @author fangchen
 *
 */
public class PdfDisplay extends Composite implements HasText {
	private static PdfDisplayUiBinder uiBinder = GWT
			.create(PdfDisplayUiBinder.class);
	@UiField Label title;
	@UiField HTMLPanel pdfPagePanel;
	@UiField VerticalPanel pdfdisplay;
	@UiField PdfDisplayControl pdfDisplayControl;
	
	interface PdfDisplayUiBinder extends UiBinder<Widget, PdfDisplay> {
	}

	public PdfDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getText() {
		return "PDF Display";
	}

	@Override
	public void setText(String text) {
	}

	public String getTitle() {
		return title.getText();
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	protected VerticalPanel getPdfdisplay() {
		return pdfdisplay;
	}

	protected void setPdfdisplay(VerticalPanel pdfdisplay) {
		this.pdfdisplay = pdfdisplay;
	}

	protected HTMLPanel getPdfPagePanel() {
		return pdfPagePanel;
	}

	protected void setPdfPagePanel(HTMLPanel pdfPagePanel) {
		this.pdfPagePanel = pdfPagePanel;
	}

	protected PdfDisplayControl getPdfDisplayControl() {
		return pdfDisplayControl;
	}

	protected void setPdfDisplayControl(PdfDisplayControl pdfDisplayControl) {
		this.pdfDisplayControl = pdfDisplayControl;
	}

}
