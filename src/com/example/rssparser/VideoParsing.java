package com.example.rssparser;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VideoParsing {
	
	public VideoParsing(String http) {
		this.url = http;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String parseVideo() {
		
		//class for capturing page
        Document doc;
        try {
        		//define where we take the data
                doc = Jsoup.connect(getUrl()).get();
                //ask from what place
                Elements title = doc.select("script");
                //and in the cycle capture all data which are on the page
                for (Element e : title) {
                    //add in array list
                	for(DataNode node : e.dataNodes()) {
                		//Array for <script>
                        char [] charArr;
                        charArr = node.getWholeData().toCharArray();
                        String videoUrl = "";
                        //to find <script>, which contains a link to the video
                        if(charArr[0] == 'q' && charArr[1] == '(' && charArr[16] == '"')
                        {
                        	for(int i = 28; i < charArr.length; i++) {
                        		//Replace quotes for String on this variant \"
                            	if(charArr[i] == '"')
                            		charArr[i] = '\"';
                            	if(charArr[i] == ']' && charArr[i+1] == ',' && charArr[i+2] == '"' && charArr[i+3] == 'l')
                            		break;
                            	//record JSON from <script> to String
                            	videoUrl += String.valueOf(charArr[i]);
                            }

							try {
								//processes the JSON
								JSONObject json = new JSONObject(videoUrl);
								JSONArray cast = json.getJSONObject("resources").getJSONArray("h264");
								for (int i=0; i<cast.length(); i++) {
									JSONObject c = cast.getJSONObject(i);
									//Our link to the video
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
		
        return file;
	}

	private String url;
	private String file;

}
