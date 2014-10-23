package com.example.dostawy_2;

import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.view.View.OnClickListener;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.*;
import java.util.*;
import android.widget.ExpandableListView.*;
import java.lang.*;
import java.text.*;
public class FragmentHistoriaDostaw extends Fragment{
	
	View V;
	ArrayList<Dostawa> mListDostawa;
	ArrayList<ProduktWiele> mListProdukty;
	adapter_listadostaw_ex mELA;
	int mIDWczytywanego;
	int mLastExpandedGroupPosition=-1;
	SimpleDateFormat mDataOd;
	SimpleDateFormat mDataDo;
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		V = inflater.inflate(R.layout.fragment_historiadostaw, container, false);	
		
		this.filtrInit();
		this.ViewInit();
		this.WczytajDostawy();
		this.ListInit();
		
		
		
		
        return V;
    }
	
	public void filtrInit(){
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		this.mDataDo = new SimpleDateFormat(dateFormat.format(cal.getTime()));
		String s =dateFormat.format(cal.getTime());
		
		Log.d("ddd",this.mDataDo.toPattern());
	}
	
	public void WczytajDostawy(){
		Cursor cursor;
		DB_dostawy DBAdapter = new DB_dostawy(this.getActivity());
		this.mListDostawa = new ArrayList<Dostawa>();
		
		DBAdapter.open();
		cursor=DBAdapter.getAllTodos();
		cursor.moveToFirst();
		
		for(int i=0;i<=cursor.getCount()-1;i++){
			TodoDbAdapter DAdapter = new TodoDbAdapter(getActivity());
			DAdapter.open();
			Cursor DCursor = DAdapter.getPoID(cursor.getInt(3));
			DCursor.moveToFirst();
			DAdapter.close();
			this.mListDostawa.add(new Dostawa(cursor.getInt(0),new SimpleDateFormat(cursor.getString(1)),cursor.getInt(3),cursor.getInt(2),DCursor.getString(0),cursor.getString(4)));
			cursor.moveToNext();
		}
		
		
		DBAdapter.close();
	}
	
	public void ListInit(){
		
		mELA = new adapter_listadostaw_ex(getActivity(),this.mListDostawa);
		((ExpandableListView)V.findViewById(R.id.expandableListView1)).setAdapter(mELA);
	}
	
	public void setIDWczytywanego(int i){
		this.mIDWczytywanego = i;
	}

	public void ViewInit(){
		
		
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,new String[]{"Wszyscy","Piekarnia"});
		((Spinner)V.findViewById(R.id.spinner1)).setAdapter(adapter);
	}
	
	public void setData(SimpleDateFormat d, boolean o){
		
	}
	
	public void odswierzFiltry(){
		((TextView)V.findViewById(R.id.textView_DO)).setText(this.mDataDo.toPattern());
		((TextView)V.findViewById(R.id.textView_OD)).setText(this.mDataOd.toPattern());
		
	}
	
	
}
