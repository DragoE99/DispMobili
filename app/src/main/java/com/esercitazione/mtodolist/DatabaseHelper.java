package com.esercitazione.mtodolist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance =null;
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todolist.db";
    private static final String TABLE_NAME = "todoItems";

    private static final String KEY_ID = "id";
    private static final String KEY_TASK = "task";
    private static final String KEY_DATE = "creation_date";
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context){
        if(instance==null){
            instance= new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + TABLE_NAME + " ("
                + KEY_ID + " integer primary key autoincrement, "
                + KEY_TASK + " TEXT not null, "
                + KEY_DATE + " INTEGER" +
                ");");
        Log.d(TAG, "onCreate(): create table");
    }
    public long insertItem(TodoItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK, item.getTask());
        values.put(KEY_DATE, item.getDate().getTimeInMillis());
        long id = db.insert(TABLE_NAME,
                null,
                values);
        Log.d(TAG, "insertItem("+id+") " + item.toString());
        db.close();
        return id;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
        Log.d(TAG, "onUpgrade(): created fresh table");
    }


    public List<TodoItem> getAllItems() {
        List<TodoItem> items = new LinkedList<TodoItem>();

        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TodoItem task = null;
        if (cursor.moveToFirst()) {
            do {
                task = new TodoItem();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTask(cursor.getString(1));
                GregorianCalendar createOn = new GregorianCalendar();
                createOn.setTimeInMillis(cursor.getLong(2));
                task.setDate(createOn);
                items.add(task);
// Add item to items
            } while (cursor.moveToNext());
        } // 4. close
        db.close();
        Log.d(TAG, "getAllItems(): "+ items.toString());
        return items; // return items
    }
    public void deleteItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, //table name
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(item.getId()) }); //selections args

        db.close();

        Log.d(TAG, "deleted item "+item.toString());
    }

}