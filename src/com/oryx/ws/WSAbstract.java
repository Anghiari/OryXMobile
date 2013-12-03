package com.oryx.ws;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;

import com.oryx.handlers.SessionHandler;
import android.os.StrictMode;

public abstract class WSAbstract {

	protected Document webData = null;
	protected String webURL = "";
	protected boolean status = true;

	public WSAbstract() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

	}

	protected void getDoc(String method) {

		try {
			
			HttpGet uri = new HttpGet(webURL + method);
			DefaultHttpClient client = new DefaultHttpClient();

			HttpResponse resp = client.execute(uri);
			StatusLine status = resp.getStatusLine();
			if (status.getStatusCode() != 200) {
				this.status = false;
			}

			// HttpEntity entity = resp.getEntity();
			// if(entity != null) {
			// String responseBody = EntityUtils.toString(entity);
			// Log.d("pahan", responseBody);
			// }

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			webData = builder.parse(resp.getEntity().getContent());

		} catch (Exception e) {
			this.status = false;
		}
	}

	protected void setWSUrl(String url) {
		webURL = url;
	}

	protected void registerApp(String method) {

		HttpGet uri = new HttpGet(webURL + method);
		DefaultHttpClient client = new DefaultHttpClient();

		try {
			HttpResponse resp = client.execute(uri);
			StatusLine status = resp.getStatusLine();
			if (status.getStatusCode() == 200) {
				SessionHandler.setValidApp(true);
			}
		} catch (Exception e) {
		}

	}

}
