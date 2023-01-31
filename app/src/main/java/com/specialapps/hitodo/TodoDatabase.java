package com.specialapps.hitodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class TodoDatabase extends SQLiteOpenHelper {
    public static class TodoData{
        public final String text;
        private final long _startDate;
        private final long _endDate;
        public final String startDate;
        public final String endDate;
        public final int id;


        public TodoData(String text, long startDate, long endDate, int id) {
            this.text = text;
            _startDate = startDate;
            _endDate = endDate;
            this.id = id;

            this.startDate =  new Date(_startDate).toString().substring(4,16);
            this.endDate =  new Date(_endDate).toString().substring(4,16);
        }
    }

    public static final String DATABASE_NAME = "Todo.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "todo";
    public static final String COLUMN_ID = "_id";

    // Dates are  represented by unix epoch time
    public static final String COLUMN_START_DATE = "_start_date";
    public static final String COLUMN_END_DATE = "_end_date";
    public static final String COLUMN_TEXT = "_text";

    private Context context;

    public TodoDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_START_DATE + " INTEGER, " +
                        COLUMN_END_DATE + " INTEGER, "+
                        COLUMN_TEXT + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    // The crud methods
    public boolean create(String text, long startDate, long endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_START_DATE, startDate);
        cv.put(COLUMN_END_DATE, endDate);
        cv.put(COLUMN_TEXT, text);

        long insert = db.insert(TABLE_NAME, null, cv);

        db.close();
        if(insert == -1){
            Toast.makeText(context, "Couldn't insert the row into the database", Toast.LENGTH_LONG).show();
            return false;
        }else{
            Toast.makeText(context, "Inserted row into the database!!", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    public boolean update(int id, String text){
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_TEXT + " = \"" +
                text + "\" WHERE " + COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(!cursor.moveToNext()){
            Toast.makeText(context, "Successfully updated todo with id: "+id, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(context, "Couldn't updated todo with id: "+id, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<TodoData> getAllData(){
        ArrayList<TodoData> data = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                long startDate = cursor.getLong(1);
                long endDate = cursor.getLong(2);
                String text = cursor.getString(3);
                data.add(new TodoData(text, startDate, endDate, id));
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return data;
    }

    public boolean delete(int id){
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(!cursor.moveToNext()){
            Toast.makeText(context, "Successfully deleted todo with id: "+id, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(context, "Couldn't delete todo with id: "+id, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
