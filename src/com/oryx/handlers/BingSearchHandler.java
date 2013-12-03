package com.oryx.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import android.util.Log;
import com.oryx.xml.XMLReader;
import com.oryx.xml.XMLStringConverter;

public class BingSearchHandler {

		
	private static BingSearchHandler bsh = null;
	private XMLReader xmlReader;
	private XMLStringConverter docReader;

	private BingSearchHandler() {
		xmlReader = new XMLReader();
		docReader = new XMLStringConverter();
	}

	public static BingSearchHandler getHandler() {
		if (bsh == null) {
			return new BingSearchHandler();
		} else {
			return bsh;
		}
	}
	 public ArrayList<SearchItem> getQueryList(String query, int count){
		 	
		 	
		 	String bingUrl = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%27" + java.net.URLEncoder.encode(query) + "%27&$format=atom&$top="+count;
		    
		    byte[] accountKeyBytes =Base64.encodeBase64(("bc09fae1-dc44-4475-9583-205a0b17288b" + ":" + "ZZu1pQDzQgjlcNgCnh+eBssiHQ3VxkopRlZJEZDXHk0").getBytes()); // code for encoding found on stackoverflow
		    String accountKeyEnc = new String(accountKeyBytes);
		    
		    ArrayList<SearchItem> list = null;

		    URL url;
		    
			try {
				url = new URL(bingUrl);

			    URLConnection urlConnection = url.openConnection();
			    String s1 = "Basic " + accountKeyEnc;
			    
			    urlConnection.setRequestProperty("Authorization", s1);
			    
			    BufferedReader in = new BufferedReader(new InputStreamReader(
			        urlConnection.getInputStream()));
			    String inputLine;
			    StringBuffer sb = new StringBuffer();
			    while ((inputLine = in.readLine()) != null)
			      sb.append(inputLine);
			    in.close();

			    Document doc = docReader.loadXMLFrom(sb.toString());

			    list = xmlReader.getSearchResult(doc);
			    
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
		    return list;
		  }

}
