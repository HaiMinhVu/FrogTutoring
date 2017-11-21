package com.haivu.frogtutoring;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haivu on 11/18/17.
 */

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return  db.rawQuery(sql, null);
    }

    public boolean insertStudent(String name, String email, String pass, String phone){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stname", name);
        values.put("stemail", email);
        values.put("stpass", pass);
        values.put("stphone", phone);

        long result =  db.insert("students",null, values);
        if(result == -1)
            return false;
        else
            return true;
    }


}
