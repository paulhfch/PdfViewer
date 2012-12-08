package com.version80.gwt.pdfViewer.shared.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.version80.gwt.pdfViewer.shared.PdfPage;

public interface PdfServiceAsync {
	
	void getPdfPageFromFile(String pdfFilepath, int pageNumber, AsyncCallback<PdfPage> callback);
	
	void getPdfPageFromResourcesDir(String pdfFilepath, int pageNumber, AsyncCallback<PdfPage> callback);
}
