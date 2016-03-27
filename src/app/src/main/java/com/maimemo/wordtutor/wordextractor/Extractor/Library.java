package com.maimemo.wordtutor.wordextractor.Extractor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jason on 3/23/16.
 */
public class Library {

    private Map<CharSequenceWrapper, CharSequenceWrapper> words;
    private Map<CharSequenceWrapper, CharSequenceWrapper> phrases;
    private static Library library;

    public Library() {
        words = new HashMap<>();
        phrases = new HashMap<>();
    }

    public static Library getInstence() {
        if (library == null) {
            library = new Library();
        }
        return library;
    }

    public boolean contains(SubCharSequence subCharSequence) {
        if (words.containsKey(subCharSequence)) {
            return true;
        }
        return false;
    }

    public void loadData(CharSequenceWrapper word) {
        words.put(word, word);
    }

    public void loadPhrase(CharSequenceWrapper phrase){
        phrases.put(phrase,phrase);
    }

    public Map<CharSequenceWrapper, CharSequenceWrapper> getwords() {
        return words;
    }
    public String get(SubCharSequence key){
        return words.get(key).toString();
    }
}
