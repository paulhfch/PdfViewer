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
import com.version80.gwt.pdfViewer.client.service.PdfService;
import com.version80.gwt.pdfViewer.client.service.PdfServiceAsync;
import com.version80.gwt.pdfViewer.client.view.PdfDisplay;
import com.version80.gwt.pdfViewer.client.view.PdfDisplayControl;
import com.version80.gwt.pdfViewer.client.view.WarningPopup;
import com.version80.gwt.pdfViewer.shared.PdfPage;

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
	
	public PdfViewer(){
		super();
		
		pdfViewerContext = new PdfViewerContext();
		pdfDisplayControl = this.getPdfDisplayControl();
		 
		initWarning();
		bind();
		style();
	}
	
	public PdfViewer(PdfViewerContext pdfViewerContext){
		super();
		
		this.pdfViewerContext = pdfViewerContext;
		pdfDisplayControl = this.getPdfDisplayControl();
		
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
				if (pdfViewerContext.getCurrentPdfPageNumber() > 1) {
					callPdfRenderingService(pdfViewerContext.getCurrentPdfFilePath(), pdfViewerContext.isAbsolutePath(),
							pdfViewerContext.getCurrentPdfPageNumber() - 1);
				}
				else{
					setWarning("This is the first page of the PDF file.");
				}
			}
		});

		pdfDisplayControl.getNextButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (pdfViewerContext.getCurrentPdfPageNumber() < pdfViewerContext.getNumberOfPdfPages()) {
					callPdfRenderingService(pdfViewerContext.getCurrentPdfFilePath(), pdfViewerContext.isAbsolutePath(),
							pdfViewerContext.getCurrentPdfPageNumber() + 1);
				}
				else{
					setWarning("You have reached the end of the PDF file.");
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
			callPdfRenderingService(pdfViewerContext.getCurrentPdfFilePath(), pdfViewerContext.isAbsolutePath(), goToPage);
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
	
	private void callPdfRenderingService(final String pdfFilepath, boolean isAbsolutePath, final int pageNumber) {
		PdfServiceAsync pdfService = (PdfServiceAsync) GWT.create(PdfService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) pdfService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "PdfService");

		AsyncCallback<PdfPage> callback = new AsyncCallback<PdfPage>() {
			public void onSuccess(PdfPage pdfPage) {
				HTML imageHtml = null;
				
				if( pdfPage == null ){				
					imageHtml = new HTML("<i>PDF page rendering failed: File Not Found. </i>");
				}
				else if (pdfPage.getPageImage() != null) {
					pdfViewerContext.setCurrentPdfFilePath(pdfFilepath);
					pdfViewerContext.setCurrentPdfPageNumber(pdfPage.getCurrentPageNumber());
					pdfViewerContext.setNumberOfPdfPages(pdfPage.getNumberOfPages());
					pdfViewerContext.setCurrentPdfImage(pdfPage.getPageImage());
					pdfViewerContext.setPdfPageWidth(pdfPage.getWidth());
					pdfViewerContext.setPdfPageHeight(pdfPage.getHeight());
					pdfViewerContext.setPdfHeightToWidthRatio(pdfPage.getHeight() / pdfPage.getWidth());
					
					imageHtml = new HTML("<img style='display:block; "
							+ "width:" + pdfViewerContext.getPdfPageWidth() * pdfViewerContext.getPdfEnlargeRate() + "px;" 
							+ "height:" + pdfViewerContext.getPdfPageWidth() * pdfViewerContext.getPdfEnlargeRate() * pdfViewerContext.getPdfHeightToWidthRatio() + "px;'"
							+ "src='" + pdfPage.getPageImage() + "' />");
				}

				PdfViewer.this.getPdfPagePanel().clear();
				PdfViewer.this.getPdfPagePanel().add(imageHtml);

				PdfViewer.this.getPdfDisplayControl().setNumberOfPages(pdfViewerContext.getNumberOfPdfPages());
				PdfViewer.this.getPdfDisplayControl().setGoToText(pdfViewerContext.getCurrentPdfPageNumber());
			}

			public void onFailure(Throwable caught) {
				PdfViewer.this.getPdfDisplayControl().setNumberOfPages(0);
				PdfViewer.this.getPdfDisplayControl().setGoToText(0);
				
				setWarning("Error: cannot load file " + pdfViewerContext.getCurrentPdfFilePath());
				clearPdfImg();
			}
		};
		
		clearPdfImg();
		PdfViewer.this.getPdfPagePanel().add(new HTML("<i>Loading...</i>"));
		
		pdfService.getPdfPage(pdfFilepath, isAbsolutePath, pageNumber, callback);
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
	public void loadPdfFile( String filePath, boolean isAbsolutePath ){
		pdfViewerContext.setCurrentPdfFilePath(filePath);
		pdfViewerContext.setAbsolutePath(isAbsolutePath);
		
		callPdfRenderingService(pdfViewerContext.getCurrentPdfFilePath(), pdfViewerContext.isAbsolutePath(), 1);
	}
	
	private void reloadPdfImg() {
		HTML imageHtml = new HTML("<img style='display:block; "
				+ "width:" + pdfViewerContext.getPdfPageWidth() * pdfViewerContext.getPdfEnlargeRate() + "px;" 
				+ "height:" + pdfViewerContext.getPdfPageWidth() * pdfViewerContext.getPdfEnlargeRate() * pdfViewerContext.getPdfHeightToWidthRatio() + "px;'"
				+ "src='" + pdfViewerContext.getCurrentPdfImage() + "' />");
		
		clearPdfImg();
		this.getPdfPagePanel().add(imageHtml);
	}
	
	private void clearPdfImg() {
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
}
