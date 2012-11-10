package com.version80.gwt.pdfViewer.server.servlet.serviceImpl;

import java.net.URL;

import com.version80.gwt.pdfViewer.client.service.PdfService;
import com.version80.gwt.pdfViewer.shared.PdfPage;


public class PdfServiceImpl implements PdfService{	
	
	public PdfPage getPdfPage(String filePath, boolean isAbsolutePath, int pageNumber){
		PdfPage page = null;
		String file = filePath;
		
		if( !isAbsolutePath ){
			URL pdfFilepath = this.getClass().getClassLoader().getResource(filePath);
			file = pdfFilepath.getPath();
		}
	
		try {
			page = PdfRendererHelper.getPageInPDF(file, pageNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return page;
	}
}
