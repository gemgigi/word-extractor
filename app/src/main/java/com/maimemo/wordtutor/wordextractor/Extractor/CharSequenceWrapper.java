package com.maimemo.wordtutor.wordextractor.Extractor;

/**
 * Created by jason on 3/23/16.
 */
public class CharSequenceWrapper implements CharSequence {

    private String source;

    public CharSequenceWrapper(String source) {
        this.source = source;
    }


    @Override
    public int length() {

        if (source.endsWith("...")) {
            return source.length() - 4;
        }
        return source.length();
    }

    @Override
    public char charAt(int index) {
        return source.charAt(index);
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
