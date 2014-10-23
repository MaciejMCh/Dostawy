package com.example.dostawy_2;

public class Produkt {
	
	protected String mNazwa;
	protected int mCena;
	protected int mIDDostawcy;
	protected int mID;
	
	public Produkt(){
		
	}
	public Produkt(String n, int c, int i, int d){
		this.mNazwa=n;
		this.mCena=c;
		this.mIDDostawcy=i;
		this.mID=d;
	}
	
	String getNazwa(){
		return this.mNazwa;
	}
	
	int getCena(){
		return this.mCena;
	}
	
	int getIDDostawcy(){
		return this.mIDDostawcy;
	}
	
	int getID(){
		return this.mID;
	}
	
	void setNazwa(String n){
		this.mNazwa = n;
	}
	
	void setCena(int c){
		this.mCena = c;
	}

}
