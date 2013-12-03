package com.oryx.handlers;

public class URLItem {

	private String title = "";
	private String url = "";
	private String favLocal = "";
	private String favUrl = "";
	private int type = 0;
	private String tag = "";
	
	public URLItem(String tile, String url, String local, String fav) {
		this.title = tile.trim();
		this.url = url.trim();
		this.favLocal = local.trim();
		this.favUrl = fav.trim();
	}
	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getFavLocal() {
		return favLocal;
	}

	public String getFavUrl() {
		return favUrl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	

}
