package com.example.rssparser;

public class Item {
	
	String speaker; //Author
	String speach; //Theme
	String img; //Image
	String link; //Page link
	
	Item(String _speaker, String _speach, String _img, String _link) {
		
		speaker = _speaker;
		speach = _speach;
		img = _img;
		link = _link;
		
	}
}