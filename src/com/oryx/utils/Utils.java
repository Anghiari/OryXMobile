package com.oryx.utils;

import com.oryx.home.R;

public class Utils {

	public static String typeface = "fonts/roboto.ttf";
	public static String transformationWS_URL = "http://oryxtransformationengine.herokuapp.com/";
	public static String classificationWS_URL = "http://oryxwebclassification.herokuapp.com/";
	
	public static String app_id = "qazwsxedcr";
	public static String jsEnginePath = "file:///android_asset/app/index.html";
	
	public static String favPath = "http://oryxwebclassification.herokuapp.com/detaillist/";
	public static int REQUEST_CODE = 1234;
	
	public static String EXTRA_URL = "url";
	public static String EXTRA_TYPE = "type";
	public static String EXTRA_TAG = "tag";
	
	
	public static String[] catNames = new String[] { "Blogs", "News",
			"Organizations", "Wikis", "Forums", "Financial" };
	
	public static int[] catColours = new int[] { R.color.blue,R.color.red,R.color.yellow,R.color.green,R.color.blue,R.color.red};
	public static int[] catImages = new int[] { R.drawable.blog, R.drawable.news, R.drawable.org, R.drawable.wiki, R.drawable.forum, R.drawable.finalancial};
	public static int[] catImageswhite = new int[] { R.drawable.blog_white, R.drawable.news_white, R.drawable.org_white, R.drawable.wiki_white, R.drawable.forum_white, R.drawable.finalancial_white};
	
	public static int[] subcatBtnIds = new int[] { R.id.techBlogBtn, R.id.sportsBlogBtn, R.id.travelBlogBtn,
			R.id.fashionBlogBtn, R.id.businessBlogBtn, R.id.educationalBlogsBtn };
	public static String[] subCatNames = new String[] { "TECHNOLOGY", "SPORTS",
		"TRAVEL", "FASHION", "BUSINESS", "EDUCATIONAL" };
	
	public static String[] filesForSuggesstions = new String[]{"Blogs_TECHNOLOGY","Blogs_SPORTS","Blogs_TRAVEL","Blogs_FASHION","Blogs_BUSINESS","Blogs_EDUCATIONAL"}; 

	
	public static String formatURL(String url){
		
		return  url.replace("http://", "");
	}
	
	
	
	public static String DEFUALT_CAT = "Blogs";
}
