package com.example.dostawy_2;

import org.json.*;
import java.util.ArrayList;

import android.app.Fragment;
import android.database.Cursor;
import android.widget.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.*;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentNowaDostawa extends Fragment{
	
	View V;
	DialogFragment_wybierzdostawce mDF;
	ArrayList<Produkt> mListProdukty;
	ArrayList<ProduktWiele> mListZamowienie;
	Produkt mWybranyProdukt;
	int mIDDostawcy;
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		V = inflater.inflate(R.layout.fragment_nowadostawa, container, false);
		this.ViewInit();
        return V;
        
    }
	
	public void DodajDoZamowienia(int i){
		mListZamowienie.add(new ProduktWiele(mWybranyProdukt,i));
		mListProdukty.remove(mWybranyProdukt);
		this.OdswierzListy();
	}
	
	public void ViewInit(){
		((Button)V.findViewById(R.id.button_dodajdostawe)).setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				mDF = new DialogFragment_wybierzdostawce();
				mDF.show(getFragmentManager(), "tag");
			}
		});
		((ListView)V.findViewById(R.id.listView_nz_wszystkieprodukty)).setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				new DialogFragment_podajilosc().show(getFragmentManager(), "tag");
				mWybranyProdukt = ((adapter_listaproduktow)parent.getAdapter()).getItem(position);
			}
		});
		((Button)V.findViewById(R.id.button_dodajzamowienie)).setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				JSONArray JArray = new JSONArray();
				
				int suma=0;
				for(int i=0;i<=mListZamowienie.size()-1;i++){
					JArray.put(mListZamowienie.get(i).toString());
					suma=suma+(mListZamowienie.get(i).getCena()*mListZamowienie.get(i).getIlosc());
				}
				Log.d("baza", "cena: "+suma);
				DB_dostawy DBAdapter = new DB_dostawy(getActivity());
				DBAdapter.open();
				DBAdapter.insertTodo(JArray.toString(),suma,mIDDostawcy);
				DBAdapter.close();
				
				WyswietlOdNowa();
				Toast.makeText(getActivity(), "Dodano dostawę", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void WyswietlOdNowa(){
		((Button)V.findViewById(R.id.button_dodajdostawe)).setVisibility(View.VISIBLE);
		((LinearLayout)V.findViewById(R.id.linearLayout_nowezamowienie)).setVisibility(View.INVISIBLE);
	}
	
	public void PobierzProdukty(int id){
		DB_produkty adapter_produkty = new DB_produkty(getActivity());
		adapter_produkty.open();
		Cursor cursor;
		cursor = adapter_produkty.getTodosByID(id);
		cursor.moveToFirst();
		mListZamowienie = new ArrayList<ProduktWiele>();
		mListProdukty = new ArrayList<Produkt>();
		for(int i=0;i<=cursor.getCount()-1;i++){
			mListProdukty.add(new Produkt(cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(0)));
			cursor.moveToNext();
		}
		adapter_produkty.close();
	}
	
	public void OdswierzListy(){
		((ListView)V.findViewById(R.id.listView_nz_wszystkieprodukty)).setAdapter(new adapter_listaproduktow(getActivity(),mListProdukty));
		((ListView)V.findViewById(R.id.listView_nowezamowienie)).setAdapter(new adapter_zamowienie(getActivity(),mListZamowienie));
	}
	
	public void WybralDostawce(Dostawca d){
		this.mIDDostawcy=d.GetID();
		mDF.dismiss();
		((Button)V.findViewById(R.id.button_dodajdostawe)).setVisibility(View.INVISIBLE);
		((LinearLayout)V.findViewById(R.id.linearLayout_nowezamowienie)).setVisibility(View.VISIBLE);
		
		PobierzProdukty(d.GetID());
		OdswierzListy();
		
	}
	
	
	

}
