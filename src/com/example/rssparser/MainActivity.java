package com.example.rssparser;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity implements  OnItemClickListener {

	private Elements title;
    private Elements speach;
    private Elements img;
    private Elements link;
    private ArrayList<String> titleList = new ArrayList<String>();
    private ArrayList<String> speachList = new ArrayList<String>();
    private ArrayList<String> imgList = new ArrayList<String>();
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
            //������ �������� � �������� ������ ��� ListView � ��������� ������
            new NewThread().execute();
            //������� ��� ListView
            iAdapter = new ItemAdapter(this, list);
            //����������� ���������� ������� �� ��������� �������� ListView
            lv.setOnItemClickListener(this);
       }
    
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    		//������ <script> � ������� ������ �� ����� � ��������� ������
	    	new VideoThread(linkList.get(position)).execute();
    }
    
    public class NewThread extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... arg) {

                    // ����� ������� ����������� ��������
                    Document doc;
                    try {
                            // ���������� ������ ����� ����� ������
                            doc = Jsoup.connect("http://ted.com/talks/").get();
                            // ������ � ������ �����
                            title = doc.select("h4[class=h12 talk-link__speaker]");
                            speach = doc.select("h4[class=h9 m5]");
                            img = doc.select("img[src]");
                            link = doc.select("a[href]");
                            // ������ ��� ����� ���� ��� ���� ��� �� ���������
                            int m = 2;
                            list.clear();
                            titleList.clear();
                            speachList.clear();
                            imgList.clear();
                            linkList.clear();
                            // � � ����� ����������� ��� ������ ����� ���� �� ��������
                            for (Element e : title) {
                                // ���������� � ����� ����
                                titleList.add(e.text());
                            }
                            for (Element e : speach) {
                                // ���������� � ����� ����
                            	speachList.add(e.text());
                            }
                            for (Element e : img) {
                                // ���������� � ����� ����                            	
                            	if(e.attr("src").startsWith("https://tedcdnpi-a.akamaihd.net/r/images")){
                                    imgList.add(e.attr("src"));
                                }
                            }
                            for (Element e : link) {
                                // ���������� � ����� ����
                            	if(e.attr("href").startsWith("/talks/")){
                            		if(m%2 == 0)
                            			linkList.add("http://www.ted.com" + e.attr("href"));
                                m++;
                                }
                            }
                            for (int i = 0; i < titleList.size(); i++) {
                                list.add(new Item(titleList.get(i),speachList.get(i),imgList.get(i),""));
                            }
                    } catch (IOException e) {
                            e.printStackTrace();
                    }
                    return null;
            }

            @Override
            protected void onPostExecute(String result) {

                    // ����� ������� ��������� �������
                    lv.setAdapter(iAdapter);
            }
    }
    
    class VideoThread extends AsyncTask<String, Void, String> {

        private String web;
        
	    private VideoThread(String str) {
	        this.web = str;
	    }
		
        @Override
        protected String doInBackground(String... arg) {

                // ����� ������� ����������� ��������
                Document doc;
                try {
                        // ���������� ������ ����� ����� ������
                        doc = Jsoup.connect(web).get();
                        // ������ � ������ �����, � ������ ��������� ������
                        Elements title = doc.select("script");
                        // � � ����� ����������� ��� ������ ����� ���� �� ��������
                        for (Element e : title) {
                            // ���������� � ����� ����
                        	for(DataNode node : e.dataNodes()) {
                        		//������ ��� <script>
                                char [] charArr;
                                charArr = node.getWholeData().toCharArray();
                                String videoUrl = "";
                                //������� ������������ ��� <script>, � ������� ���������� ������ �� �����
                                if(charArr[0] == 'q' && charArr[1] == '(' && charArr[16] == '"')
                                {
                                	for(int i = 28; i < charArr.length; i++) {
                                		//�������� ��������� ������� �� �������� ��� ���� String ������� \"
	                                	if(charArr[i] == '"')
	                                		charArr[i] = '\"';
	                                	if(charArr[i] == ']' && charArr[i+1] == ',' && charArr[i+2] == '"' && charArr[i+3] == 'l')
	                                		break;
	                                	//����������� ����������� JSON �� <script> � String
	                                	videoUrl += String.valueOf(charArr[i]);
	                                }

									try {
										//������������ ���������� JSON
										JSONObject json = new JSONObject(videoUrl);
										JSONArray cast = json.getJSONObject("resources").getJSONArray("h264");
										for (int i=0; i<cast.length(); i++) {
											JSONObject c = cast.getJSONObject(i);
											//���� ������ �� �����
											file = c.getString("file");
										}
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
                                }
                        	}
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                // ������ �� ���������� ������ ��� � ��� �������)
                return file;
        }

        @Override
        protected void onPostExecute(String result) {

                //��������� ����� � ����� Activity
        		Intent intent = new Intent(MainActivity.this, WebActivity.class);
        		intent.putExtra("http", file);
        		startActivity(intent);
        }
	}
}