package com.version80.gwt.pdfViewer.server.servlet.serviceImpl;

import java.net.URL;

import com.version80.gwt.pdfViewer.client.service.PdfService;
import com.version80.gwt.pdfViewer.shared.PdfPage;


public class PdfServiceImpl implements PdfService{	
	
	public PdfPage getPdfPage(String relativeFilePath, int pageNumber){
		PdfPage page = null;
		URL pdfFilepath = this.getClass().getClassLoader().getResource(relativeFilePath);
		
		try{
			page = PdfRendererHelper.getPageInPDF(pdfFilepath.getPath(), pageNumber);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return page;
	}
}
