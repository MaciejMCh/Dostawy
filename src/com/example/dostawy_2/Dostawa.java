package com.example.dostawy_2;

import java.util.*;

import android.util.Log;
import java.text.*;

public class Dostawa {
	
	private int mID;
	private SimpleDateFormat mData;
	private int mIDDostawcy;
	private int mWartosc;
	private String mNazwaDostawcy;
	private ArrayList<ProduktWiele> mLista;
	
	public Dostawa(int i, SimpleDateFormat d, int dos, int w, String n){
		this.mID = i;
		this.mData = d;
		this.mIDDostawcy = dos;
		this.mWartosc = w;
		this.mNazwaDostawcy = n;
		
	}
	
	public Dostawa(int i, SimpleDateFormat d, int dos, int w, String n, String p){
		this.mID = i;
		this.mData = d;
		this.mIDDostawcy = dos;
		this.mWartosc = w;
		this.mNazwaDostawcy = n;
		
		String[] prod=p.substring(1,p.length()-1)
				.replace('"', '\0')
				.split(",");
		
		this.mLista = new ArrayList<ProduktWiele>();
		for(int it=0;it<=prod.length-1;it++){
			this.mLista.add(new ProduktWiele(prod[it].trim()));
		}
		
				
		Log.d("dataa", mData.toPattern());
	}
	
	public int getID(){
		return this.mID;
	}
	public SimpleDateFormat getData(){
		return this.mData;
	}
	public int getIDDostawcy(){
		return this.mIDDostawcy;
	}
	public int getWartosc(){
		return this.mWartosc;
	}
	public String getNazwaDostawcy(){
		return this.mNazwaDostawcy;
	}
	public ArrayList<ProduktWiele> getLista(){
		return this.mLista;
	}
	
}
