package com.version80.gwt.pdfViewer.shared;

public class PageLoadException extends Exception{

	private static final long serialVersionUID = 7108706096842409867L;
	
	private String message = "";
	
	public PageLoadException(){
	}
	
	public PageLoadException( String message ){
		this.message = message;
	}
	
	public PageLoadException( String pdfName, int pageNumber ){
		this.message = "ERROR: [" + pdfName + "] Page " + pageNumber + " couldn't load." ;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
