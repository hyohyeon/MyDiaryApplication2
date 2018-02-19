package com.example.interpark.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hyohyeon on 2018-02-12.
 */

public class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDiary.db";
    private static final int DATABASE_VERSION =1;


    /*
     *먼저 SQLiteOpenHelper클래스를 상속받은 dbHelper클래스가 정의 되어 있다. 데이터베이스 파일 이름은 "mycontacts.db"가되고,
     *데이터베이스 버전은 1로 되어있다. 만약 데이터베이스가 요청되었는데 데이터베이스가 없으면 onCreate()를 호출하여 데이터베이스
     *파일을 생성해준다.
     */

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);");
        db.execSQL("CREATE TABLE diary (\n" +
                "  dno     INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "  ddate   TEXT, \n" +
                "  dtitle TEXT,\n" +
                "  dimgpath TEXT,\n" +
                "  dcontent text, \n" +
                "  id TEXT,\n" +
                "  FOREIGN KEY(id) REFERENCES user(id)\n" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL();

    }
}
