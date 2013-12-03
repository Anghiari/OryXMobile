package com.oryx.handlers;

import java.util.ArrayList;
import java.util.Map;
import org.w3c.dom.Document;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.oryx.home.URLActivity;
import com.oryx.home.WebViewActivity;
import com.oryx.utils.Utils;
import com.oryx.utils.WSConstants;
import com.oryx.ws.ClassificationWS;
import com.oryx.ws.TransformationWS;
import com.oryx.xml.XMLReader;

public class DataHandler {

	private ClassificationWS cWS = null;
	private TransformationWS tWS = null;
	private ProgressDialog loadingIcon;
	private static DataHandler dh = null;

	private DataHandler() {
		cWS = new ClassificationWS();
		tWS = new TransformationWS();
	}

	public static DataHandler getHandler() {
		if (dh == null) {
			return new DataHandler();
		} else {
			return dh;
		}
	}

	public ArrayList<URLItem> getUrls(String type, String tag) {

		if (type.equalsIgnoreCase("Blogs")) {
			return cWS.getUrlsForBlogs(tag);
		} else {
			return null;
		}
	}

	public ArrayList<URLItem> getUrls(Context c,String filename) {

		XMLReader xmlReader = new XMLReader();
		return xmlReader.getUrlsForClassifcation(xmlReader
				.getXMLDocumentFromAssets(c,filename));
	}
	
	public String[] getSugesstions(Context c){
		
			
		ArrayList<URLItem> sugestionList = new ArrayList<URLItem>();
		
		for(int i = 0;i<Utils.filesForSuggesstions.length;i++){
			ArrayList<URLItem> temp = getUrls(c, Utils.filesForSuggesstions[i]);
			for(int j = 0;j<temp.size();j++){
				sugestionList.add(temp.get(j));
			}
		}
		
		String[] suggestionArry = new String[sugestionList.size()];
		
		for(int cc = 0;cc<sugestionList.size();cc++){
			String s = sugestionList.get(cc).getUrl();
			suggestionArry[cc] = Utils.formatURL(s);
		}
		
		return suggestionArry;
	}


	public Map<String, String> getWebpageInfo(String url) {

		Map<String, String> info = tWS.getWebpageInfo(url);
		return info;

	}
	
	public String getWSURL(String url){
		return tWS.getWSContentURL(url);
	}

	public ArrayList<RSSItem> getFeedList(String url) {
		return tWS.getFeedsList(url);
	}

	
	public String getVoiceContentFromService(String url){
		
		return tWS.getVoiceString(url);
	}


	
	public ArrayList<String> getServerSubscriptionURLList() {

		String appid = Utils.app_id;
		return cWS.getSubscriptions(appid);
	}
	
	public ArrayList<String> AddToServerSubscriptionList(String url) {

		String appid = Utils.app_id;
		return cWS.addSubscriptions(appid, url);
	}
	
	public ArrayList<String> deleteServerSubscriptionList(String url) {

		String appid = Utils.app_id;
		return cWS.deleteSubscriptions(appid, url);
	}


}
