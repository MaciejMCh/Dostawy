package com.example.dostawy_2;





import java.text.*;
import java.util.Date;

import android.content.*;
import android.util.*;

import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.*;



public class DB_dostawy {
	
	private static final String DEBUG_TAG = "SqLiteTodoManager";
	 
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String DB_TODO_TABLE = "dostawy";
    
    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_DATA = "data";
    public static final String DATA_OPTIONS = "DATETIME NOT NULL";
    public static final int DATA_COLUMN = 1;
    public static final String KEY_PRODUKTY = "produkty";
    public static final String PRODUKTY_OPTIONS = "TEXT";
    public static final int PRODUKTY_COLUMN = 2;
    public static final String KEY_SUMA = "suma";
    public static final String SUMA_OPTIONS = "INTEGER";
    public static final int SUMA_COLUMN = 3;
    public static final String KEY_DOSTAWCA = "dostawca";
    public static final String DOSTAWCA_OPTIONS = "INTEGER";
    public static final int DOSTAWCA_COLUMN = 4;
    
    
    public static final String DB_CREATE_TODO_TABLE =
            "CREATE TABLE " + DB_TODO_TABLE + "( " +
            KEY_ID + " " + ID_OPTIONS + ", " +
            KEY_DATA + " " + DATA_OPTIONS + ", " +
            KEY_PRODUKTY + " " + PRODUKTY_OPTIONS + ", " +
            KEY_SUMA + " " + SUMA_OPTIONS + ", " +
            KEY_DOSTAWCA + " " + DOSTAWCA_OPTIONS +
            ");";
    
    private static final String DROP_TODO_TABLE =
            "DROP TABLE IF EXISTS " + DB_TODO_TABLE;  
    
    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;
    
    public DB_dostawy(Context context){
    	this.context=context;
    }
    
    public DB_dostawy open(){
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    
    
    public void close() {
        dbHelper.close();
    }
    
    public long insertTodo(String produkty,int suma, int dostawca) {
        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(KEY_PRODUKTY, produkty);
        newTodoValues.put(KEY_SUMA, suma);
        newTodoValues.put(KEY_DOSTAWCA, dostawca);
        //newTodoValues.put(KEY_DATA, DateFormat.getDateTimeInstance(). format(new Date()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date(System.currentTimeMillis()));
 
        newTodoValues.put(KEY_DATA,strDate);
        
        return db.insert(DB_TODO_TABLE, null, newTodoValues);
        
    }
     
    public boolean updateTodo(long id, String produkty) {
        String where = KEY_ID + "=" + id;
        ContentValues updateTodoValues = new ContentValues();
        updateTodoValues.put(KEY_PRODUKTY, produkty);
        return db.update(DB_TODO_TABLE, updateTodoValues, where, null) > 0;
    }
    
    public boolean deleteTodo(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(DB_TODO_TABLE, where, null) > 0;
    }
    
    public Cursor getAllTodos() {
        String[] columns = {KEY_ID, KEY_DATA, KEY_SUMA, KEY_DOSTAWCA, KEY_PRODUKTY};
        return db.query(DB_TODO_TABLE, columns, null, null, null, null, KEY_DATA);
    }
    
    public Cursor getProduktyPoID(int ID) {
        String[] columns = {KEY_PRODUKTY};
        return db.query(DB_TODO_TABLE, columns, KEY_ID+" like "+ID, null, null, null, KEY_DATA);
    }
    
    
    
    
    
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
     
        @Override
        public void onCreate(SQLiteDatabase db) {
        	Log.d("baza", "robi");
            db.execSQL(DB_CREATE_TODO_TABLE);
     
            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " ver." + DB_VERSION + " created");
        }
     
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TODO_TABLE);
     
            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_TODO_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");
     
            onCreate(db);
        }
    }
    
    
    
    
}
