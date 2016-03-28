package com.maimemo.wordtutor.wordextractor.Extractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 3/23/16.
 */
public class CharSequenceWrapper implements CharSequence {

    private String source;
    private String temp;
    private static final String REPLACE_CHAR = "...";
    private static final String END_CHAR_1 = " ...";
    private static final String END_CHAR_2 = " ...?";
    private static final String END_CHAR_3 = "?";
    private static final String END_CHAR_4 = "!";
    private static List<String> pronounList = new ArrayList<>();

    public CharSequenceWrapper(String source) {
        this.source = source;
        this.temp = source;
        replacePronoun();
    }

    static {
        pronounList.add("do sth.");
        pronounList.add("do sth");
        pronounList.add("sb.'s");
        pronounList.add("sth.");
        pronounList.add("sb.");
        pronounList.add("sth");
        pronounList.add("sb");
        pronounList.add("one's");
        pronounList.add("somebody's");
        pronounList.add("somebody");
        pronounList.add("something");
    }

    public void replacePronoun() {
        if (source.indexOf(" ") > -1) {
            for (int i = 0; i < pronounList.size(); i++) {
                if (source.contains(pronounList.get(i))) {
                    temp = temp.replace(pronounList.get(i), REPLACE_CHAR);
                }
            }
        }
    }


    @Override
    public int length() {
        if (temp.endsWith(END_CHAR_1)) {
            return temp.length() - END_CHAR_1.length();
        }
        if (temp.endsWith(END_CHAR_2)) {
            return temp.length() - END_CHAR_2.length();
        }
        if (temp.endsWith(END_CHAR_3) || temp.endsWith(END_CHAR_4)) {
            return temp.length() - END_CHAR_3.length();
        }
        return temp.length();
    }

    @Override
    public char charAt(int index) {
        return temp.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return source.subSequence(start, end);
    }

    @Override
    public int hashCode() {
        int length = length();
        int hash = 0;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                hash = 31 * hash + Character.toLowerCase(charAt(i));
            }
        }
        return hash;
    }

    @Override
    public String toString() {
        return source.toString();
    }
}
