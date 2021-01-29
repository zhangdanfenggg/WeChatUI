package com.wechatui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbOpenHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE_SQL = "create table users(_id integer primary key autoincrement,  name, phonenumber, password, imageid)";
    final String CREATE_TABLE_SQL_2 = "create table friend_circle(_id integer primary key autoincrement,  uid integer references users(_id), text, time,isdelete)";


    public DbOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL_2);
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.print("oldVersion:"+oldVersion+" newVersion:"+newVersion);
    }

}
