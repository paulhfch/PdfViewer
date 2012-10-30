package com.version80.gwt.pdfViewer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.version80.gwt.pdfViewer.client.service.PdfService;
import com.version80.gwt.pdfViewer.client.service.PdfServiceAsync;
import com.version80.gwt.pdfViewer.client.view.PdfDisplay;
import com.version80.gwt.pdfViewer.client.view.PdfDisplayControl;
import com.version80.gwt.pdfViewer.client.view.WarningPopup;
import com.version80.gwt.pdfViewer.shared.PdfPage;

/**
 * PdfViewer renders PDF files on the server into Base64 Strings and
 * displays the images in the browser.
 * 
 * @author fangchen
 *
 */
public class PdfViewer extends PdfDisplay{

	private static String currentPdfFilePath = null;
	private static String currentPdfImage = null;
	private static int currentPdfPageNumber = -1;
	private static int numberOfPdfPages = -1;
	private static double pdfPageWidth = 500;
	private static double pdfPageHeight = 500;
	
	private static double pdfEnlargeRate = 1;
	private final static double ZoomInRate = 0.2;
	private static double pdfHeightToWidthRatio = -1;
	
	private WarningPopup warningPopup;
	
	public PdfViewer(){
		super();
		
		initWarning();
		bind();
		style();
	}
	
	private void initWarning(){
		warningPopup = new WarningPopup();
	}
	
	private void bind(){
		final PdfDisplayControl pdfDisplayControl = this.getPdfDisplayControl();
		
		pdfDisplayControl.getPrevButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentPdfPageNumber > 1) {
					callPdfRenderingService(currentPdfFilePath,
							currentPdfPageNumber - 1);
				}
				else{
					setWarning("This is the first page of the PDF file.");
				}
			}
		});

		pdfDisplayControl.getNextButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (currentPdfPageNumber < numberOfPdfPages) {
					callPdfRenderingService(currentPdfFilePath,
							currentPdfPageNumber + 1);
				}
				else{
					setWarning("You have reached the end of the PDF file.");
				}
			}
		});

		pdfDisplayControl.getGoToButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int goToPage = Integer.parseInt(pdfDisplayControl.getGoToPage());

				if (goToPage <= numberOfPdfPages && goToPage > 0) {
					callPdfRenderingService(currentPdfFilePath, goToPage);
				}
			}
		});

		pdfDisplayControl.getZoomIn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pdfEnlargeRate += ZoomInRate;
				reloadPdfImg();
			}
		});

		pdfDisplayControl.getOriginalSize().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pdfEnlargeRate = 1;
				reloadPdfImg();
			}
		});
	}
	
	/**
	 * Override this method to modify widget CSS styling
	 */
	protected void style(){
		this.setStyleName("pdfDisplay");
		this.getPdfDisplayControl().setStyleName("pdfDisplayControl");
	}
	
	private void callPdfRenderingService(final String pdfFilepath, final int pageNumber) {
		PdfServiceAsync pdfService = (PdfServiceAsync) GWT
				.create(PdfService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) pdfService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "PdfService");

		AsyncCallback<PdfPage> callback = new AsyncCallback<PdfPage>() {
			public void onSuccess(PdfPage pdfPage) {
				HTML imageHtml = null;
				
				if( pdfPage == null ){				
					imageHtml = new HTML("<i>PDF page rendering failed: File Not Found. </i>");
				}
				else if (pdfPage.getPageImage() != null) {
					currentPdfFilePath = pdfFilepath;
					currentPdfPageNumber = pdfPage.getCurrentPageNumber();
					numberOfPdfPages = pdfPage.getNumberOfPages();
					currentPdfImage = pdfPage.getPageImage();
					pdfPageWidth = pdfPage.getWidth();
					pdfPageHeight = pdfPage.getHeight();
					pdfHeightToWidthRatio = pdfPageHeight / pdfPageWidth;
					
					imageHtml = new HTML("<img style='display:block; "
							+ "width:" + pdfPageWidth * pdfEnlargeRate + "px;" 
							+ "height:" + pdfPageWidth * pdfEnlargeRate * pdfHeightToWidthRatio + "px;'"
							+ "src='" + pdfPage.getPageImage() + "' />");
				}

				PdfViewer.this.getPdfPagePanel().clear();
				PdfViewer.this.getPdfPagePanel().add(imageHtml);
				PdfViewer.this.getPdfPagePanel().setWidth(pdfPageWidth + "px");
				PdfViewer.this.getPdfPagePanel().setHeight(pdfPageHeight + "px");

				PdfViewer.this.getPdfDisplayControl().setNumberOfPages(numberOfPdfPages);
				PdfViewer.this.getPdfDisplayControl().setGoToText(currentPdfPageNumber);
			}

			public void onFailure(Throwable caught) {
				setWarning("Internal Error: Please inform the admin.");

				Window.alert("ERROR: " + caught.toString());
			}
		};
		
		PdfViewer.this.getPdfPagePanel().clear();
		PdfViewer.this.getPdfPagePanel().add(new HTML("<i>Loading...</i>"));
		
		pdfService.getPdfPage(pdfFilepath, pageNumber, callback);
	}
	
	/**
	 * loads up the PDF file at the file path and displays 
	 * the first page.
	 * @param filePath relative to the resources directory
	 */
	public void loadPdfFile( String filePath ){
		currentPdfFilePath = filePath;
		
		callPdfRenderingService(currentPdfFilePath , 1);
	}
	
	private void reloadPdfImg() {
		HTML imageHtml = new HTML("<img style='display:block; " 
				+ "width:" + pdfPageWidth * pdfEnlargeRate + "px;" 
				+ "height:" + pdfPageWidth * pdfEnlargeRate * pdfHeightToWidthRatio + "px;'"
				+ "src='" + currentPdfImage + "' />");

		this.getPdfPagePanel().clear();
		this.getPdfPagePanel().add(imageHtml);
	}
	
	/**
	 * Pops up a warning dialog
	 * @param warningMessage
	 */
	public void setWarning(String warningMessage){
		warningPopup.setWarningMessage(warningMessage);
		warningPopup.center();
	}
}
