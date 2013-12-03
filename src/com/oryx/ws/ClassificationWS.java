package com.oryx.ws;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.oryx.handlers.URLItem;
import com.oryx.utils.Utils;
import com.oryx.xml.XMLReader;

public class ClassificationWS extends WSAbstract {

	private XMLReader xmlReader = null;
	
	public ClassificationWS() {
		super.setWSUrl(Utils.classificationWS_URL);
		xmlReader = new XMLReader();
	}
	
	public ArrayList<URLItem> getUrlsForBlogs(String tag){
		String method = "detaillist/Blogs_Technology.xml";
		super.getDoc(method);
		return xmlReader.getUrlsForClassifcation(super.webData);
	}
	
	
	
	/***
	 * Following are the method for Subscription  get, add, delete
	 */
	public ArrayList<String> getSubscriptions(String appid){
		String method = "sub?method=1&appid="+appid;
		super.getDoc(method);
		return xmlReader.getUrlsForSubscription(super.webData);
	}
	
	public ArrayList<String> addSubscriptions(String appid, String url){
		String method = "sub?method=2&appid="+appid+"&url="+url;
		super.getDoc(method);
		return xmlReader.getUrlsForSubscription(super.webData);
	}
	
	public ArrayList<String> deleteSubscriptions(String appid, String url){
		String method = "sub?method=3&appid="+appid+"&url="+url;
		super.getDoc(method);
		return xmlReader.getUrlsForSubscription(super.webData);
	}
	
	
}
