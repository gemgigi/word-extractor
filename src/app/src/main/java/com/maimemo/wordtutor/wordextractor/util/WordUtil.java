package com.maimemo.wordtutor.wordextractor.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by jason on 3/19/16.
 */
public class WordUtil {


    public static List<String> splitText(String text) {

        List<String> list = new ArrayList<>();
        String regex = "[,.\\s-]+";
        String[] words = text.replaceAll("[\\[\\]\\(\\)<>/\'\"!@#&\\$%\\^\\*\\?\\d:;]*", "").split(regex);
        // Arrays.sort(words);
        for (int i = 0; i < words.length; i++) {
            if (Character.isUpperCase(words[i].charAt(0))) {
                if (words[i].length() > 1) {
                    if (words[i].equals(words[i].toUpperCase())) {
                        list.add(words[i]);
                    } else {
                        list.add(words[i].toLowerCase());
                    }
                } else {
//                    list.add(words[i]);
                    list.add(words[i].toLowerCase());
                }
            } else {
                list.add(words[i]);
            }
        }
        return list;

    }

    public static String getWordFromFile(Context context) {
        InputStream inputStream;
        BufferedReader bufferedReader;
        List<String> words = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        StringBuilder sb = new StringBuilder();
        try {
            inputStream = assetManager.open("word-list.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String word;
            while ((word = bufferedReader.readLine()) != null) {
//                words.add(word);
                sb.append(word);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Collections.sort(words);
        return sb.toString();
    }


}
