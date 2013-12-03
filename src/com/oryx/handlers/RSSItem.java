package com.oryx.handlers;

public class RSSItem {

	private String name;
	private String url;

	public RSSItem(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
}
