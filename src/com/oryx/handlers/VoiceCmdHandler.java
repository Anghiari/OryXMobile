package com.oryx.handlers;

import java.util.ArrayList;
import java.util.List;

import com.oryx.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.util.Log;

public class VoiceCmdHandler {

	private Context currntContext;
	private static VoiceCmdHandler handler;
	
	private VoiceCmdHandler(Context context) {
		currntContext = context;
	}
	
	public static VoiceCmdHandler getHandler(Context c){
		if(handler == null){
			handler = new VoiceCmdHandler(c);
			return handler;
		}
		else{
			return handler;
		}
	}
	public boolean checkVoiceRecognition() {
			  // Check if voice recognition is present
			  PackageManager pm = currntContext.getPackageManager();
			  List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
			    RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
			  if (activities.size() == 0) {
				   return false;
			  }
			  return true;
	 }
	
	public Intent startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "OryX Voice Recognition");
        return intent;
    }
	
	 public String onActivityResult(int requestCode, int resultCode, Intent data)
	    {
		 	ArrayList<String> matches = new ArrayList<String>();
	        if (requestCode == Utils.REQUEST_CODE && resultCode == -1)
	        {
	            // Populate the wordsList with the String values the recognition engine thought it heard
	            matches = data.getStringArrayListExtra(
	                    RecognizerIntent.EXTRA_RESULTS);
	        }
	        if(matches.size() > 0){
	        	return matches.get(0);
	        }
	        else{
	        	return "";
	        }

	    }
}
