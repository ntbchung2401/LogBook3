package com.example.logbook3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "logbook.db";
    private static final String IMAGE_URL = "imageUrl";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String url_table = "CREATE TABLE "+IMAGE_URL+ "(image_id INTEGER primary key autoincrement, imgURL TEXT);";
        db.execSQL(url_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+IMAGE_URL);
        onCreate(db);

    }
    public Cursor readAll(){
        String query ="SELECT * FROM "+ IMAGE_URL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor readImgURL(){
        String query ="SELECT imgURL FROM "+ IMAGE_URL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void addImgURL(String imgURL){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("imgUrl", imgURL);
        long result = db.insert(IMAGE_URL,null,cv);
        if(result == -1){
            Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully to Add", Toast.LENGTH_SHORT).show();
        }
    }

}
