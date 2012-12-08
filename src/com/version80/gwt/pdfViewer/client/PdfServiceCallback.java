package com.version80.gwt.pdfViewer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.version80.gwt.pdfViewer.shared.PdfPage;

public class PdfServiceCallback implements AsyncCallback<PdfPage>{
	
	private PdfViewer pdfViewer;
	private PdfViewerContext pdfViewerContext;	
	
	public PdfServiceCallback(PdfViewer pdfViewer, PdfViewerContext pdfViewerContext){
		this.pdfViewer = pdfViewer;
		this.pdfViewerContext = pdfViewerContext;
	}
	
	public void onSuccess(PdfPage pdfPage) {
		HTML imageHtml = null;
		
		if( pdfPage == null ){				
			imageHtml = new HTML("<i>PDF page rendering failed: File Not Found. </i>");
		}
		else if (pdfPage.getPageImage() != null) {
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

		pdfViewer.getViewerPdfPagePanel().clear();
		pdfViewer.getViewerPdfPagePanel().add(imageHtml);

		pdfViewer.getViewerPdfDisplayControl().setNumberOfPages(pdfViewerContext.getNumberOfPdfPages());
		pdfViewer.getViewerPdfDisplayControl().setGoToText(pdfViewerContext.getCurrentPdfPageNumber());
	}

	public void onFailure(Throwable caught) {
		pdfViewer.getViewerPdfDisplayControl().setNumberOfPages(0);
		pdfViewer.getViewerPdfDisplayControl().setGoToText(0);
		
		pdfViewer.setWarning("Error: cannot load file " + pdfViewerContext.getCurrentPdfFilePath());
		pdfViewer.clearPdfImg();
	}
}
