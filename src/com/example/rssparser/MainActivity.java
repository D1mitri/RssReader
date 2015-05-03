package com.example.rssparser;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity implements  OnItemClickListener {

    private ArrayList<String> linkList = new ArrayList<String>();
    private ArrayList<Item> list = new ArrayList<Item>();
    private ItemAdapter iAdapter;
    private ListView lv;
    private String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
            
    	lv = (ListView) findViewById(R.id.listView1);
    	//Parse page and get data for ListView in another thread
    	new PageThread("http://ted.com/talks/").execute();
    	//Assign handler to ListView
    	lv.setOnItemClickListener(this);
    }
    
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	//Parse <script> and find link for video in another thread
	    new VideoThread(linkList.get(position)).execute();
    }
    
    public class PageThread extends AsyncTask<String, Void, String> {
    	
	    private String url;
	        
		private PageThread(String str) {
		    this.url = str;
		}

        @Override
        protected String doInBackground(String... arg) {
            	
            PageParsing p = new PageParsing(url);
            list = p.parse();
            linkList = p.links();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            iAdapter = new ItemAdapter(MainActivity.this, list);
            lv.setAdapter(iAdapter);
        }
    }
    
    public class VideoThread extends AsyncTask<String, Void, String> {

        private String web;
        
	    private VideoThread(String str) {
	        this.web = str;
	    }
		
        @Override
        protected String doInBackground(String... arg) {
        	
        	VideoParsing v = new VideoParsing(web);
        	file = v.parseVideo();
            return file;
        }

        @Override
        protected void onPostExecute(String result) {

            //Open video in new Activity
        	Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        	intent.putExtra("http", file);
        	startActivity(intent);
        }
	}
}