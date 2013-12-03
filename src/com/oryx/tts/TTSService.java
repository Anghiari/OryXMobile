package com.oryx.tts;

import java.util.Locale;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import com.oryx.handlers.DataHandler;
import com.oryx.utils.Utils;

public class TTSService extends Service implements TextToSpeech.OnInitListener, OnUtteranceCompletedListener {
    private TextToSpeech tts;
    private StringBuilder text2peech;
  
    private String textContent;
    private String url;
    @Override
    public void onCreate() {
    	 //Empty for now
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
    	url = intent.getExtras().getString("url");
		textContent = DataHandler.getHandler().getVoiceContentFromService(Utils.formatURL(url));
        tts = new TextToSpeech(this, this);
        return START_STICKY;
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US); 
            int utterResult= tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
				
				@Override
				public void onStart(String utteranceId) {
					
				}
				
				@Override
				public void onError(String utteranceId) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onDone(String utteranceId) {
					// TODO Auto-generated method stub
			    	Log.d("chinthaka TTSService 62", "done");
					
				}
			});
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
            	speakOut(textContent);
            }
        }
    }

    @Override
    public void onUtteranceCompleted(String uttId) {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();	
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    	

	public void speakOut(String words) {

		if (!words.isEmpty())
			tts.speak(words,TextToSpeech.QUEUE_ADD, null);
			
	}


}
