package com.example.dostawy_2;

public class Zlotowki {

	static String getString(int l){
		String s=l+" zł";
		while(s.length()<6)s="0"+s;
		s=s.substring(0,s.length()-5)+","+s.substring(s.length()-5);
		return s;
	}
}
