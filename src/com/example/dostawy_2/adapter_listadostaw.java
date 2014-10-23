package com.example.dostawy_2;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class adapter_listadostaw extends ArrayAdapter<Produkt> {
	
	private Context mContext;
	private List<Produkt> mList;
	
	public adapter_listadostaw(Activity context, List<Produkt> ListProdukty) {
		super(context,R.layout.listitem_produkty,ListProdukty);
		this.mContext=context;
		this.mList=ListProdukty;
	}
	
	public int getCount() {  
		 return mList.size();
	 }  
	
	public Produkt getItem(int position) {  
		 return mList.get(position);  
	}
	
	 public long getItemId(int position) {  
		 return position;
	 }  
	
	 
	 public View getView(int position, View convertView, ViewGroup parent) {   
		 LinearLayout ll;  
		 final int fposition=position;
		 
		 if (convertView == null) {			
			 LayoutInflater li=LayoutInflater.from(mContext);
			 ll = (LinearLayout)li.inflate(R.layout.listitem_produkty, null);
		}
		else {  
			ll = (LinearLayout) convertView;  
		}
		 
		String nazwa = this.mList.get(position).getNazwa();
		((TextView)ll.findViewById(R.id.textView_listitem_produkty_nazwa)).setText(nazwa);
		nazwa = this.mList.get(position).getCena()+" zł";
		while(nazwa.length()<6)nazwa="0"+nazwa;
		nazwa=nazwa.substring(0,nazwa.length()-5)+","+nazwa.substring(nazwa.length()-5);
		((TextView)ll.findViewById(R.id.textView_listitem_produkty_cena)).setText(nazwa);
		
		return ll;  
	 }

}
