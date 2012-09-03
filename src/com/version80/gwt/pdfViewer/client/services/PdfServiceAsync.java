package com.version80.gwt.pdfViewer.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.version80.gwt.pdfViewer.shared.PdfPage;


public interface PdfServiceAsync {
	void getPdfPage(String pdfFilepath, int pageNumber, AsyncCallback<PdfPage> callback);
}
