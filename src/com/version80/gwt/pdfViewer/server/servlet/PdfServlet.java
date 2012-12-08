package com.version80.gwt.pdfViewer.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.version80.gwt.pdfViewer.server.servlet.serviceImpl.PdfServiceImpl;
import com.version80.gwt.pdfViewer.shared.PageLoadException;
import com.version80.gwt.pdfViewer.shared.PdfPage;
import com.version80.gwt.pdfViewer.shared.service.PdfService;


public class PdfServlet extends RemoteServiceServlet implements PdfService{

	private static final long serialVersionUID = 4036459221191133291L;
	
	PdfService impl = new PdfServiceImpl();

	@Override
	public PdfPage getPdfPageFromFile(String pdfFilepath, int pageNumber){

		try {
			return impl.getPdfPageFromFile(pdfFilepath, pageNumber);
		} catch (PageLoadException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public PdfPage getPdfPageFromResourcesDir(String pdfFilepath, int pageNumber){
		
		try {
			return impl.getPdfPageFromResourcesDir(pdfFilepath, pageNumber);
		} catch (PageLoadException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
