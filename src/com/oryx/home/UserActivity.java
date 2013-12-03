package com.oryx.home;

import java.util.ArrayList;

import com.oryx.db.DBHandler;
import com.oryx.handlers.URLItem;
import com.oryx.handlers.UserSubItem;
import com.oryx.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends Activity {

	private GridView gridView;
	private ArrayList<UserSubItem> subcriptions;
	private LinearLayout emptyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user);
		init();
		initGrid();
	}

	private void init() {
		subcriptions = DBHandler.getHandler(UserActivity.this).getSubcriptions();
		
		
		LinearLayout homeBtn = (LinearLayout)findViewById(R.id.userActHome);
		final Intent homein = new Intent(UserActivity.this,HomeActivity.class);
		homeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(homein);
			}
		});
		
		ImageView searchBtn = (ImageView)findViewById(R.id.userSearchBtn);
		final Intent searchIn = new Intent(UserActivity.this,SearchActivity.class);
		searchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(searchIn);
			}
		});
	}
	
	private void initGrid(){

		gridView = (GridView) findViewById(R.id.subGrid);
		emptyView = (LinearLayout)findViewById(R.id.emptyView);
		gridView.setAdapter(new GridAdapter(this, subcriptions));
		gridView.setEmptyView(emptyView);
	}

	class GridAdapter extends BaseAdapter {
		private Context context;
		private final ArrayList<UserSubItem> values;

		public GridAdapter(Context contexts, ArrayList<UserSubItem> valuess) {
			this.context = contexts;
			this.values = valuess;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View gridViewItem;

			if (convertView == null) {

				gridViewItem = new View(context);

	
				gridViewItem = inflater.inflate(R.layout.grid_item_custom, null);

				final UserSubItem item = values.get(position);
				
				TextView textView = (TextView) gridViewItem
						.findViewById(R.id.Sitename);
				textView.setText(item.getName());
				textView.setTextColor(getResources().getColor(item.getColour()));
				
				TextView urll = (TextView) gridViewItem
						.findViewById(R.id.Siteurl);
				urll.setText(item.getUrl());

				TextView count = (TextView) gridViewItem
						.findViewById(R.id.notiCount);
				count.setVisibility(View.GONE);
				
				ImageView imageView = (ImageView) gridViewItem
						.findViewById(R.id.siteImg);
				imageView.setImageResource(item.getImg());
				
				LinearLayout colorPad = (LinearLayout)gridViewItem.findViewById(R.id.colourPad);
				colorPad.setBackgroundColor(getResources().getColor(item.getColour()));
				
				
				final Intent webIn = new Intent(UserActivity.this,
						WebViewActivity.class);
				
				gridViewItem.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						webIn.putExtra(Utils.EXTRA_URL, item.getUrl());
						startActivity(webIn);

					}
				});
				
				gridViewItem.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View arg0) {
						showConfirmationDialog(item,position);
						return true;
					}
				});



			} else {
				gridViewItem = (View) convertView;
			}

			return gridViewItem;
		}
		
		@Override
		public int getCount() {
			return values.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		class DialogClickListener implements DialogInterface.OnClickListener{
			private UserSubItem item = null;
			private int id = -1;
			public DialogClickListener(UserSubItem item,int id) {
				this.item = item;
				this.id = id;
			}
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            boolean res = DBHandler.getHandler(UserActivity.this).deleteSubcription(item.getUrl());
		            if(res){
		            	Toast.makeText(UserActivity.this, "Successfully UnSubcribed...!!", Toast.LENGTH_SHORT).show();
		            	subcriptions = DBHandler.getHandler(UserActivity.this).getSubcriptions();
		        		gridView.setAdapter(new GridAdapter(UserActivity.this, subcriptions));
		            }
		            else{
		            	Toast.makeText(UserActivity.this, "Error occured...!!", Toast.LENGTH_SHORT).show();
		            }
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		            dialog.dismiss();
		            break;
		        }
				
			}
			
		}
		
		
		private void showConfirmationDialog(UserSubItem itm,int id){
			AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
			DialogClickListener dialogClickListener = new DialogClickListener(itm,id);
			builder
				.setMessage("Are you sure you want to unsubscribe to "+itm.getName())
				.setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener)
			    .show();
		}

	}
	
	

}
