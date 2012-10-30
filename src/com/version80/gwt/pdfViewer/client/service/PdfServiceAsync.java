package com.version80.gwt.pdfViewer.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.version80.gwt.pdfViewer.shared.PdfPage;


public interface PdfServiceAsync {
	void getPdfPage(String pdfFilepath, boolean isAbsolutePath, int pageNumber, AsyncCallback<PdfPage> callback);
}
