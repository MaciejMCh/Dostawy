package com.example.dostawy_2;

import java.util.ArrayList;

import android.app.*;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ListView;
import android.database.*;


public class Fragment_M_Edytuj_1 extends Fragment{
	
	View V;
	ListView lv;
	TodoDbAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		V = inflater.inflate(R.layout.fragment_m_edyt_1, container, false);
		
		adapter = new TodoDbAdapter(getActivity());
		
		this.viewInit();
		this.PobierzDB();
		
		return V;
	}
	
	public void viewInit(){
		lv = (ListView)V.findViewById(R.id.listView_m1);
	}
	
	

	public void PobierzDB(){
		adapter.open();
		Cursor cursor = adapter.getAllTodos();
		
		cursor.moveToFirst();
		ArrayList<Dostawca> ListDostawca = new ArrayList();
		for(int i = 0;i<=cursor.getCount()-1;i++){
			Log.d("liscik", cursor.getString(1));
			ListDostawca.add(new Dostawca(cursor.getString(1),cursor.getInt(0)));
			cursor.moveToNext();
		}
		lv.setAdapter(new adapter_listadostawcow(getActivity(),ListDostawca));
		adapter.close();
	}
	
}
