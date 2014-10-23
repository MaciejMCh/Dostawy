package com.example.dostawy_2;








import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.*;

import android.widget.*;
import android.database.sqlite.*;
import android.util.*;

import android.database.*;

public class MainActivity extends Activity implements 
DialogFragment_dodajdostawce.DialogDodajListener,
DialogFragment_edytujdostawce.EdytujDostawceListener,
FragmentDostawcy.UsunDostawceListener,
DialogFragment_edytujprodukt.EdycjaProduktyListener,
DialogFragment_wybierzdostawce.WybralDostawceListener,
DialogFragment_podajilosc.DodajDoZamowieniaListener,
DialogFragment_data.DateListener,
DialogFragment_dodajprodukt.DialogDodajProduktListener{
	
	int mOrientation;
	TodoDbAdapter adapter;
	DB_produkty adapter_produkty;
	ActionBar.TabListener tabListener;
	FragmentDostawcy mFragmentDostawcy;
	FragmentNowaDostawa mFragmentNowaDostawa;
	FragmentHistoriaDostaw mFragmentHistoriaDostaw;
	Fragment_M_Edytuj_1 mFragment_M_Edytuj_1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		this.mOrientation = display.getOrientation();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date(System.currentTimeMillis()));
		Log.d("dostawa",strDate);
		AdapterInit();
		FragmentInit();
		AddTabs();
		DBInit();
		
		//DB_dostawy db = new DB_dostawy(this);
		//db.open();
		//for(int i=0;i<=100;i++)db.deleteTodo(i);
		
	}
	
	public void DBInit(){
		new DB_Init(this).Init();
	}
		
	
	public void AddTabs(){
		ActionBar actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		tabListener = new ActionBar.TabListener() {
		       public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		    	   if(tab.getPosition()==0){
		    		   ft.replace(R.id.main_content,mFragmentNowaDostawa);		
		    	   }else if(tab.getPosition()==1){
		    		   ft.replace(R.id.main_content,mFragmentHistoriaDostaw);
		    	   }else if(tab.getPosition()==2){
		    		   if(mOrientation%2==1){
		    			   ft.replace(R.id.main_content,mFragmentDostawcy);
		    		   }else{
		    			   ft.replace(R.id.main_content,mFragment_M_Edytuj_1);
		    		   }
		    	   }
		       }
		       public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {    	   
		       }
		       public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		       }
		};
		
		
		actionBar.addTab(actionBar.newTab()
				.setText("Nowa Dostawa")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab()
				.setText("Historia Dostaw")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab()
				.setText("Edytuj Dostawców")
				.setTabListener(tabListener));
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void AdapterInit(){
		adapter = new TodoDbAdapter(this);
		adapter_produkty = new DB_produkty(this);
	}
	public void FragmentInit(){
		this.mFragmentDostawcy = new FragmentDostawcy();
		this.mFragmentHistoriaDostaw = new FragmentHistoriaDostaw();
		this.mFragmentNowaDostawa = new FragmentNowaDostawa();
		this.mFragment_M_Edytuj_1 = new Fragment_M_Edytuj_1();
	}
	
	public void onEdytujeDostawce(int i,String n){
		adapter.open();
		adapter.updateTodo(i, n, false);		
		adapter.close();
		this.mFragmentDostawcy.odswierz_dostawcow(n);
	}
	
	public void pozyt(String n){
		adapter.open();
		adapter.insertTodo(n);
		adapter.close();
		this.mFragmentDostawcy.PobierzDB();
	}
	public void dodaj(String n, int c, int i){
		adapter_produkty.open();
		adapter_produkty.insertTodo(n, c, i);
		mFragmentDostawcy.wyswietl_produkty();
	}
	public void onUsunalDostawce(Dostawca d){
		adapter.open();
		adapter.deleteTodo(d.GetID());
		adapter.close();
		this.mFragmentDostawcy.zmien_widok_usunietydostawca();
	}
	public void onEdytowanoProdukt(Produkt p){
		Log.d("produkt", p.getID()+" "+p.getNazwa());
		adapter_produkty.open();
		adapter_produkty.updateTodo(p.getID(), p.getNazwa(), p.getCena(), p.getIDDostawcy());
		adapter_produkty.close();
		this.mFragmentDostawcy.wyswietl_produkty();
	}	
	public void onSzukajDostawcow(String s){
		this.mFragmentDostawcy.WyszukajDostawcow(s);
	}
	public void onSzukajProduktow(String s){
		this.mFragmentDostawcy.WyszukajProduktow(s);
	}
	public void onWybranoDostawce(Dostawca d){
		this.mFragmentNowaDostawa.WybralDostawce(d);
	}
	public void onDodalDoZamowienia(int i){
		this.mFragmentNowaDostawa.DodajDoZamowienia(i);
	}
	public void onDateChange(SimpleDateFormat d, boolean o){
		Log.d("ddd", d.toPattern());
	}

}
