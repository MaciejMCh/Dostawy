package com.example.dostawy_2;



import android.app.*;
import android.os.Bundle;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.database.*;
import java.util.*;

import com.example.dostawy_2.DialogFragment_dodajdostawce.DialogDodajListener;



import android.content.*;
import android.view.View.OnClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class FragmentDostawcy extends Fragment{
	
	View V;
	ListView lv;
	TodoDbAdapter adapter;
	DB_produkty adapter_produkty;
	Button mButtonDodaj;
	Button mButtonDodajProdukt;
	ListAdapter mLAdapter;
	int mWybranyDostawca = -1;
	Dostawca mWybrany;
	UsunDostawceListener mListener;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		V = inflater.inflate(R.layout.fragment_dostawcy, container, false);	
		
		mWybrany=null;
		
		DBInit();
		ViewInit();
		LVInit();
		PobierzDB();
		SearchInit();
		
		
		
		
		
		
        return V;
    }
	
	public void SearchInit(){
		((SearchView)V.findViewById(R.id.searchView1)).setOnQueryTextListener(new OnQueryTextListener(){
			@Override 
		    public boolean onQueryTextChange(String newText) { 
		        return true; 
		    } 

		    @Override 
		    public boolean onQueryTextSubmit(String query) { 
		        mListener.onSzukajDostawcow(query);
		        return true;
		    }
		});
		((SearchView)V.findViewById(R.id.searchView2)).setOnQueryTextListener(new OnQueryTextListener(){
			@Override 
		    public boolean onQueryTextChange(String newText) { 
		        return true; 
		    } 

		    @Override 
		    public boolean onQueryTextSubmit(String query) { 
		        mListener.onSzukajProduktow(query);
		        return true;
		    }
		});
		
	}
	
	public void DBInit(){
		adapter = new TodoDbAdapter(this.getActivity());		
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
	
	public void WyszukajProduktow(String n){
		((SearchView)V.findViewById(R.id.searchView2)).clearFocus();
		
		this.adapter_produkty.open();
		Cursor cursor;
		cursor = this.adapter_produkty.getPoNazwie(n, this.mWybranyDostawca);
		cursor.moveToFirst();
		
		ArrayList<Produkt> ListProdukt = new ArrayList();
		for(int i=0;i<=cursor.getCount()-1;i++){
			ListProdukt.add(new Produkt(cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(0)));
			cursor.moveToNext();
		}
		ListView lv = (ListView)V.findViewById(R.id.listView2);
		lv.setAdapter(new adapter_listaproduktow(getActivity(),ListProdukt));
		
		//for(int i=0;i<=100;i++)this.adapter_produkty.deleteTodo(i);
		
		this.adapter_produkty.close();
	}
	
	public void WyszukajDostawcow(String n){
		((SearchView)V.findViewById(R.id.searchView1)).clearFocus();
		adapter.open();
		Cursor cursor = adapter.getPoNazwie(n);
		
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
	
	public void LVInit(){
		lv = (ListView)V.findViewById(R.id.listView1);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	adapter_listadostawcow adapter = (adapter_listadostawcow)parent.getAdapter();
		    	Dostawca dostawca = adapter.getItem(position);
		    	if(mWybrany==null)FragmentDostawcy.this.zmien_widok_pierwszywybor();
		    	mWybranyDostawca=dostawca.GetID();
		    	mWybrany=dostawca;
		    	FragmentDostawcy.this.wyswietl_produkty();
		    	
		    	((TextView)V.findViewById(R.id.TextView_fragment_dostawcyy)).setText("Dostawca: "+dostawca.GetNazwa());
		    	
		    }
		});
		
		((ListView)V.findViewById(R.id.listView2)).setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				new DialogFragment_edytujprodukt()
					.setWybrany(((adapter_listaproduktow)parent.getAdapter()).getItem(position))
					.show(getFragmentManager(), "tag");
			}
		});
		
	}
	
	public void zmien_widok_pierwszywybor(){
		V.findViewById(R.id.TextView_fragment_dostawcy_puste).setVisibility(View.INVISIBLE);
    	V.findViewById(R.id.TextView_fragment_dostawcy).setVisibility(View.VISIBLE);
    	V.findViewById(R.id.searchView2).setVisibility(View.VISIBLE);
    	V.findViewById(R.id.button2).setVisibility(View.VISIBLE);
    	V.findViewById(R.id.listView2).setVisibility(View.VISIBLE);
	}
	
	public void zmien_widok_usunietydostawca(){
		V.findViewById(R.id.TextView_fragment_dostawcy_puste).setVisibility(View.VISIBLE);
    	V.findViewById(R.id.TextView_fragment_dostawcy).setVisibility(View.INVISIBLE);
    	V.findViewById(R.id.searchView2).setVisibility(View.INVISIBLE);
    	V.findViewById(R.id.button2).setVisibility(View.INVISIBLE);
    	V.findViewById(R.id.listView2).setVisibility(View.INVISIBLE);
    	this.mWybrany=null;
    	this.PobierzDB();
	}
	
	public void odswierz_dostawcow(String n){
		PobierzDB();
		this.mWybrany.SetNazwa(n);
		((TextView)V.findViewById(R.id.TextView_fragment_dostawcyy)).setText("Dostawca: "+this.mWybrany.GetNazwa());
	}
	
	public void wyswietl_produkty(){
		this.adapter_produkty = new DB_produkty(getActivity());
		this.adapter_produkty.open();
		Cursor cursor;
		//cursor = this.adapter_produkty.getAllTodos();
		cursor = this.adapter_produkty.getTodosByID(this.mWybranyDostawca);
		cursor.moveToFirst();
		
		ArrayList<Produkt> ListProdukt = new ArrayList();
		for(int i=0;i<=cursor.getCount()-1;i++){
			ListProdukt.add(new Produkt(cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(0)));
			cursor.moveToNext();
		}
		ListView lv = (ListView)V.findViewById(R.id.listView2);
		lv.setAdapter(new adapter_listaproduktow(getActivity(),ListProdukt));
		
		//for(int i=0;i<=100;i++)this.adapter_produkty.deleteTodo(i);
		
		this.adapter_produkty.close();
	}
	
	public void ViewInit(){
		mButtonDodaj = (Button)V.findViewById(R.id.button1);
		mButtonDodaj.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				new DialogFragment_dodajdostawce().show(getActivity().getFragmentManager(), "tag");
			}
		});
		
		mButtonDodajProdukt = (Button)V.findViewById(R.id.button2);
		mButtonDodajProdukt.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(mWybranyDostawca!=-1){
					DialogFragment_dodajprodukt df = new DialogFragment_dodajprodukt();
					df.setWybrany(FragmentDostawcy.this.mWybranyDostawca);
					df.show(getActivity().getFragmentManager(), "tag");
				}
				else Toast.makeText(getActivity(), "Wybierz dostawcę", Toast.LENGTH_SHORT).show();
			}
		});
		
		ImageButton IBEdytuj = (ImageButton)V.findViewById(R.id.imageButton1);
		IBEdytuj.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				new DialogFragment_edytujdostawce().SetDostawca(FragmentDostawcy.this.mWybrany).show(getActivity().getFragmentManager(), "tag");
			}
		});
		
		((ImageButton)V.findViewById(R.id.imageButton2)).setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				new DialogFragment(){		
					
					Dostawca mDostawca;	
					UsunDostawceListener mListener;
					
					DialogFragment SetDostawca(Dostawca d){
						this.mDostawca = d;
						return this;
					}		
					
					public void onAttach(Activity activity) {
				        super.onAttach(activity);
				        try {
				            mListener = (UsunDostawceListener) activity;
				        } catch (ClassCastException e) {
				            throw new ClassCastException(activity.toString()
				                    + " must implement NoticeDialogListener");
				        }
				    }
					
					@Override
				    public Dialog onCreateDialog(Bundle savedInstanceState) {
				        return new AlertDialog.Builder(getActivity())
				        	.setMessage("Usunąć Dostawcę "+mWybrany.GetNazwa()+"?")
				        	.setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
				        		public void onClick(DialogInterface dialog, int id) {
				        			mListener.onUsunalDostawce(mWybrany);
				        		}
				        	})
				        	.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
				        		public void onClick(DialogInterface dialog, int id) {
				        		}
				        	})
				        	.create();
					}
				}.SetDostawca(mWybrany).show(getActivity().getFragmentManager(), "tag");
			}
		});	
	}
	
	public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (UsunDostawceListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	
	

	
	public interface UsunDostawceListener{
		public void onUsunalDostawce(Dostawca d);
		public void onSzukajDostawcow(String s);
		public void onSzukajProduktow(String s);
	}
	
	

}
