package com.version80.gwt.pdfViewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class PdfViewerDemo implements EntryPoint{
	final String TEST_PDF = "test.pdf";

	@Override
	public void onModuleLoad() {		
		PdfViewer viewer = new PdfViewer();
		viewer.loadPdfFile( TEST_PDF, false );
		
		RootPanel.get().add(viewer);
	}

}
