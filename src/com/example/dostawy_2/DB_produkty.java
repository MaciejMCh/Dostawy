package com.example.dostawy_2;







import android.content.*;
import android.util.*;

import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.*;





public class DB_produkty {
	
	
	
	private static final String DEBUG_TAG = "SqLiteTodoManager";
	 
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String DB_PRODUKTY_TABLE = "produkty";
    
    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_NAZWA = "nazwa";
    public static final String NAZWA_OPTIONS = "TEXT NOT NULL";
    public static final int NAZWA_COLUMN = 1;
    public static final String KEY_CENA = "cena";
    public static final String CENA_OPTIONS = "INTEGER NOT NULL";
    public static final int COENA_COLUMN = 2;
    public static final String KEY_ID_DOSTAWCY = "id_dostawcy";
    public static final String ID_DOSTAWCY_OPTIONS = "INTEGER NOT NULL";
    public static final int ID_DOSTAWCY_COLUMN = 3;
    
    public static final String DB_CREATE_PRODUKTY_TABLE =
            "CREATE TABLE " + DB_PRODUKTY_TABLE + "( " +
            KEY_ID + " " + ID_OPTIONS + ", " +
            KEY_NAZWA + " " + NAZWA_OPTIONS + ", " +
            KEY_CENA + " " + CENA_OPTIONS + ", " + 
            KEY_ID_DOSTAWCY + " " + ID_DOSTAWCY_OPTIONS +
            ");";
    
    private static final String DROP_PRODUKTY_TABLE =
            "DROP TABLE IF EXISTS " + DB_PRODUKTY_TABLE;
    
    
    public SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;
    
    public DB_produkty(Context context){
    	this.context = context;
    }
    
    public DB_produkty open(){
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        //DB_VERSION++;
        return this;
    }
    
    public void close() {
        dbHelper.close();
    }
    
    public long insertTodo(String nazwa, int cena, int id_dostawcy) {
        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(KEY_NAZWA, nazwa);
        newTodoValues.put(KEY_CENA, cena);
        newTodoValues.put(KEY_ID_DOSTAWCY, id_dostawcy);
        return db.insert(DB_PRODUKTY_TABLE, null, newTodoValues);
    }
    
    public boolean updateTodo(long id, String nazwa, int cena, int id_dostawcy) {
        String where = KEY_ID + "=" + id;
        ContentValues updateTodoValues = new ContentValues();
        updateTodoValues.put(KEY_NAZWA, nazwa);
        updateTodoValues.put(KEY_CENA, cena);
        updateTodoValues.put(KEY_ID_DOSTAWCY, id_dostawcy);
        return db.update(DB_PRODUKTY_TABLE, updateTodoValues, where, null) > 0;
    }

    public boolean deleteTodo(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(DB_PRODUKTY_TABLE, where, null) > 0;
    }
    
    public Cursor getAllTodos() {
        String[] columns = {KEY_ID, KEY_NAZWA, KEY_CENA, KEY_ID_DOSTAWCY};
        return db.query(DB_PRODUKTY_TABLE, columns, null, null, null, null, null);
    }
    
    public Cursor getPoNazwie(String n, int id) {
    	String[] columns = {KEY_ID, KEY_NAZWA, KEY_CENA, KEY_ID_DOSTAWCY};
    	return db.query(DB_PRODUKTY_TABLE, columns, KEY_ID_DOSTAWCY+" like "+id+" AND "+KEY_NAZWA+" like '%"+n+"%'", null, null, null, KEY_NAZWA);
    }
    
    public Cursor getTodosByID(int id){
    	String[] columns = {KEY_ID, KEY_NAZWA, KEY_CENA, KEY_ID_DOSTAWCY};
    	return db.query(DB_PRODUKTY_TABLE, columns, KEY_ID_DOSTAWCY+" like "+id, null, null, null, KEY_NAZWA);
    }
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
     
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_PRODUKTY_TABLE);
     
            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_PRODUKTY_TABLE + " ver." + DB_VERSION + " created");
        }
     
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_PRODUKTY_TABLE);
     
            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_PRODUKTY_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");
     
            onCreate(db);
        }
    }
    
    
	

}
