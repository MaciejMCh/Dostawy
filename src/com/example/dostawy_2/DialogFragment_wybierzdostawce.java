package com.example.dostawy_2;

import android.os.Bundle;
import android.widget.*;
import android.widget.SearchView.OnQueryTextListener;
import android.app.*;
import android.content.DialogInterface;
import android.view.*;
import android.database.*;
import java.util.*;
import android.widget.AdapterView.OnItemClickListener;

import com.example.dostawy_2.DialogFragment_dodajdostawce.DialogDodajListener;
import com.example.dostawy_2.DialogFragment_dodajprodukt.DialogDodajProduktListener;

public class DialogFragment_wybierzdostawce extends DialogFragment {	
	
	View V;
	WybralDostawceListener mListener;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        V=this.getActivity().getLayoutInflater().inflate(R.layout.alertdialog_nowadostawa_wybierzdostawce, null);
        
        
        this.ViewInit();
        this.WyswietlWszystkich();
        
        
        builder
        	.setNegativeButton("Anuluj", null)
        	.setView(V);
        
        
        return builder.create();
	}
	
	public void ViewInit(){
		((SearchView)V.findViewById(R.id.searchView_dialog_nowadostawa_wybierzdostawe)).setOnQueryTextListener(new OnQueryTextListener(){
			@Override 
		    public boolean onQueryTextChange(String newText) { 
		        return true; 
		    } 

		    @Override 
		    public boolean onQueryTextSubmit(String query) { 
		        DialogFragment_wybierzdostawce.this.WyswietlSzukanych(query);
		        return true;
		    }
		});
		((ListView)V.findViewById(R.id.listView_dialog_nowadostawa_wybeirzdostawe)).setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mListener.onWybranoDostawce(((adapter_listadostawcow)parent.getAdapter()).getItem(position));
		    }			
		});
	}
	
	public void WyswietlSzukanych(String n){
		((SearchView)V.findViewById(R.id.searchView_dialog_nowadostawa_wybierzdostawe)).clearFocus();
		TodoDbAdapter adapter = new TodoDbAdapter(this.getActivity());
		adapter.open();
		Cursor cursor;
		cursor=adapter.getPoNazwie(n);
		cursor.moveToFirst();
		ArrayList<Dostawca> List = new ArrayList<Dostawca>();
		for(int i=0;i<=cursor.getCount()-1;i++){
			List.add(new Dostawca(cursor.getString(1),cursor.getInt(0)));
			cursor.moveToNext();
		}
		((ListView)V.findViewById(R.id.listView_dialog_nowadostawa_wybeirzdostawe)).setAdapter(new adapter_listadostawcow(getActivity(),List));
		
		
		adapter.close();		
	}
	
	public void WyswietlWszystkich(){
		TodoDbAdapter adapter = new TodoDbAdapter(this.getActivity());
		adapter.open();
		Cursor cursor;
		cursor=adapter.getAllTodos();
		cursor.moveToFirst();
		ArrayList<Dostawca> List = new ArrayList<Dostawca>();
		for(int i=0;i<=cursor.getCount()-1;i++){
			List.add(new Dostawca(cursor.getString(1),cursor.getInt(0)));
			cursor.moveToNext();
		}
		((ListView)V.findViewById(R.id.listView_dialog_nowadostawa_wybeirzdostawe)).setAdapter(new adapter_listadostawcow(getActivity(),List));
		
		
		adapter.close();		
	}

	public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (WybralDostawceListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	public interface WybralDostawceListener{
		public void onWybranoDostawce(Dostawca d);
	}

}
