package com.version80.gwt.pdfViewer.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.version80.gwt.pdfViewer.client.service.PdfService;
import com.version80.gwt.pdfViewer.server.servlet.serviceImpl.PdfServiceImpl;
import com.version80.gwt.pdfViewer.shared.PdfPage;


public class PdfServlet extends RemoteServiceServlet implements PdfService{

	private static final long serialVersionUID = 4036459221191133291L;
	
	PdfService impl = new PdfServiceImpl();
	
	@Override
	public PdfPage getPdfPage(String pdfFilepath, boolean isAbsolutePath, int pageNumber) {
		return impl.getPdfPage(pdfFilepath, isAbsolutePath, pageNumber);
	}

}
