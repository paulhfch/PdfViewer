package com.version80.gwt.pdfViewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class PdfViewerDemo implements EntryPoint{
	final String TEST_PDF = "test.pdf";

	PdfViewer viewer;

	@Override
	public void onModuleLoad() {		
		viewer = new PdfViewer();
		viewer.loadPdfFile( TEST_PDF );
		
		RootPanel.get().add(viewer);
	}

}
