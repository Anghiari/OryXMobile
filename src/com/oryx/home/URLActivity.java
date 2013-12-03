package com.oryx.home;

import java.util.ArrayList;

import com.oryx.db.DBHandler;
import com.oryx.handlers.DataHandler;
import com.oryx.handlers.URLItem;
import com.oryx.handlers.UserSubItem;
import com.oryx.utils.Utils;
import com.oryx.utils.WSConstants;

import android.R.bool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class URLActivity extends ListActivity {

	private ImageView loadingImg;
	private LinearLayout loadingLayout;
	private int SELECTED_TYPE = 0;
	private String SELECTED_TAG = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_url);
		rotateAnimation();
		init();
		new URLsLoadingTask().execute(Utils.catNames[SELECTED_TYPE]+"_"+SELECTED_TAG);

	}

	private void init() {

		LinearLayout homeBtn = (LinearLayout) findViewById(R.id.urlActHome);
		final Intent homein = new Intent(URLActivity.this, HomeActivity.class);
		homeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(homein);
			}
		});
		
		ImageView userBtn = (ImageView) findViewById(R.id.urlUserBtn);
		final Intent userin = new Intent(URLActivity.this, UserActivity.class);
		userBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(userin);
			}
		});
		
		ImageView searchBtn = (ImageView) findViewById(R.id.urlSearchBtn);
		final Intent searchin = new Intent(URLActivity.this, SearchActivity.class);
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(searchin);
			}
		});

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			SELECTED_TYPE = extras.getInt(Utils.EXTRA_TYPE);
			SELECTED_TAG = extras.getString(Utils.EXTRA_TAG);
		}

		TextView typeView = (TextView) findViewById(R.id.urltype);
		TextView tagView = (TextView) findViewById(R.id.urltag);

		typeView.setText("Popular " + Utils.catNames[SELECTED_TYPE]);
		tagView.setText(SELECTED_TAG);

		//subcriptions = DBHandler.getHandler(URLActivity.this).getSubcriptions();
	}

	private class URLsListAdapter extends ArrayAdapter {

		private Context mContext;
		private int id;
		private ArrayList<URLItem> items;
		private int[] catColours = new int[] { R.color.blue, R.color.red,
				R.color.yellow, R.color.green };

		public URLsListAdapter(Context context, int textViewResourceId,
				ArrayList<URLItem> list) {
			super(context, textViewResourceId, list);
			mContext = context;
			id = textViewResourceId;
			items = list;
		}

		@Override
		public View getView(final int position, View v, ViewGroup parent) {
			View mView = v;
			if (mView == null) {
				LayoutInflater vi = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				mView = vi.inflate(id, null);
			}

			if (items.get(position) != null) {
				TextView text = (TextView) mView.findViewById(R.id.URLname);
				text.setText(items.get(position).getTitle());

				TextView texturl = (TextView) mView.findViewById(R.id.URLurl);
				texturl.setText(items.get(position).getUrl());

				TextView catlay = (TextView) mView.findViewById(R.id.colourPad);
				catlay.setBackgroundColor(getResources().getColor(
						catColours[position % 4]));

				final ImageView subBtn = (ImageView) mView
						.findViewById(R.id.subBtn);

				subBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						URLItem subItem = items.get(position);
						subItem.setType(SELECTED_TYPE);
						subItem.setTag(SELECTED_TAG);
						int res = DBHandler.getHandler(URLActivity.this)
								.addSubcription(subItem);

						if (res == 1) {
							showToast("Successfully Subscribed...!!");
						}
						else if(res == 0){
							showToast("Already Subscribed...!!");
						}
						else{
							showToast("Error Occured...!!");
						}
					}

				});

				final Intent webIn = new Intent(URLActivity.this,
						WebViewActivity.class);
				LinearLayout itemView = (LinearLayout) mView
						.findViewById(R.id.listItemView);

				itemView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						webIn.putExtra(Utils.EXTRA_URL, items.get(position)
								.getUrl());
						startActivity(webIn);

					}
				});

			}

			return mView;
		}

		@Override
		public int getCount() {
			return items.size();
		}

	}

	public void rotateAnimation() {

		loadingImg = (ImageView) findViewById(R.id.loadingImg);
		loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setInterpolator(new LinearInterpolator());
		rotateAnimation.setDuration(1000);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setRepeatCount(Animation.INFINITE);

		loadingImg.setAnimation(rotateAnimation);
	}

	public class URLsLoadingTask extends
			AsyncTask<String, Void, ArrayList<URLItem>> {

		protected ArrayList<URLItem> doInBackground(String... params) {

			// return DataHandler.getHandler().getUrls("Blogs", "");
			return DataHandler.getHandler().getUrls(URLActivity.this, params[0]);
		}

		@Override
		protected void onPreExecute() {
			getListView().getEmptyView().setVisibility(View.INVISIBLE);
			super.onPreExecute();
		}

		protected void onPostExecute(ArrayList<URLItem> result) {
			loadingLayout.setVisibility(View.GONE);
			if (result != null) {
				setListAdapter(new URLsListAdapter(URLActivity.this,
						R.layout.list_item_custom, result));
			} else {
				getListView().getEmptyView().setVisibility(View.VISIBLE);
				setListAdapter(new URLsListAdapter(URLActivity.this,
						R.layout.list_item_custom, new ArrayList<URLItem>()));
			}

		}

	}
	
	private void showToast(String text){
		
		LayoutInflater inflater = getLayoutInflater();
	    View view = inflater.inflate(R.layout.custom_toast,
	                                   (ViewGroup) findViewById(R.id.toastView));
	    TextView tv = (TextView)view.findViewById(R.id.toastText);
	    tv.setText(text);
	    Toast toast = new Toast(this);
	    toast.setView(view);
	    toast.setDuration(Toast.LENGTH_LONG);
	    toast.show();
	}

}
