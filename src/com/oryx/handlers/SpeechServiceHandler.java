package com.oryx.handlers;

import com.oryx.notifications.NotificationService;
import com.oryx.tts.TTSService;
import android.content.Context;
import android.content.Intent;

public class SpeechServiceHandler {

	private Intent notificationServiceIntent;
	private Intent ttsServiceIntent;
	private Context currentContext;
	
	private static SpeechServiceHandler ssh = null;
	
	public static String status = "ready";
	
	private SpeechServiceHandler(Context c) {
		currentContext = c;
		
		ttsServiceIntent = new Intent( currentContext ,TTSService.class);
		notificationServiceIntent = new Intent(currentContext, NotificationService.class);
	}
	

	public static SpeechServiceHandler getHandler(Context c) {
		if (ssh == null) {
			ssh = new SpeechServiceHandler(c);
			return ssh;
		} else {
			return ssh;
		}
	}

	public static SpeechServiceHandler getHandler() {
		return ssh;
	}

	public void startSpeechServices(String urlParam){
		
		ttsServiceIntent.putExtra("url", urlParam);
		currentContext.startService(ttsServiceIntent);
		
		notificationServiceIntent.putExtra("url", urlParam);
		currentContext.startService(notificationServiceIntent);

	}
	
	public void stopTTSPlaying(){
		currentContext.stopService(ttsServiceIntent);
	}
	

	public Intent getTTSServiceIntent() {
		return ttsServiceIntent;
	}

	public void setTTSServiceIntent(Intent tTSService) {
		ttsServiceIntent = tTSService;
	}
}
