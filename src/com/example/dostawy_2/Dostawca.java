package com.example.dostawy_2;

public class Dostawca {
	private String mNazwa;
	private int mID;
	
	public Dostawca(){
		
	}
	
	public Dostawca(String n,int i){
		mNazwa=n;
		mID=i;
	}
	
	public String GetNazwa(){
		return mNazwa;
	}
	
	public int GetID(){
		return mID;
	}
	
	void SetNazwa(String n){
		this.mNazwa=n;
	}
	

}
