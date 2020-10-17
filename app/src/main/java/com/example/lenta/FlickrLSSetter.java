package com.example.lenta;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FlickrLSSetter {

    private SQLiteDatabase db;

    FlickrLSSetter(SQLiteDatabase db)
    {
        this.db = db;
    }

    public boolean hasLike(String photoID)
    {
        Cursor c = db.rawQuery("select islike from photo where id="+photoID,null);
        c.moveToFirst();
        String isLikeRaw = c.getString(c.getColumnIndex("islike"));
        return isLikeRaw.equals("yes");
    }

    public void likeEnable(String photoID) {
        ContentValues cv = new ContentValues();
        cv.put("islike", "yes");
        db.update("photo", cv, "id = " + photoID, null);
    }

    public void likeDisable(String photoID) {
        ContentValues cv = new ContentValues();
        cv.put("islike", "no");
        db.update("photo", cv, "id = " + photoID, null);
    }
}
