package com.oryx.handlers;

public class SearchItem {
	
	private String title;
	private String description;
	private String url;

	public SearchItem(String tit,String desc,String u) {
		title = tit;
		description = desc;
		url = u;
		
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}
	
	public String getdescription() {
		return description;
	}

}
