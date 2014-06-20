package com.edufe.mobile;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnInfoListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.edufe.fxlunion.R;

public class EduPlayerActivity extends Activity{

    private VideoView videoView;
    private ProgressBar loadingDialog;
    private String returnValue = "";
	private int stopPosition;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//force fullscreen
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//force lansacpe
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		
		videoView = (VideoView) findViewById(R.id.videoView);  
		loadingDialog = (ProgressBar) findViewById(R.id.progressBar1);  
		MediaController mediaController = new MediaController(this); 
		videoView.setMediaController(mediaController); 
	
		Intent intent = getIntent();
		String videopath = intent.getStringExtra("videopath");
		final int start = intent.getIntExtra("start", 0);
        Uri uri = Uri.parse(videopath);
        
        videoView.setVideoURI(uri);  
        videoView.requestFocus();
        videoView.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener(){
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.seekTo(start);
				initMediaPlayer(mp);
			}
        });
		
		videoView.start();
		
		//check playing progress every 0.5 seconds
		new Timer().schedule(new TimerTask(){
			@Override
			public void run(){
				returnValue = String.valueOf(videoView.getCurrentPosition() / 1000);
			}
		}, 0, 500);
	}
	
	public void initMediaPlayer(MediaPlayer mp){
		loadingDialog.setVisibility(View.GONE);
		mp.setOnInfoListener(new OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    loadingDialog.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    loadingDialog.setVisibility(View.GONE);
                }
                return false;
            }
        });
		
	}
	
	@Override
	public void finish(){
		Intent intent = getIntent();

		intent.putExtra("time", returnValue);
		setResult(RESULT_OK, intent);
		super.finish();
	}

	@Override
	public void onPause() {
	    super.onPause();
	    stopPosition = videoView.getCurrentPosition(); //stopPosition is an int
	    videoView.pause();
	}
	@Override
	public void onResume() {
	    super.onResume();
	    videoView.seekTo(stopPosition);
	    videoView.start(); //Or use resume() if it doesn't work. I'm not sure
	}

}


