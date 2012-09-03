#PDF Viewer GWT Module
Inherit this module in GWT to have PDF files stored on the server rendered into png images, transferred to the clients by GWT RPC in the format of Base64 Strings and displayed in the browser. Navigation controls are provided for going to the next/previous pages, going to a specific page in the PDF file, enlarging the page images and shrinking the pages to the original size. 

#Quick Start
Add PdfViewer source code to the dependent Projects in the build path.
Or build PdfViewer.jar from source and add to Libraries in the build path.

In {project}.gwt.xml file, inherit module.

	<inherits name='com.version80.gwt.pdfViewer.PdfViewer'/>

Create GWT Web Application Project.

In entry point class, onModuleLoad() method,

	PdfViewer viewer = new PdfViewer();

	RootPanel.get().add(viewer);

	viewer.loadPdfFile(FILEPATH);
