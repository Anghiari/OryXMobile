package com.oryx.home;

import com.oryx.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends Activity {


	private int[] catIds = new int[] { R.id.blogBtn, R.id.newsBtn, R.id.orgBtn,
			R.id.wikiBtn, R.id.forumBtn, R.id.financialBtn };
	private int SELECTED_TYPE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		catClickEvents();
		init();
	}

	private void catClickEvents() {

		for (int i = 0; i < catIds.length; i++) {
			
			LinearLayout blogBtn = (LinearLayout) findViewById(catIds[i]);
			blogBtn.setOnClickListener(new catOnClickLister(i));
		}
		
		for (int i = 0; i < Utils.subcatBtnIds.length; i++) {
			
			LinearLayout subBtn = (LinearLayout) findViewById(Utils.subcatBtnIds[i]);
			final Intent in = new Intent(HomeActivity.this,URLActivity.class);
			subBtn.setOnClickListener(new subCatOnClickLister(i,in));
		}
	}
	
	private void init(){

		
		LinearLayout userBtn = (LinearLayout) findViewById(R.id.userBtn);
		final Intent inn = new Intent(HomeActivity.this,UserActivity.class);
		userBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(inn);
			}
		});
		
		LinearLayout searchBtn = (LinearLayout) findViewById(R.id.searchBtn);
		final Intent searchin = new Intent(HomeActivity.this,SearchActivity.class);
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(searchin);
			}
		});
	}


	private void setCatName(String name) {
		TextView catName = (TextView) findViewById(R.id.catsublistname);
		catName.setText(name);
	}
	
	private void setCatLayout(int color) {
		LinearLayout catlay = (LinearLayout) HomeActivity.this.findViewById(R.id.catsublistLayout);
		catlay.setBackgroundColor(getResources().getColor(color));
	}
	
	
	class catOnClickLister implements OnClickListener{

		private int count = 0;
		public catOnClickLister(int i) {
			count = i;
		}
		
		@Override
		public void onClick(View v) {
			SELECTED_TYPE = count;
			setCatName(Utils.catNames[count]);
			setCatLayout(Utils.catColours[count]);
		}
		
	}
	
	class subCatOnClickLister implements OnClickListener{

		private int count = 0;
		private Intent inn = null;
		public subCatOnClickLister(int i, Intent in) {
			count = i;
			inn = in;
		}
		
		@Override
		public void onClick(View v) {
			inn.putExtra(Utils.EXTRA_TYPE,SELECTED_TYPE);
			inn.putExtra(Utils.EXTRA_TAG,Utils.subCatNames[count]);
			startActivity(inn);
		}
		
	}

}
