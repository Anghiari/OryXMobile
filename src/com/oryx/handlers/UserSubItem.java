package com.oryx.handlers;

import com.oryx.utils.Utils;

public class UserSubItem {

	private String name = "";
	private String url = "";
	private int type = 0;
	private int colour = 0;
	private String tag = "";
	private int img = 0;
	
	public UserSubItem(String name,String url,int type,String tag) {

		this.name = name;
		this.url = url;
		this.type = type;
		this.tag = tag;
		
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public int getType() {
		return type;
	}
	public int getColour() {
		return Utils.catColours[type];
	}
	public int getImg() {
		return Utils.catImageswhite[type];
	}
	
	
}
