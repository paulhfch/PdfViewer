#PDF Viewer GWT Module
Inherit this module in GWT to have PDF files stored on the server rendered into png images, transferred to the clients by GWT RPC in the format of Base64 Strings and displayed in the browser. Navigation controls are provided for going to the next/previous pages, navigating to a specific page in the PDF file, enlarging the page images and shrinking the pages to the original size. 

#Build 
    cd {path/to/PdfViewer/dir}  
    ant clean  
    ant  

#PDF Viewer Demo
PdfViewerDemo module is runnable in GWT devMode.

Build project first.

In Eclipse IDE: 
Right click PdfViewerDemo.gwt.xml -> run as -> Web application

#Quick Start
Add the pdfViewer.jar file in {path/to/PdfViewer/dir}/build directory to the Java build path.

Create GWT Web Application Project.

Drop PDFRenderer-0.9.1.jar in the {path/to/war/dir/WEB-INF/lib}. You may download it [here](https://github.com/paulhfch/PdfViewer/blob/master/war/WEB-INF/lib/PDFRenderer-0.9.1.jar?raw=true).

In {project}.gwt.xml file, inherit module

	<inherits name='com.version80.gwt.pdfViewer.PdfViewer'/>

In web.xml file, add the following lines:

    <servlet>
      <servlet-name>pdfServlet</servlet-name>
      <servlet-class>com.version80.gwt.pdfViewer.server.servlet.PdfServlet</servlet-class>
    </servlet>
    <servlet-mapping>
       <servlet-name>pdfServlet</servlet-name>
       <url-pattern>/pdfViewer/PdfService</url-pattern>
    </servlet-mapping>


To instantiate a PdfViewer object and load up a PDF file stored on server,

	PdfViewer viewer = new PdfViewer();

	RootPanel.get().add(viewer);

	viewer.loadPdfFile(FILEPATH);
