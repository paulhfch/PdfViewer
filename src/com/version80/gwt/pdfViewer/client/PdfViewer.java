package com.version80.gwt.pdfViewer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.version80.gwt.pdfViewer.client.view.PdfDisplay;
import com.version80.gwt.pdfViewer.client.view.PdfDisplayControl;
import com.version80.gwt.pdfViewer.client.view.WarningPopup;
import com.version80.gwt.pdfViewer.shared.PdfPage;
import com.version80.gwt.pdfViewer.shared.service.PdfService;
import com.version80.gwt.pdfViewer.shared.service.PdfServiceAsync;

/**
 * <code>PdfViewer</code> consists of a GWT-widget and a servlet, which renders PDF pages into PNG images 
 * and displays the PDF pages in the GWT-widget. 
 * 
 * @author fangchen
 *
 */
public class PdfViewer extends PdfDisplay{

	private PdfDisplayControl pdfDisplayControl;
	private WarningPopup warningPopup;
	private PdfViewerContext pdfViewerContext;
	
	public PdfViewer(PdfViewerContext pdfViewerContext){
		this.pdfViewerContext = pdfViewerContext;
		pdfDisplayControl =  getPdfDisplayControl();
		
		initWarning();
		bind();
		style();
	}
	
	private void initWarning(){
		warningPopup = new WarningPopup();
	}
	
	private void bind(){
		pdfDisplayControl.getPrevButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(pdfViewerContext.getNumberOfPdfPages() != 0){
					if (pdfViewerContext.getCurrentPdfPageNumber() > 1) {
						callPdfRenderingService(
								pdfViewerContext.getCurrentPdfFilePath(),
								pdfViewerContext.getCurrentPdfPageNumber() - 1);
					} else {
						setWarning("This is the first page of the PDF file.");
					}
				}
			}
		});

		pdfDisplayControl.getNextButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (pdfViewerContext.getNumberOfPdfPages() != 0) {
					if (pdfViewerContext.getCurrentPdfPageNumber() < pdfViewerContext
							.getNumberOfPdfPages()) {
						callPdfRenderingService(
								pdfViewerContext.getCurrentPdfFilePath(),
								pdfViewerContext.getCurrentPdfPageNumber() + 1);
					} else {
						setWarning("You have reached the end of the PDF file.");
					}
				}
			}
		});
		
		pdfDisplayControl.getGoToText().addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER){
					goToPage( Integer.parseInt(pdfDisplayControl.getGoToText().getText()) );
				}
			}
		});

		pdfDisplayControl.getGoToButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				goToPage( Integer.parseInt(pdfDisplayControl.getGoToText().getText()) );
			}
		});

		pdfDisplayControl.getZoomIn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pdfViewerContext.setPdfEnlargeRate(pdfViewerContext.getPdfEnlargeRate() + pdfViewerContext.getZoomInRate());
				reloadPdfImg();
			}
		});

		pdfDisplayControl.getOriginalSize().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				pdfViewerContext.setPdfEnlargeRate(1);
				reloadPdfImg();
			}
		});
	}
	
	private void goToPage(int pageNumber){
		int original = pdfViewerContext.getCurrentPdfPageNumber();
		int goToPage = pageNumber;

		if (goToPage <= pdfViewerContext.getNumberOfPdfPages() && goToPage > 0) {
			callPdfRenderingService(pdfViewerContext.getCurrentPdfFilePath(), goToPage);
		}
		else{
			setWarning( "Error: page number is out of range." );
			pdfDisplayControl.getGoToText().setText(String.valueOf(original));
		}
	}
	
	private void style(){
		this.setStyleName("pdfDisplay");
		this.getPdfDisplayControl().setStyleName("pdfDisplayControl");
		
		if(pdfViewerContext.getPdfPageMaxWidth() != null
				&& pdfViewerContext.getPdfPageMaxHeight() != null){	
			PdfViewer.this.getPdfPagePanel().setWidth(pdfViewerContext.getPdfPageMaxWidth() + "px");
			PdfViewer.this.getPdfPagePanel().setHeight(pdfViewerContext.getPdfPageMaxHeight() + "px");
		}
		else{
			PdfViewer.this.getPdfPagePanel().setWidth(pdfViewerContext.getPdfPageWidth() + "px");
			PdfViewer.this.getPdfPagePanel().setHeight(pdfViewerContext.getPdfPageHeight() + "px");
		}
	}
	
	private void callPdfRenderingService(final String pdfFilepath, final int pageNumber) {
		PdfServiceAsync pdfService = (PdfServiceAsync) GWT.create(PdfService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) pdfService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "PdfService");
		
		pdfViewerContext.setCurrentPdfFilePath(pdfFilepath);

		AsyncCallback<PdfPage> callback = new PdfServiceCallback(this, pdfViewerContext);
		
		clearPdfImg();
		getPdfPagePanel().add(new HTML("<i>Loading...</i>"));
		
		if( pdfViewerContext.getSource() == PdfSource.ABSOLUTE_PATH ){
			pdfService.getPdfPageFromFile(pdfFilepath, pageNumber, callback);
		}
		
		if( pdfViewerContext.getSource() == PdfSource.RESOURCES_DIR ){
			pdfService.getPdfPageFromResourcesDir(pdfFilepath, pageNumber, callback);
		}
	}

	/**
	 * loads up the PDF file at the (relative/absolute)file path and displays 
	 * the first page.
	 * 
	 * @param filePath to the PDF file
	 * @param isAbsolutePath? 
	 * true - look for the file at the absolute path; 
	 * false - look for the file relative to the resources directory
	 */
	public void loadPdfFile( String filePath ){
		pdfViewerContext.setCurrentPdfFilePath(filePath);
		
		callPdfRenderingService(pdfViewerContext.getCurrentPdfFilePath(), 1);
	}
	
	private void reloadPdfImg() {
		HTML imageHtml = new HTML("<img style='display:block; "
				+ "width:" + pdfViewerContext.getPdfPageWidth() * pdfViewerContext.getPdfEnlargeRate() + "px;" 
				+ "height:" + pdfViewerContext.getPdfPageWidth() * pdfViewerContext.getPdfEnlargeRate() * pdfViewerContext.getPdfHeightToWidthRatio() + "px;'"
				+ "src='" + pdfViewerContext.getCurrentPdfImage() + "' />");
		
		clearPdfImg();
		this.getPdfPagePanel().add(imageHtml);
	}
	
	/*package*/ void clearPdfImg() {
		this.getPdfPagePanel().clear();
	}
	
	/**
	 * Pops up a warning dialog
	 * @param warningMessage warning text displayed to the user
	 */
	public void setWarning(String warningMessage){
		warningPopup.setWarningMessage(warningMessage);
		warningPopup.center();
	}
	
	/*package*/ HTMLPanel getViewerPdfPagePanel() {
		return super.getPdfPagePanel();
	}

	/*package*/ void setViewerPdfPagePanel(HTMLPanel pdfPagePanel) {
		super.setPdfPagePanel(pdfPagePanel);
	}

	/*package*/ PdfDisplayControl getViewerPdfDisplayControl() {
		return super.getPdfDisplayControl();
	}

	/*package*/ void setViewerPdfDisplayControl(PdfDisplayControl pdfDisplayControl) {
		super.setPdfDisplayControl(pdfDisplayControl);
	}
}
