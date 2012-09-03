package com.version80.gwt.pdfViewer.server;


public class ServerUtil {
	
	/**
	 * converts byte array to base64 string for the image retrieval from DB blobs
	 * @param byteArray
	 * @return
	 */
	public static String byteArrayToBase64String(byte[] byteArray){
		String base64String = null;
		
		if(byteArray != null){
			sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder(); 
        	String base64Contents = enc.encode(byteArray).replaceAll("\\s+", ""); 
        	base64String = "data:image/png;base64," + base64Contents; 
		}
		
		return base64String;
	}
}
