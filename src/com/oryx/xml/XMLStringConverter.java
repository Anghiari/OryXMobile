package com.oryx.xml;

public class XMLStringConverter {
	
	public org.w3c.dom.Document loadXMLFrom(String xml)
		    throws org.xml.sax.SAXException, java.io.IOException {
		    return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()));
		}

		public org.w3c.dom.Document loadXMLFrom(java.io.InputStream is) 
		    throws org.xml.sax.SAXException, java.io.IOException {
		    javax.xml.parsers.DocumentBuilderFactory factory =
		        javax.xml.parsers.DocumentBuilderFactory.newInstance();
		    factory.setNamespaceAware(true);
		    javax.xml.parsers.DocumentBuilder builder = null;
		    try {
		        builder = factory.newDocumentBuilder();
		    }
		    catch (javax.xml.parsers.ParserConfigurationException ex) {
		    }  
		    org.w3c.dom.Document doc = builder.parse(is);
		    is.close();
		    return doc;
		}

}
