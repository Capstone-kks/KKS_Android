package com.example.kks.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper extends SQLiteOpenHelper {
    public myDBHelper(Context context) {
        super(context, "groupDB", null, 1);
    }

    //FIRST CREATE TABLE
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE groupTBL ( gName CHAR(20) PRIMARY KEY);");
        //db.execSQL("CREATE TABLE groupTBL ( gImg CHAR(20) PRIMARY KEY);");
    }

    //DELETE&CREATE TABLE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS groupTBL");
        onCreate(db);
    }
}
