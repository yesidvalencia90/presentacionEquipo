package com.example.presentacionequipo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "integrats_table";
    private static final String COL1 = "id";
    private static final String COL2 = "name";
    private static final String COL3 = "last_name";
    private static final String COL6 = "identification";
    private static final String COL4 = "phone";
    private static final String COL5 = "profile_pic";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL6 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, String lastName, String cc, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, lastName);
        contentValues.put(COL6, cc);
        contentValues.put(COL4, phone);
        contentValues.put(COL5, "FOTO AQUI :)");
        Log.d(TAG, "addData: Adding " + name + "to " + TABLE_NAME);
        Long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIdByName(String identification){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " || " + " ' ' " + " || " + COL3 + " = '" + fullName + "'";
        //String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + fullName + "'";
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL6 + " = '" + identification + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIntegrantByIdentification(String cc) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL6 + " = '" + cc + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIntegrantById(int selectedID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + selectedID + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
