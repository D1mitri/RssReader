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

	  // ���-�� ���������
	  @Override
	  public int getCount() {
	    return objects.size();
	  }

	  // ������� �� �������
	  @Override
	  public Object getItem(int position) {
	    return objects.get(position);
	  }

	  // id �� �������
	  @Override
	  public long getItemId(int position) {
	    return position;
	  }

	  // ����� ������
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    // ���������� ���������, �� �� ������������ view
	    View view = convertView;
	    if (view == null) {
	      view = lInflater.inflate(R.layout.list_item, parent, false);
	    }

	    p = getItems(position);
	    

	    // ��������� View �������
	    ((TextView) view.findViewById(R.id.speaker_name)).setText(p.speaker);
	    ((TextView) view.findViewById(R.id.speach)).setText(p.speach);
	    ImageView img = (ImageView) view.findViewById(R.id.imageView1);
	    
	    //���������� ���������� Picasso ��� ��������� �����������
	    Picasso.with(ctx)
	    .load(p.img)
	    .into(img);

	    return view;
	  }

	  // ����� �� �������
	  Item getItems(int position) {
	    return ((Item) getItem(position));
	  }
	  
}