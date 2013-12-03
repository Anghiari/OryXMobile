package com.oryx.home;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import com.oryx.handlers.DataHandler;
import com.oryx.handlers.SpeechServiceHandler;
import com.oryx.utils.Utils;
import com.oryx.utils.WSConstants;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends Activity {

	private WebView webView;
	private String url = "";
	private String urlSub = "";
	private String wsurl = "";
	private TextView urlText;
	private ImageView loadingImg;
	private Map<String, String> webinfo = new HashMap<String, String>();
	private LinearLayout loadingLayout;
	private LinearLayout emptyLayout;
	private TextView loadingText;
	private ImageView ttsBtn;
	private ImageView refreshBtn;
	private ImageView feedBtn;
	private Stack<String> historyList = new Stack<String>();
	private ImageView userBtn;
	private RelativeLayout wvlayout;
	private LinearLayout belowoptionview;
	private LinearLayout wvoptionPane;
	private Handler uiHandler = null;
	private MakeInvisibleOptionView makeInvisibleOptionView = null;
	private ImageView fullscreenBtn;
	private TextView erroText;
	private TextView gotoNormalPageBTn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web_view);
		loadingImg = (ImageView) findViewById(R.id.webviewloadingimg);
		rotateAnimation(loadingImg);
		uiHandler = new Handler();
		makeInvisibleOptionView = new MakeInvisibleOptionView();
		init();
		initData();
		// loadWebView();
		new InitWebViewTask().execute("1");
	}

	private void init() {
		webView = (WebView) findViewById(R.id.webview);
		loadingLayout = (LinearLayout) findViewById(R.id.webviewloadingLayout);
		emptyLayout = (LinearLayout) findViewById(R.id.webviewEmpty);
		loadingText = (TextView) findViewById(R.id.webviewloadingtext);
		refreshBtn = (ImageView) findViewById(R.id.wvrefreshBtn);
		wvlayout = (RelativeLayout) findViewById(R.id.vwlayoutt);
		belowoptionview = (LinearLayout) findViewById(R.id.vwbelowoptionview);
		wvoptionPane = (LinearLayout) findViewById(R.id.wvoptionpane);
		erroText = (TextView) findViewById(R.id.errorText);
		gotoNormalPageBTn = (TextView) findViewById(R.id.gotoNormalPageBtn);

		LinearLayout homeBtn = (LinearLayout) findViewById(R.id.webviewHome);
		final Intent homein = new Intent(WebViewActivity.this,
				SearchActivity.class);
		homeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(homein);
			}
		});

		ttsBtn = (ImageView) findViewById(R.id.wvTTSBtn);
		ttsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SpeechServiceHandler.getHandler(WebViewActivity.this)
						.startSpeechServices(url);
			}
		});

		refreshBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				roatateSmallLoader();
				new InitWebViewTask().execute("0");
			}
		});

		feedBtn = (ImageView) findViewById(R.id.wvFeedsBtn);
		feedBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		userBtn = (ImageView) findViewById(R.id.wvUserBtn);
		final Intent userIn = new Intent(WebViewActivity.this,
				UserActivity.class);
		userBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(userIn);
			}
		});

		fullscreenBtn = (ImageView) findViewById(R.id.wvfullScrrenBtn);
		fullscreenBtn.setOnClickListener(new OnClickListener() {
			boolean visibel = true;

			@Override
			public void onClick(View arg0) {
				if (visibel) {
					wvoptionPane.setVisibility(View.GONE);
					visibel = false;
				} else {
					wvoptionPane.setVisibility(View.VISIBLE);
					visibel = true;
				}
			}
		});

		ImageView zoomIn = (ImageView) findViewById(R.id.wvZoomInBtn);
		zoomIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				webView.zoomIn();

			}

		});

		ImageView zoomOut = (ImageView) findViewById(R.id.wvZoomOutBtn);
		zoomOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				webView.zoomOut();

			}

		});

		gotoNormalPageBTn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (webinfo.get(WSConstants.effUrl) != null) {
					Intent browse = new Intent(Intent.ACTION_VIEW, Uri
							.parse(webinfo.get(WSConstants.effUrl)));
					startActivity(browse);
				}
				else{
					Log.d("Pahan", "NULL");
				}

			}

		});

		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new WebAppInterface(this, url),
				"Android");
		webView.setWebViewClient(new OryXWebView());

	}

	private void initData() {

		urlText = (TextView) findViewById(R.id.webviewURL);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			url = extras.getString(Utils.EXTRA_URL);
		}

		urlText.setText(url);

	}

	private void loadWebView() {

		wsurl = DataHandler.getHandler().getWSURL(
				Utils.formatURL(webinfo.get(WSConstants.url)));
		webView.loadUrl(Utils.jsEnginePath); // file:///android_asset/app/index.html
		// webView.loadUrl("file:///android_asset/temp.html");
		setURL(webinfo.get(WSConstants.effUrl));
	}

	public class WebAppInterface {
		Context mContext;
		private String url = "not found";

		WebAppInterface(Context c, String url) {
			mContext = c;
			this.url = url;
		}

		@JavascriptInterface
		public String getURL() {
			return wsurl;
		}

		@JavascriptInterface
		public void setURL(String url) {
			this.url = url;
		}
	}

	private void setURL(String urll) {
		urlText.setText(urll);
		url = Utils.formatURL(urll);

	}

	private class OryXWebView extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String urll) {

			setURL(urll);
			roatateSmallLoader();
			new InitWebViewTask().execute("1");
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			stopSmallLoader();
			super.onPageFinished(view, url);

		}

	}

	@Override
	public void onBackPressed() {

		if (!historyList.isEmpty()) {
			historyList.pop();
		}

		if (!historyList.isEmpty()) {
			roatateSmallLoader();
			setURL(historyList.pop());
			new InitWebViewTask().execute("0");
		} else {
			finish();
		}
	}

	private class InitWebViewTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... arg0) {
			webinfo = DataHandler.getHandler().getWebpageInfo(url);
			if (webinfo.isEmpty()) {
				return 0;
			}
			if (webinfo.get(WSConstants.urlStatus).equals("1")) {

				if (!webinfo.get(WSConstants.extraction).equals("1")) {
					return 2;
				}

				if (arg0[0] != "0") {
					historyList.push(webinfo.get(WSConstants.url));
				}
				return 1;
			} else {
				return 0;
			}

		}

		@Override
		protected void onPostExecute(Integer result) {

			loadingLayout.setVisibility(View.GONE);
			Log.d("Pahan", "" + result);

			if (result == 0) {
				erroText.setText("We are sorry. We could not find the URL given..");
				wvlayout.setVisibility(View.INVISIBLE);
				emptyLayout.setVisibility(View.VISIBLE);
				stopSmallLoader();
			} else if (result == 2) {
				erroText.setText("We are sorry.. Could not do extraction properly..");
				gotoNormalPageBTn.setVisibility(View.VISIBLE);
				wvlayout.setVisibility(View.INVISIBLE);
				emptyLayout.setVisibility(View.VISIBLE);
				stopSmallLoader();
			} else {
				setURL(webinfo.get(WSConstants.url));
				emptyLayout.setVisibility(View.GONE);
				roatateSmallLoader();
				loadWebView();
			}
			super.onPostExecute(result);

		}

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

	private void roatateSmallLoader() {
		refreshBtn.setImageResource(R.drawable.loading2);
		refreshBtn.setBackgroundColor(Color.TRANSPARENT);
		rotateAnimation(refreshBtn);

	}

	private void stopSmallLoader() {
		refreshBtn.setImageResource(R.drawable.refresh);
		refreshBtn.setBackgroundColor(getResources().getColor(R.color.blue));
		refreshBtn.clearAnimation();

	}

	class MakeInvisibleOptionView implements Runnable {

		@Override
		public void run() {
			belowoptionview.setVisibility(View.INVISIBLE);

		}

	}

	@Override
	public void onUserInteraction() {
		if (!belowoptionview.isShown()) {
			belowoptionview.setVisibility(View.VISIBLE);
			uiHandler.postDelayed(makeInvisibleOptionView, 6000);
		}
	}

}
