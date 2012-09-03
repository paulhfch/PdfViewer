package com.version80.gwt.pdfViewer.shared;

import java.io.Serializable;

public class PdfPage implements Serializable{

	private static final long serialVersionUID = 5060404977843192035L;
	
	public PdfPage(){
	}
	
	/**
	 * file info
	 */
	private String pdfTitle;
	private int numberOfPages;
	
	/**
	 * page info
	 */
	private int currentPageNumber;
	private String pageImage;
	private double width;
	private double height;
	
	public String getPdfTitle() {
		return pdfTitle;
	}
	public void setPdfTitle(String pdfTitle) {
		this.pdfTitle = pdfTitle;
	}
	public int getNumberOfPages() {
		return numberOfPages;
	}
	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	public String getPageImage() {
		return pageImage;
	}
	public void setPageImage(String pageImage) {
		this.pageImage = pageImage;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
}
