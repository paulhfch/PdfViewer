package com.version80.gwt.pdfViewer.server.servlet.serviceImpl;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.version80.gwt.pdfViewer.shared.PdfPage;

public class PdfRendererHelper {
	
	/**
	 * Retrieves the PDF page from the PDF file and renders it into Base64 string, and 
	 * encapsulate the image in the DTO PdfPage
	 * 
	 * @param pdfFilePath file path to the PDF file
	 * @param pageNumber page number of the PDF file to render
	 * @return PdfPage which contains the PNG image and other meta data
	 * @throws Exception
	 */
	/*package*/ static PdfPage getPageInPDF(String pdfFilePath, int pageNumber) throws Exception{
		PdfPage page = new PdfPage();
		
		PDFFile pdfFile = getPdfFile(pdfFilePath);
		String pageImage = getPdfPageInBase64String(pdfFile, pageNumber);
		
		page.setPdfTitle(pdfFilePath);
		page.setNumberOfPages(pdfFile.getNumPages());
		page.setCurrentPageNumber(pageNumber);
		page.setPageImage(pageImage);
		setPdfPageToOriginalSize(page, pdfFile, pageNumber);
		
		return page;
	}
	
	private static PDFFile getPdfFile(String filepath) throws Exception{
		RandomAccessFile raf = new RandomAccessFile(new File(filepath), "r");
	    FileChannel fc = raf.getChannel ();
	    ByteBuffer buf = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size ());
	    PDFFile pdfFile = new PDFFile (buf);
		
		return pdfFile;
	}
	
	/**
	 * outputs Base64 string of the pdf page image to its original size
	 * 
	 * <p> Note: pdf file page number starts at 1 <p>
	 * 
	 * @param pdfFile the instance of the PDF file
	 * @param pageNumber page number of the PDF file to render
	 * @return Base64 encoded string of the PNG image
	 * @throws IOException 
	 */
	/*package*/ static String getPdfPageInBase64String(PDFFile pdfFile, int pageNumber) throws Exception{		
		if(pageNumber > pdfFile.getNumPages()){
			return null;
		}
		
	    PDFPage page = pdfFile.getPage(pageNumber);
	    
	    Rectangle2D r2d = page.getBBox();
	    double width = r2d.getWidth();
	    double height = r2d.getHeight();
	      
	    Image image = page.getImage((int) width, (int) height, r2d, null, true, true);
	   
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write((BufferedImage)image, "png", baos );
	    byte[] imageInByte = baos.toByteArray();

	    return byteArrayToBase64String(imageInByte);
	}
	
	private static void setPdfPageToOriginalSize(PdfPage pdfPage, PDFFile pdfFile, int pageNumber) throws Exception{
		if(pageNumber > pdfFile.getNumPages()){
			throw new Exception("page number doesn't exist.");
		}
		
	    PDFPage page = pdfFile.getPage(pageNumber);
	    
	    Rectangle2D r2d = page.getBBox();
	    pdfPage.setWidth(r2d.getWidth());
	    pdfPage.setHeight(r2d.getHeight());
	}
	
	private static String byteArrayToBase64String(byte[] byteArray){
		String base64String = null;
		
		if(byteArray != null){
			sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder(); 
        	String base64Contents = enc.encode(byteArray).replaceAll("\\s+", ""); 
        	base64String = "data:image/png;base64," + base64Contents; 
		}
		
		return base64String;
	}
}
