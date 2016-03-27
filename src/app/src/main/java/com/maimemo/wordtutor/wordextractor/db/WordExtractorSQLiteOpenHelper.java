package com.maimemo.wordtutor.wordextractor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jason on 3/19/16.
 */
public class WordExtractorSQLiteOpenHelper extends SQLiteOpenHelper {


    private static final String CREATE_WORD = "create table if not exists word(" +
            "id integer primary key autoincrement," +
            "name text," +
            "type text) ";
    private static final String CREATE_WORD_INDEX = "create index index_word on word(name)";

    public WordExtractorSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORD);
        db.execSQL(CREATE_WORD_INDEX);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
