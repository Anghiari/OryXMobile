package com.oryx.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.oryx.handlers.SearchItem;
import com.oryx.handlers.RSSItem;
import com.oryx.handlers.URLItem;

public class XMLReader {

	private Document doc;

	public ArrayList<URLItem> getUrlsForClassifcation(Document doc) {

		ArrayList<URLItem> list = new ArrayList<URLItem>();

		if (doc != null) {

			NodeList nList = doc.getElementsByTagName("url");

			for (int i = 0; i < nList.getLength(); i++) {
				Node url = nList.item(i);
				NodeList nodeEle = url.getChildNodes();
				Node title = nodeEle.item(1);
				Node urllink = nodeEle.item(3);
				Node favLocal = nodeEle.item(5);
				list.add(new URLItem(title.getTextContent(), urllink
						.getTextContent(), "", ""));

			}
		}

		return list;
	}

	public Map<String, String> getWebpageInfo(Document doc) {

		Map<String, String> info = new HashMap<String, String>();
		try {
			if (doc != null) {
				NodeList nList = doc.getElementsByTagName("oryx");

				NodeList infolist = nList.item(0).getChildNodes();

				for (int i = 0; i < infolist.getLength(); i++) {
					Node in = infolist.item(i);
					info.put(in.getNodeName(), in.getTextContent());

				}
			}
		} catch (Exception e) {

		}

		return info;
	}

	public ArrayList<RSSItem> getFeedsList(Document doc) {

		ArrayList<RSSItem> list = new ArrayList<RSSItem>();

		if (doc != null) {
			NodeList nList = doc.getElementsByTagName("item");

			for (int i = 0; i < nList.getLength(); i++) {
				Node name = nList.item(i);
				NodeList nodeEle = name.getChildNodes();
				Node nameN = nodeEle.item(0);
				Node urlN = nodeEle.item(1);
				list.add(new RSSItem(nameN.getTextContent(), urlN
						.getTextContent()));

			}
		}
		return list;

	}



	/****
	 * This method is written to read the Local XML file. Need to make the URL
	 * parameter and generalized for more use cases.
	 * 
	 * @param c
	 * @return
	 */
	public Document getXMLDocumentFromAssets(Context c,String filename) {
		if (doc == null) {

			AssetManager am = c.getAssets();
			try {

				InputStream inputStream = am.open(filename+".xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = null;
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(inputStream);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return doc;
	}

	/**
	 * This method is used to ge tthe URLList for Subscription
	 * 
	 * @param doc
	 * @return
	 */
	public ArrayList<String> getUrlsForSubscription(Document doc) {

		ArrayList<String> list = new ArrayList<String>();

		if (doc != null) {

			NodeList nList = doc.getElementsByTagName("url");

			for (int i = 0; i < nList.getLength(); i++) {
				Node url = nList.item(i);
				list.add(url.getTextContent());
			}
		}

		return list;
	}
	
	public ArrayList<SearchItem> getSearchResult(Document doc) {

		ArrayList<SearchItem> list = new ArrayList<SearchItem>();

		if (doc != null) {
			
			NodeList nList = doc.getElementsByTagName("m:properties");
				
			for (int i = 0; i < nList.getLength(); i++) {
				Node name = nList.item(i);
				NodeList nodeEle = name.getChildNodes();
				Node titleNode = nodeEle.item(1);
				Node descNode = nodeEle.item(2);
				Node urlNode = nodeEle.item(3);
				list.add(new SearchItem(titleNode.getTextContent(), descNode.getTextContent(), urlNode.getTextContent()));

			}
			
	
		}
		return list;	

	}
	

	public String getVoiceFromXML(Document doc) {

		NodeList nl = doc.getElementsByTagName("voice");

		return nl.item(0).getTextContent();
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

}
