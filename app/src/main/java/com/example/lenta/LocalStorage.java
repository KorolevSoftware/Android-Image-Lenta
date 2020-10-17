package com.example.lenta;

import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalStorage extends SQLiteOpenHelper {

    public LocalStorage(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("bd", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table photo ("
                + "id text primary key,"
                + "islike text"
                +  ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
