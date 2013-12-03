/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.oryx.notifications;

import com.oryx.handlers.SpeechServiceHandler;
import com.oryx.home.R;
import com.oryx.utils.WSConstants;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * PingService creates a notification that includes 2 buttons: one to snooze the
 * notification, and one to dismiss it.
 */
public class NotificationService extends IntentService {
	


	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	private String url = "url";

	public NotificationService() {

		super("com.oryx");
	}
	
	

	@Override
	protected void onHandleIntent(Intent intent) { 

		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Bundle extras = intent.getExtras();
		if (extras != null) {
			url = extras.getString("url");
		}
		issueNotification(intent);
		String action = intent.getAction();
		if (action == null) {

		} else if (action.equals("PAUSE")) {
			SpeechServiceHandler.getHandler().stopTTSPlaying();
			Log.d("PAHAN", "PAUSE CLIECKED");
		} else if (action.equals("STOP")) {
			SpeechServiceHandler.getHandler().stopTTSPlaying();
			mNotificationManager.cancelAll();
			
			Log.d("PAHAN", "STOP CLIECKED");
		}

		

	}

	private void issueNotification(Intent intent) {

		Intent pauseIntent = new Intent(this, NotificationService.class);
		pauseIntent.setAction("PAUSE");
		PendingIntent piPause = PendingIntent.getService(this, 0, pauseIntent,
				0);

		Intent stopIntent = new Intent(this, NotificationService.class);
		stopIntent.setAction("STOP");
		PendingIntent piStop = PendingIntent.getService(this, 0, stopIntent, 0);

		Notification n = new NotificationCompat.Builder(this)
				.setContentTitle("Text To Speech")
				.setContentText("Reading the content")
				.setContent(getNotiView(piPause, piStop))
				.setSmallIcon(R.drawable.noti_icon).build();
		
		mNotificationManager.notify(0, n);
	}

	private RemoteViews getNotiView(PendingIntent pi, PendingIntent pi2) {
		RemoteViews remoteViews = new RemoteViews(this.getPackageName(),
				R.layout.noti_custom);
		remoteViews.setTextViewText(R.id.notiURL, this.url);
		remoteViews.setOnClickPendingIntent(R.id.notipause, pi);
		remoteViews.setOnClickPendingIntent(R.id.notiplay, pi2);
		return remoteViews;
	}

}
