package com.version80.gwt.pdfViewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class PdfViewerDemo implements EntryPoint{
	public static final String TEST_PDF = "test.pdf";

	@Override
	public void onModuleLoad() {	
		
		PdfViewerContext context = new PdfViewerContext(600d, 500d);
		PdfViewer viewer = new PdfViewer(context);
		viewer.loadPdfFile( TEST_PDF, false );
		
		RootPanel.get().add(viewer);
	}

}
