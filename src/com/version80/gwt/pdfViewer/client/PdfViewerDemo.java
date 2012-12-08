package com.version80.gwt.pdfViewer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class PdfViewerDemo implements EntryPoint{
	public static final String TEST_PDF = "test.pdf";

	@Override
	public void onModuleLoad() {	
		
		PdfViewerContext context = PdfViewerContext.createPdfViewerContext(600d, 500d, PdfSource.RESOURCES_DIR);
		PdfViewer viewer = new PdfViewer(context);
		viewer.loadPdfFile( TEST_PDF );
		
		RootPanel.get().add(viewer);
	}

}
