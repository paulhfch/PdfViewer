package com.version80.gwt.pdfViewer.client;

public class PdfViewerContext {
	private PdfSource source;
	private String currentPdfFilePath = null;
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
	
	private PdfViewerContext(Double width, Double height, PdfSource source){
		this.pdfPageMaxWidth = width;
		this.pdfPageMaxHeight = height;
		
		this.setSource(source);
	}

	/**
	 * constructs pdf viewer context with the maximum width and maximum height
	 * to enable scrolling
	 * 
	 * @param width maximum width in px
	 * @param height maximum height in px
	 */
	public static PdfViewerContext createPdfViewerContext(Double width, Double height, PdfSource source){
		return new PdfViewerContext(width, height, source);
	}

	public String getCurrentPdfFilePath() {
		return currentPdfFilePath;
	}

	/*packages*/ void setCurrentPdfFilePath(String currentPdfFilePath) {
		this.currentPdfFilePath = currentPdfFilePath;
	}

	public String getCurrentPdfImage() {
		return currentPdfImage;
	}

	/*packages*/ void setCurrentPdfImage(String currentPdfImage) {
		this.currentPdfImage = currentPdfImage;
	}

	public int getCurrentPdfPageNumber() {
		return currentPdfPageNumber;
	}

	/*packages*/ void setCurrentPdfPageNumber(int currentPdfPageNumber) {
		this.currentPdfPageNumber = currentPdfPageNumber;
	}

	public int getNumberOfPdfPages() {
		return numberOfPdfPages;
	}

	/*packages*/ void setNumberOfPdfPages(int numberOfPdfPages) {
		this.numberOfPdfPages = numberOfPdfPages;
	}

	public double getPdfPageWidth() {
		return pdfPageWidth;
	}

	/*packages*/ void setPdfPageWidth(double pdfPageWidth) {
		this.pdfPageWidth = pdfPageWidth;
	}

	public double getPdfPageHeight() {
		return pdfPageHeight;
	}

	/*packages*/ void setPdfPageHeight(double pdfPageHeight) {
		this.pdfPageHeight = pdfPageHeight;
	}

	public double getPdfEnlargeRate() {
		return pdfEnlargeRate;
	}

	/*packages*/  void setPdfEnlargeRate(double pdfEnlargeRate) {
		this.pdfEnlargeRate = pdfEnlargeRate;
	}

	public double getZoomInRate() {
		return ZoomInRate;
	}

	/*packages*/  void setZoomInRate(double zoomInRate) {
		ZoomInRate = zoomInRate;
	}

	public  double getPdfHeightToWidthRatio() {
		return pdfHeightToWidthRatio;
	}

	/*packages*/  void setPdfHeightToWidthRatio(double pdfHeightToWidthRatio) {
		this.pdfHeightToWidthRatio = pdfHeightToWidthRatio;
	}

	public Double getPdfPageMaxWidth() {
		return pdfPageMaxWidth;
	}

	/*packages*/  void setPdfPageMaxWidth(double pdfPageMaxWidth) {
		this.pdfPageMaxWidth = pdfPageMaxWidth;
	}

	public Double getPdfPageMaxHeight() {
		return pdfPageMaxHeight;
	}

	/*packages*/  void setPdfPageMaxHeight(double pdfPageMaxHeight) {
		this.pdfPageMaxHeight = pdfPageMaxHeight;
	}

	public PdfSource getSource() {
		return source;
	}
	
	private void setSource(PdfSource source) {
		this.source = source;
	}
}
