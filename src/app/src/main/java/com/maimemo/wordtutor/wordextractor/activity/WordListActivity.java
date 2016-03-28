package com.maimemo.wordtutor.wordextractor.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.maimemo.wordtutor.wordextractor.Extractor.CharSequenceWrapper;
import com.maimemo.wordtutor.wordextractor.Extractor.Extractor;
import com.maimemo.wordtutor.wordextractor.Extractor.Library;
import com.maimemo.wordtutor.wordextractor.Extractor.SubCharSequence;
import com.maimemo.wordtutor.wordextractor.R;
import com.maimemo.wordtutor.wordextractor.adapter.WordListViewAdapter;
import com.maimemo.wordtutor.wordextractor.db.WordExtractorDb;
import com.maimemo.wordtutor.wordextractor.model.Word;
import com.maimemo.wordtutor.wordextractor.util.WordUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordListActivity extends AppCompatActivity {


    private ListView listView;
    private WordListViewAdapter adapter;
    private ArrayAdapter arrayAdapter;
    private String text;
    private List<String> words = new ArrayList<>();
    private ProgressDialog progressDialog;
    private WordExtractorDb wordExtractorDb;
    private long start;
    private Library library;
    private Extractor extractor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordlist);
        listView = (ListView) findViewById(R.id.wordlv);
    //    adapter = new WordListViewAdapter(this, R.layout.item_wordlist, extractedWordList);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,words);
        listView.setAdapter(arrayAdapter);
        Intent intent = getIntent();
        text = intent.getBundleExtra("text_bundle").getString("text");
        progressDialog = new ProgressDialog(WordListActivity.this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
        new GetDataTask().execute();

    }
    public class GetDataTask extends AsyncTask<Void, Void, Map<String, List<String>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }
        @Override
        protected void onPostExecute(Map<String, List<String>> stringListMap) {
            super.onPostExecute(stringListMap);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Log.d("TIME", String.valueOf(System.currentTimeMillis() - start));
            Log.d("size", "" + words.size());
            arrayAdapter = new ArrayAdapter(WordListActivity.this,android.R.layout.simple_list_item_1,words);
            listView.setAdapter(arrayAdapter);
//            arrayAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Map<String, List<String>> doInBackground(Void... params) {
            wordExtractorDb = WordExtractorDb.getWordExtractorDb(WordListActivity.this);
            library = Library.getInstence();
            extractor = Extractor.getInstance();
            if (library.getwords().size() <= 0) {
                library = wordExtractorDb.initLibrary();
            }
            start = System.currentTimeMillis();
            words = extractor.start(library, text);
           // extractor.initPosition(text);
            Log.d("words", words.toString());
            return null;
        }

    }
}
