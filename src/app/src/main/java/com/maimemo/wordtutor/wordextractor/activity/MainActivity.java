package com.maimemo.wordtutor.wordextractor.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.maimemo.wordtutor.wordextractor.R;
import com.maimemo.wordtutor.wordextractor.db.WordExtractorDb;
import com.maimemo.wordtutor.wordextractor.util.WordUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private EditText editText;
    private Button button;
    private WordExtractorDb wordExtractorDb;
    private ProgressDialog progressDialog;
    private boolean isFirstLauncher = false;
    private String text;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edittext);
        button = (Button) findViewById(R.id.extractor);
        // wordExtractorDb = WordExtractorDb.getWordExtractorDb(MainActivity.this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        isFirstLauncher = sp.getBoolean("isFirstLauncher", false);
        if (!isFirstLauncher) {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
            new LoadDataTask().execute();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editText.getText().toString();
               // text = WordUtil.getWordFromFile(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, WordListActivity.class);
                intent.putExtra("text",text);
                startActivity(intent);

            }
        });

    }

    public class LoadDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirstLauncher",true);
            editor.commit();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            wordExtractorDb = WordExtractorDb.getWordExtractorDb(MainActivity.this);
            wordExtractorDb.loadWordList(MainActivity.this);
            return null;
        }
    }


}
