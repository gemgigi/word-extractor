package com.maimemo.wordtutor.wordextractor.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.maimemo.wordtutor.wordextractor.Extractor.CharSequenceWrapper;
import com.maimemo.wordtutor.wordextractor.Extractor.Library;
import com.maimemo.wordtutor.wordextractor.util.MyApplication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jason on 3/19/16.
 */
public class WordExtractorDb {
    private static final String DBNAME = "word.db";
    private static final int VERSION = 1;
    private static WordExtractorDb wordExtractorDb;
    private SQLiteDatabase db;
    private WordExtractorSQLiteOpenHelper openHelper;
    private List<String> phraseList = new ArrayList<>();
    private Map<String, Object> map2 = new HashMap<>();


    public Map<String, Object> getMap2() {
        return map2;
    }

    public WordExtractorDb(Context context) {
        openHelper = new WordExtractorSQLiteOpenHelper(context, DBNAME, null, VERSION);
        db = openHelper.getWritableDatabase();

    }


    public synchronized static WordExtractorDb getWordExtractorDb(Context context) {
        if (wordExtractorDb == null) {
            wordExtractorDb = new WordExtractorDb(context);
        }
        return wordExtractorDb;
    }


    public List<String> getPhraseList() {
        return phraseList;
    }



    public Map<String,Object> billMap(){
        Cursor cursor = db.query("word", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            map2.put(cursor.getString(cursor.getColumnIndex("name")),null);
        }
        if (cursor!=null){
            cursor.close();
        }
        return map2;
    }

    public Library initLibrary(){
        Library library = Library.getInstence();
        Cursor cursor = db.query("word", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            library.loadData(new CharSequenceWrapper(cursor.getString(cursor.getColumnIndex("name"))));
        }
        if (cursor!=null){
            cursor.close();
        }
        return library;
    }

    public boolean queryWord(String word) {
        Cursor cursor = null;
        try {
            cursor = db.query("word", null, "name = ?", new String[]{word}, null, null, null, null);
            while (cursor.moveToNext()) {
                return true;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }


    public void loadWordList(Context context) {
        List<String> words = getWordFromFile(context);
        String sql = "insert into word(name) values(?)";
        db.beginTransaction();
        try {
            for (String w : words) {
                SQLiteStatement statement = db.compileStatement(sql);
                statement.bindString(1, w);
                //statement.bindString(2, w.substring(0, 1));
                statement.executeInsert();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }

    public List<String> getWordFromFile(Context context) {
        InputStream inputStream;
        BufferedReader bufferedReader;
        List<String> words = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            inputStream = assetManager.open("word-list.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                words.add(word);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
       // Collections.sort(words);
        return words;
    }
}
