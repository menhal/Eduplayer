package com.edufe.mobile;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;


public class EduPlayer extends CordovaPlugin {

	CallbackContext callbackContext;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	CordovaInterface cordova = this.cordova;
    	this.callbackContext = callbackContext;
    	
    	String videopath = args.getString(0);
		int start = Integer.parseInt(args.getString(1)) * 1000;
    	
    	Intent intent = new Intent();
    	intent.setClass(cordova.getActivity(), EduPlayerActivity.class);
		intent.putExtra("videopath", videopath);
		intent.putExtra("start", start);
		
		cordova.setActivityResultCallback(this);
		cordova.startActivityForResult(this, intent, 1);
    	
        return true;
    }
    

    public void onActivityResult(int requestCode, int resultCOde, Intent data) {
    	
    	JSONObject json = new JSONObject();
    	try {
    		if(data != null && data.hasExtra("time")){
    	    	String time = data.getStringExtra("time");
    	    	json.put("time", time);
    	    }
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	callbackContext.success(json);
    }

}
