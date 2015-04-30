package com.example.rssparser;

public class Item {
	
	String speaker; //Автор
	String speach; //Тема доклада
	String img; //Изображение
	String link; //Ссылка на страницу
	
	Item(String _speaker, String _speach, String _img, String _link) {
		
		speaker = _speaker;
		speach = _speach;
		img = _img;
		link = _link;
		
	}
}