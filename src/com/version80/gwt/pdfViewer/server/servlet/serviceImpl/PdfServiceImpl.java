package com.version80.gwt.pdfViewer.server.servlet.serviceImpl;

import java.net.URL;

import com.version80.gwt.pdfViewer.shared.PageLoadException;
import com.version80.gwt.pdfViewer.shared.PdfPage;
import com.version80.gwt.pdfViewer.shared.service.PdfService;


public class PdfServiceImpl implements PdfService{	

	@Override
	public PdfPage getPdfPageFromFile(String pdfFilepath, int pageNumber) throws PageLoadException{
		PdfPage page = null;
		String filepath = pdfFilepath;
	
		try {
			page = PdfRendererHelper.getPageInPDF(filepath, pageNumber);
		} catch (Exception e) {
			throw new PageLoadException(filepath, pageNumber);
		}
		
		return page;
	}

	@Override
	public PdfPage getPdfPageFromResourcesDir(String pdfFilepath, int pageNumber) throws PageLoadException{
		PdfPage page = null;
		URL filepath = this.getClass().getClassLoader().getResource(pdfFilepath);
	
		try {
			page = PdfRendererHelper.getPageInPDF(filepath.getPath(), pageNumber);
		} catch (Exception e) {
			throw new PageLoadException(filepath.getPath(), pageNumber);
		}
		
		return page;
	}
}
