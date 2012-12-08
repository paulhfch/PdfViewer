package com.version80.gwt.pdfViewer.shared.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.version80.gwt.pdfViewer.shared.PageLoadException;
import com.version80.gwt.pdfViewer.shared.PdfPage;

public interface PdfService extends RemoteService{
	
	PdfPage getPdfPageFromFile(String pdfFilepath, int pageNumber) throws PageLoadException;
	
	PdfPage getPdfPageFromResourcesDir(String pdfFilepath, int pageNumber) throws PageLoadException;
}
