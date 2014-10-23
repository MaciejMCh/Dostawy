package com.example.dostawy_2;

import android.util.Log;
import android.widget.Toast;

public class ProduktWiele extends Produkt{
	protected int mIlosc;
	
	public ProduktWiele(Produkt p,int i){
		this.mCena=p.getCena();
		this.mID=p.getID();
		this.mIDDostawcy=p.getIDDostawcy();
		this.mNazwa=p.getNazwa();
		this.mIlosc=i;
	}
	
	public ProduktWiele(String wpis){
		String[] dane = wpis.split(":");
		this.mNazwa = dane[0];
		this.mCena = Integer.parseInt(dane[1]);
		this.mID = Integer.parseInt(dane[2]);
		this.mIDDostawcy = Integer.parseInt(dane[3]);
		this.mIlosc = Integer.parseInt(dane[4]);
		
	}
	
	public int getIlosc(){
		return this.mIlosc;
	}
	
	public void setIlosc(int i){
		this.mIlosc=i;
	}
	
	public int getSuma(){
		return this.mCena*this.mIlosc;
	}
	
	@Override
	public String toString(){
		return this.mNazwa+":"+this.mCena+":"+this.mID+":"+this.mIDDostawcy+":"+this.mIlosc;
	}
	
}
