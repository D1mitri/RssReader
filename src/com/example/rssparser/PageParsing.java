package com.example.rssparser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParsing {
	
	public PageParsing(String http) {
		this.setUrl(http);
	}	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public ArrayList<Item> parse() {
		ArrayList<Item> list = new ArrayList<Item>();
		
		//class for capturing page
        Document doc;
        try {
                //define where we take the data
                doc = Jsoup.connect(getUrl()).get();
                //ask from what place
                title = doc.select("h4[class=h12 talk-link__speaker]");
                speach = doc.select("h4[class=h9 m5]");
                img = doc.select("img[src]");
                link = doc.select("a[href]");
                //clear array lists
                int m = 2;
                list.clear();
                titleList.clear();
                speachList.clear();
                imgList.clear();
                linkList.clear();
                //and in the cycle capture all data which are on the page
                for (Element e : title) {
                    //add in array list
                    titleList.add(e.text());
                }
                for (Element e : speach) {
                    //add in array list
                	speachList.add(e.text());
                }
                for (Element e : img) {
                    //add in array list
                	if(e.attr("src").startsWith("https://tedcdnpi-a.akamaihd.net/r/images")){
                        imgList.add(e.attr("src"));
                    }
                }
                for (Element e : link) {
                    //add in array list
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
		
		return list;
	}
	
	public ArrayList<String> links(){
		ArrayList<String> arr = new ArrayList<String>(linkList);
		return arr;
	}

	private String url;
	private Elements title;
    private Elements speach;
    private Elements img;
    private Elements link;
    private ArrayList<String> titleList = new ArrayList<String>();
    private ArrayList<String> speachList = new ArrayList<String>();
    private ArrayList<String> imgList = new ArrayList<String>();
    private ArrayList<String> linkList = new ArrayList<String>();

}
