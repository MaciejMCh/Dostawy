package com.example.dostawy_2;





import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DB_Init {
	
	 private static final int DB_VERSION = 1;
	 private static final String DB_NAME = "database.db";
	 
	 public SQLiteDatabase db;
	 private Context context;
	 private DatabaseHelper dbHelper;
	    
	 public DB_Init(Context context){
		 this.context = context;
	 }
	 
	 public void Init(){
		 dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
		 db=dbHelper.getWritableDatabase();
     }
	 
	 
	 private static class DatabaseHelper extends SQLiteOpenHelper {
	        public DatabaseHelper(Context context, String name,
	                CursorFactory factory, int version) {
	            super(context, name, factory, version);
	        }	        
	     
	        @Override
	        public void onCreate(SQLiteDatabase db) {
	            db.execSQL(DB_dostawy.DB_CREATE_TODO_TABLE);
	            db.execSQL(DB_produkty.DB_CREATE_PRODUKTY_TABLE);
	            db.execSQL(TodoDbAdapter.DB_CREATE_TODO_TABLE);
	        }
	     
	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            //db.execSQL(DROP_PRODUKTY_TABLE);
	        }
	    }

	
	
}
