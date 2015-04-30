package com.example.rssparser;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {
	  Context ctx;
	  LayoutInflater lInflater;
	  ArrayList<Item> objects;
	  private Item p;

	  ItemAdapter(Context context, ArrayList<Item> items) {
	    ctx = context;
	    objects = items;
	    lInflater = (LayoutInflater) ctx
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  }

	  // кол-во элементов
	  @Override
	  public int getCount() {
	    return objects.size();
	  }

	  // элемент по позиции
	  @Override
	  public Object getItem(int position) {
	    return objects.get(position);
	  }

	  // id по позиции
	  @Override
	  public long getItemId(int position) {
	    return position;
	  }

	  // пункт списка
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    // используем созданные, но не используемые view
	    View view = convertView;
	    if (view == null) {
	      view = lInflater.inflate(R.layout.list_item, parent, false);
	    }

	    p = getItems(position);
	    

	    // заполняем View данными
	    ((TextView) view.findViewById(R.id.speaker_name)).setText(p.speaker);
	    ((TextView) view.findViewById(R.id.speach)).setText(p.speach);
	    ImageView img = (ImageView) view.findViewById(R.id.imageView1);
	    
	    //Используем библиотеку Picasso для получения изображений
	    Picasso.with(ctx)
	    .load(p.img)
	    .into(img);

	    return view;
	  }

	  // товар по позиции
	  Item getItems(int position) {
	    return ((Item) getItem(position));
	  }
	  
}