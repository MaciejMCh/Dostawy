package com.example.dostawy_2;

import android.content.*;
import android.util.*;

import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.*;



public class TodoDbAdapter {
	
	private static final String DEBUG_TAG = "SqLiteTodoManager";
	 
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String DB_TODO_TABLE = "z";
    
    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_DESCRIPTION = "description";
    public static final String DESCRIPTION_OPTIONS = "TEXT NOT NULL";
    public static final int DESCRIPTION_COLUMN = 1;
    public static final String KEY_COMPLETED = "completed";
    public static final String COMPLETED_OPTIONS = "INTEGER DEFAULT 0";
    public static final int COMPLETED_COLUMN = 2;
    
    public static final String DB_CREATE_TODO_TABLE =
            "CREATE TABLE " + DB_TODO_TABLE + "( " +
            KEY_ID + " " + ID_OPTIONS + ", " +
            KEY_DESCRIPTION + " " + DESCRIPTION_OPTIONS + ", " +
            KEY_COMPLETED + " " + COMPLETED_OPTIONS +
            ");";
    private static final String DROP_TODO_TABLE =
            "DROP TABLE IF EXISTS " + DB_TODO_TABLE;    
    
    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;
    
    public TodoDbAdapter(Context context){
    	this.context=context;
    }
    
    public TodoDbAdapter open(){
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
    
    public long insertTodo(String description) {
        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(KEY_DESCRIPTION, description);
        return db.insert(DB_TODO_TABLE, null, newTodoValues);
    }
     
    public boolean updateTodo(long id, String description, boolean completed) {
        String where = KEY_ID + "=" + id;
        int completedTask = completed ? 1 : 0;
        ContentValues updateTodoValues = new ContentValues();
        updateTodoValues.put(KEY_DESCRIPTION, description);
        updateTodoValues.put(KEY_COMPLETED, completedTask);
        return db.update(DB_TODO_TABLE, updateTodoValues, where, null) > 0;
    }
    
    public boolean deleteTodo(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(DB_TODO_TABLE, where, null) > 0;
    }
    
    public Cursor getAllTodos() {
        String[] columns = {KEY_ID, KEY_DESCRIPTION, KEY_COMPLETED};
        return db.query(DB_TODO_TABLE, columns, null, null, null, null, KEY_DESCRIPTION);
    }
    
    public Cursor getPoID(int ID) {
        String[] columns = {KEY_DESCRIPTION};
        return db.query(DB_TODO_TABLE, columns, KEY_ID+" like "+ID, null, null, null, KEY_DESCRIPTION);
    }
    
    public Cursor getPoNazwie(String n) {
        String[] columns = {KEY_ID, KEY_DESCRIPTION, KEY_COMPLETED};
        return db.query(DB_TODO_TABLE, columns, KEY_DESCRIPTION+" like '%"+n+"%'", null, null, null, KEY_DESCRIPTION);
    }
    
    
    
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
     
        @Override
        public void onCreate(SQLiteDatabase db) {
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
