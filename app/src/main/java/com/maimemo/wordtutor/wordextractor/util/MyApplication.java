package com.maimemo.wordtutor.wordextractor.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by jason on 3/19/16.
 */
public class MyApplication extends Application {


    private static  Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
