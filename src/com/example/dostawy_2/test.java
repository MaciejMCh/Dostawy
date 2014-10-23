package com.example.dostawy_2;

import android.util.*;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.*;
import java.util.*;



import android.view.*;



import android.content.*;

public class test extends ArrayAdapter<Dostawca>{
	
	private Context mContext;
	private List<Dostawca> mListDostawcy;
	
	
	public test(Activity context, List<Dostawca> ListDostawcy) {
		super(context,R.layout.listitem_dostawcy,ListDostawcy);
		this.mContext = context;
		this.mListDostawcy=ListDostawcy;
	}
	
	 public int getCount() {  
		 return mListDostawcy.size();
	 }  
	 
	 public Dostawca getItem(int position) {  
		 return mListDostawcy.get(position);  
	 }  
	 
	 public long getItemId(int position) {  
		 return position;  
	 }  
	
	 public View getView(int position, View convertView, ViewGroup parent) {  
		 LinearLayout ll;
		 final int fposition=position;
		 if (convertView == null) {
			 LayoutInflater li=LayoutInflater.from(mContext);
			 ll = (LinearLayout)li.inflate(R.layout.listitem_dostawcy, null);			
		}
		else {  
			ll = (LinearLayout) convertView;  
		}
		final String nazwa = this.mListDostawcy.get(position).GetNazwa();
		((TextView)ll.findViewById(R.id.textView_listitem_dostawcy)).setText(nazwa);
		 
		
		return ll;  
}  
	
	
	
}
