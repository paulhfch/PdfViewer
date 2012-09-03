package com.version80.gwt.pdfViewer.server.servlets.serviceImpl;

import com.version80.gwt.pdfViewer.client.services.PdfService;
import com.version80.gwt.pdfViewer.server.PdfRendererHelper;
import com.version80.gwt.pdfViewer.shared.PdfPage;


public class PdfServiceImpl implements PdfService{
	PdfRendererHelper pdfRendererHelper = new PdfRendererHelper();
	
	public PdfPage getPdfPage(String pdfFilepath, int pageNumber){
		PdfPage page = null;
		
		try{
			page = pdfRendererHelper.getPageInPDF(pdfFilepath, pageNumber);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return page;
	}
}
