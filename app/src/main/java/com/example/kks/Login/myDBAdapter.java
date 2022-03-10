package com.example.kks.Login;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class myDBAdapter {
    Context context;
    myDBHelper myDBHelper;
    SQLiteDatabase myDB;

    public myDBAdapter(Context context){
        this.context = context;
    }

    //DB 반환
    public myDBAdapter open() throws SQLException {
        myDBHelper = new myDBHelper(context);
        myDB = myDBHelper.getReadableDatabase();

        return this;
    }

    //초기화
    public void clear() {
        myDBHelper.onUpgrade(myDB, 0, 1);
    }

    //데이터 삽입
    public void insert(String name) {
        myDB.execSQL("INSERT INTO groupTBL VALUES('" + name + "')");
    }

    //행 수정
    public void update(String name, String num) {
        myDB.execSQL("UPDATE groupTBL SET gNumber=" + name + " WHERE gName=" + "\"" + num + "\"");
    }

    //행 삭제
    public void delete(String key) {
        myDB.execSQL("DELETE FROM groupTBL WHERE gName=" + "\"" + key + "\"");
    }

    //닫기
    public void close(){
        myDB.close();
    }

    //커서 반환
    public Cursor getCursor () {
        Cursor cursor;
        cursor = myDB.rawQuery("SELECT * FROM groupTBL;", null);
        return cursor;
    }

    public String openId(){
        String dbId;
        Cursor cursor = getCursor();
        myDB = myDBHelper.getReadableDatabase();
        dbId = "";
        while(cursor.moveToNext()){
            dbId += cursor.getString(0);
        }
        cursor.close();;
        return dbId;
    }
}
