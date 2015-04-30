package com.example.rssparser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class WebActivity extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.web_layout);
            
            Intent intent = getIntent();
            String LINK = intent.getStringExtra("http");
            
            VideoView videoView =(VideoView)findViewById(R.id.videoView1);
            MediaController mc = new MediaController(this);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            Uri video = Uri.parse(LINK);
            videoView.setMediaController(mc);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.start();
	}
}