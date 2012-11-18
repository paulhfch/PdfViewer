package com.version80.gwt.pdfViewer.client;

public class PdfViewerContext {
	private String currentPdfFilePath = null;
	private boolean isAbsolutePath = false;
	private String currentPdfImage = null;
	private int currentPdfPageNumber = -1;
	private int numberOfPdfPages = -1;
	private double pdfPageWidth = 500;
	private double pdfPageHeight = 500;
	
	private Double pdfPageMaxWidth = null;
	private Double pdfPageMaxHeight = null;
	private double pdfEnlargeRate = 1;
	private double ZoomInRate = 0.2;
	private double pdfHeightToWidthRatio = -1;
	
	public PdfViewerContext(){
		//blank
	}
	
	/**
	 * constructs pdf viewer context with the maximum width and maximum height
	 * to enable scrolling
	 * 
	 * @param width maximum width in px
	 * @param height maximum height in px
	 */
	public PdfViewerContext( Double width, Double height ){
		pdfPageMaxWidth = width;
		pdfPageMaxHeight = height;
	}

	public String getCurrentPdfFilePath() {
		return currentPdfFilePath;
	}

	void setCurrentPdfFilePath(String currentPdfFilePath) {
		this.currentPdfFilePath = currentPdfFilePath;
	}

	public boolean isAbsolutePath() {
		return isAbsolutePath;
	}

	void setAbsolutePath(boolean isAbsolutePath) {
		this.isAbsolutePath = isAbsolutePath;
	}

	public String getCurrentPdfImage() {
		return currentPdfImage;
	}

	void setCurrentPdfImage(String currentPdfImage) {
		this.currentPdfImage = currentPdfImage;
	}

	public int getCurrentPdfPageNumber() {
		return currentPdfPageNumber;
	}

	void setCurrentPdfPageNumber(int currentPdfPageNumber) {
		this.currentPdfPageNumber = currentPdfPageNumber;
	}

	public int getNumberOfPdfPages() {
		return numberOfPdfPages;
	}

	void setNumberOfPdfPages(int numberOfPdfPages) {
		this.numberOfPdfPages = numberOfPdfPages;
	}

	public double getPdfPageWidth() {
		return pdfPageWidth;
	}

	void setPdfPageWidth(double pdfPageWidth) {
		this.pdfPageWidth = pdfPageWidth;
	}

	public double getPdfPageHeight() {
		return pdfPageHeight;
	}

	void setPdfPageHeight(double pdfPageHeight) {
		this.pdfPageHeight = pdfPageHeight;
	}

	public double getPdfEnlargeRate() {
		return pdfEnlargeRate;
	}

	public void setPdfEnlargeRate(double pdfEnlargeRate) {
		this.pdfEnlargeRate = pdfEnlargeRate;
	}

	public double getZoomInRate() {
		return ZoomInRate;
	}

	public void setZoomInRate(double zoomInRate) {
		ZoomInRate = zoomInRate;
	}

	public double getPdfHeightToWidthRatio() {
		return pdfHeightToWidthRatio;
	}

	public void setPdfHeightToWidthRatio(double pdfHeightToWidthRatio) {
		this.pdfHeightToWidthRatio = pdfHeightToWidthRatio;
	}

	public Double getPdfPageMaxWidth() {
		return pdfPageMaxWidth;
	}

	public void setPdfPageMaxWidth(double pdfPageMaxWidth) {
		this.pdfPageMaxWidth = pdfPageMaxWidth;
	}

	public Double getPdfPageMaxHeight() {
		return pdfPageMaxHeight;
	}

	public void setPdfPageMaxHeight(double pdfPageMaxHeight) {
		this.pdfPageMaxHeight = pdfPageMaxHeight;
	}

}
