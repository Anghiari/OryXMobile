package com.oryx.ws;

import java.util.ArrayList;
import java.util.Map;
import org.w3c.dom.Document;
import android.util.Log;
import com.oryx.handlers.RSSItem;
import com.oryx.handlers.SessionHandler;
import com.oryx.utils.Utils;
import com.oryx.xml.XMLReader;

public class TransformationWS extends WSAbstract {

	private XMLReader xmlReader = null;

	public TransformationWS() {
		super.setWSUrl(Utils.transformationWS_URL);
		xmlReader = new XMLReader();
	}

	public Map<String, String> getWebpageInfo(String url) {

		url = Utils.formatURL(url);
		String method = "api/main?url=" + url;
		super.getDoc(method);
		return xmlReader.getWebpageInfo(super.webData);
	}
	
	public String getWSContentURL(String url){
		url = Utils.formatURL(url);
		String method = "api/content?url="+url;		
		return super.webURL+method;
	}
	
	public ArrayList<RSSItem> getFeedsList(String url){
		url = Utils.formatURL(url);
		String method = "api/nav?url=" + url;
		Log.d("pahan", webURL+method);
		super.getDoc(method);
		return xmlReader.getFeedsList(super.webData);
	}
	

//	public ArrayList<ContentItem> getContent(String url) {
//		
//		url = Utils.formatURL(url);
//		String method = "api/content?url="+url;	
//		super.getDoc(method);
//		Log.d("chinthaka TransformationWS 60", method);
//		
//		try {
//			return xmlReader.getContentItemList(this.webData);
//		} catch (Exception e) {
//			Log.d("error", e.getMessage());
//			return null;
//		}
//
//	}
	
	/***
	 * Getting the voice string from the string
	 * @param url
	 * @return
	 */
	public String getVoiceString(String url){
		
		url = Utils.formatURL(url);

		String method = "api/voice?url="+url;	
		super.getDoc(method);
		
		try {
			String text=xmlReader.getVoiceFromXML(this.webData);
			return text;
			
		} catch (Exception e) {
			//Log.d("error", e.getMessage());
			return "EMPTY";
		}
	}

}
