package com.oryx.home;

import java.util.ArrayList;
import com.oryx.handlers.BingSearchHandler;
import com.oryx.handlers.DataHandler;
import com.oryx.handlers.SearchItem;
import com.oryx.handlers.VoiceCmdHandler;
import com.oryx.utils.Utils;
import com.oryx.utils.WSConstants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends ListActivity implements TextWatcher {

	private AutoCompleteTextView searchEdit;
	private ImageView searchBtn;
	private int SEARCH_ITEM_VIEW = R.layout.searchlist_item_custom;
	private LinearLayout gotoURLView;
	private TextView gotoText;
	private LinearLayout listViewlayout;
	private ImageView voiceBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		init();
	}

	private void init() {

		gotoURLView = (LinearLayout) findViewById(R.id.gotoURLView);
		gotoText = (TextView) findViewById(R.id.gotoURLText);
		listViewlayout = (LinearLayout) findViewById(R.id.listViewLayout);

		searchEdit = (AutoCompleteTextView) findViewById(R.id.searchEdit);

		final String[] values = DataHandler.getHandler().getSugesstions(SearchActivity.this);

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		searchEdit.addTextChangedListener(this);

		searchEdit.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				gotoText.setText("http://" + searchEdit.getText().toString());
				setGotoViewVisibility(true);

			}

		});

		searchEdit.setAdapter(adapter);

		final Intent webIn = new Intent(SearchActivity.this,
				WebViewActivity.class);

		searchBtn = (ImageView) findViewById(R.id.actSearchBtn);
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				setGotoViewVisibility(false);
				new SearchItemLoadingTask().execute();
			}
		});

		voiceBtn = (ImageView) findViewById(R.id.voiceSearchBtn);
		voiceBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (VoiceCmdHandler.getHandler(SearchActivity.this)
						.checkVoiceRecognition()) {
					startActivityForResult(
							VoiceCmdHandler.getHandler(SearchActivity.this)
									.startVoiceRecognitionActivity(),
							Utils.REQUEST_CODE);
				} else {
					Toast.makeText(SearchActivity.this, "Sorry.. Not supporting Voice", Toast.LENGTH_LONG).show();
				}
			}
		});

		gotoURLView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				webIn.putExtra(Utils.EXTRA_URL, searchEdit.getText().toString());
				startActivity(webIn);

			}
		});

		LinearLayout homeBtn = (LinearLayout) findViewById(R.id.searchHome);
		final Intent homein = new Intent(SearchActivity.this,
				HomeActivity.class);
		homeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(homein);
			}
		});
		
		ImageView userBtn = (ImageView) findViewById(R.id.searchUserBtn);
		final Intent userIn = new Intent(SearchActivity.this,
				UserActivity.class);
		userBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(userIn);
			}
		});
	}

	private class SearchListAdapter extends ArrayAdapter {

		private Context mContext;
		private int id;
		private ArrayList<SearchItem> items;
		private int[] catColours = new int[] { R.color.blue, R.color.red,
				R.color.yellow, R.color.green };

		public SearchListAdapter(Context context, int textViewResourceId,
				ArrayList<SearchItem> list) {
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
				TextView text = (TextView) mView.findViewById(R.id.Searchname);
				text.setText(items.get(position).getTitle());
				text.setTextColor((getResources()
						.getColor(catColours[position % 4])));

				TextView texturl = (TextView) mView
						.findViewById(R.id.Searchurl);
				texturl.setText(items.get(position).getUrl());

				TextView destext = (TextView) mView
						.findViewById(R.id.SearchDes);
				destext.setText(items.get(position).getdescription());

				TextView catlay = (TextView) mView
						.findViewById(R.id.searchcolourPad);
				catlay.setBackgroundColor(getResources().getColor(
						catColours[position % 4]));

				final Intent webIn = new Intent(SearchActivity.this,
						WebViewActivity.class);
				mView.setOnClickListener(new OnClickListener() {

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

	public class SearchItemLoadingTask extends
			AsyncTask<Void, Void, ArrayList<SearchItem>> {

		@Override
		protected ArrayList<SearchItem> doInBackground(Void... params) {

			return BingSearchHandler.getHandler().getQueryList(
					searchEdit.getText().toString(), 5);
		}

		@Override
		protected void onPreExecute() {
			roatateSmallLoader();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<SearchItem> result) {

			stopSmallLoader();

			if (result != null) {
				setListAdapter(new SearchListAdapter(SearchActivity.this,
						SEARCH_ITEM_VIEW, result));
			} else {
			}
		}

	}

	private void roatateSmallLoader() {
		searchBtn.setImageResource(R.drawable.loading2);
		searchBtn.setBackgroundColor(Color.TRANSPARENT);
		rotateAnimation(searchBtn);

	}

	private void stopSmallLoader() {
		searchBtn.setImageResource(R.drawable.search_white);
		searchBtn.setBackgroundColor(getResources().getColor(R.color.yellow));
		searchBtn.clearAnimation();

	}

	public void rotateAnimation(View v) {

		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setInterpolator(new LinearInterpolator());
		rotateAnimation.setDuration(1000);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setRepeatCount(Animation.INFINITE);

		v.setAnimation(rotateAnimation);
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		int c = searchEdit.getAdapter().getCount();
		if (c == 0) {
			String tt = s.toString();
			if (tt.startsWith("http://")) {
				gotoText.setText(s.toString());
			} else {
				gotoText.setText("http://" + s.toString());
			}
			setGotoViewVisibility(true);
		} else {
			setGotoViewVisibility(false);
		}

		if (start == 0 && before == 1) {
			setGotoViewVisibility(false);
		}

	}

	private void setGotoViewVisibility(boolean v) {
		if (v) {
			gotoURLView.setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listViewlayout
					.getLayoutParams();
			params.setMargins(0, 50, 0, 0);
			listViewlayout.setLayoutParams(params);
		} else {
			gotoURLView.setVisibility(View.INVISIBLE);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listViewlayout
					.getLayoutParams();
			params.setMargins(0, 0, 0, 0);
			listViewlayout.setLayoutParams(params);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String s = VoiceCmdHandler.getHandler(SearchActivity.this)
				.onActivityResult(requestCode, resultCode, data);
		searchEdit.setText(s);
		super.onActivityResult(requestCode, resultCode, data);
	}

}
