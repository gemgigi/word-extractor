package com.maimemo.wordtutor.wordextractor.Extractor;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jason on 3/24/16.
 */
public class Extractor {


    public static final int WORD_MAX_NUMBER = 30000;
    private int[] position = new int[WORD_MAX_NUMBER * 2];
    private List<String> wordList = new ArrayList<>();
    private List<String> phraseList = new ArrayList<>();

    public boolean isLetter(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return true;
        }
        return false;
    }

    public void initPosition(String text) {
        int start = 0;
        int subWordLength = 0;
        boolean isDash = false; //sign -
        int temp = 0;
        int dashCount = 0;
        for (int i = 0, j = 0; i < text.length(); i++) {
            if (isLetter(text.charAt(i))) {
                subWordLength++;
                if (i == text.length() - 1) {
                    position[j] = start;
                    position[j + 1] = i + 1;
                    j += 2;
                    if (isDash) {
                        position[j] = temp;
                        position[j + 1] = i + 1;
                    }
                }
            } else if (subWordLength > 0) {
                //如果是连字符，则添加
                if (isDash) {
                    position[j] = temp;
                    position[j + 1] = i;
                    j += 2;
                }
                if ('-' == text.charAt(i)) {
                    isDash = true;
                    dashCount++;
                    if (dashCount == 1) {
                        temp = start;
                    }
                } else {
                    isDash = false;
                }
                if (subWordLength + start == i && subWordLength + start > 0) {
                    position[j] = start;
                    position[j + 1] = i;
                    j += 2;
                    start = start + subWordLength;
                    subWordLength = 0;
                }
                start++;
            } else {
                start++;
            }
        }
    }


    public void extract(int start, int end) {

    }

    public List<String> start(Library library, String text) {
        initPosition(text);
        SubCharSequence subCharSequence = new SubCharSequence(text, 0, text.length());
        SubCharSequence subCharSequenceCheck = new SubCharSequence(text, 0, text.length());
        int wordMaxAmount = 5;
        int wordPosition = 9; //第5个单词
        for (int i = 0; i < position.length; i += 2) {
            int wordInterval3 = 3;
            int wordInterval5 = 5;
            int wordInterval7 = 7;
            int wordInterval9 = 9;
            int wordAmount = 0;
            if (i > 0 && position[i] == 0 && position[i + 1] == 0) {
                break;
            } else {
                subCharSequence.update(position[i], position[i + 1]);
                if (library.contains(subCharSequence)) {
                    wordList.add(library.get(subCharSequence));
                    //取出连续多个单词比较(现在四个)
                    while (i == 0 && position[i + wordInterval3] != 0 || i < position.length - wordPosition && position[i] != 0 && position[i + wordInterval3] != 0) {
                        if (wordAmount < wordMaxAmount) {
                            subCharSequence.update(position[i], position[i + wordInterval3]);
                            if (library.contains(subCharSequence)) {
                                phraseList.add(library.get(subCharSequence));
                            }
                            wordInterval3 += 2;
                            wordAmount++;
                        }else {
                            break;
                        }
                    }
                    // ... 在第一个单词后
                    while (i == 0 && position[i + wordInterval5] != 0 || i < position.length - wordPosition && (position[i] != 0 && position[i + wordInterval5] != 0)) {
                        subCharSequence.update(position[i], position[i + wordInterval5]);
                        subCharSequenceCheck.update(position[i], position[i + wordInterval5]);
                        subCharSequence.convertType(SubCharSequence.TYPE_SEPERATOR, SubCharSequence.SEPERATOR_POSITION_1);
                        subCharSequenceCheck.convertType(SubCharSequence.TYPE_SEPERATOR, SubCharSequence.SEPERATOR_POSITION_1);
                        if (library.contains(subCharSequenceCheck)) {
                            phraseList.add(library.get(subCharSequence));
                            wordInterval5 += 2;
                        } else {
                            break;
                        }
                    }
                    // ... 在第二个单词后
                    while (i == 0 && position[i + wordInterval7] != 0 || i < position.length - wordPosition && (position[i] != 0 && position[i + wordInterval7] != 0)) {
                        subCharSequence.update(position[i], position[i + wordInterval7]);
                        subCharSequenceCheck.update(position[i], position[i + wordInterval7]);
                        subCharSequence.convertType(SubCharSequence.TYPE_SEPERATOR, SubCharSequence.SEPERATOR_POSITION_2);
                        subCharSequenceCheck.convertType(SubCharSequence.TYPE_SEPERATOR, SubCharSequence.SEPERATOR_POSITION_2);
                        if (library.contains(subCharSequenceCheck)) {
                            phraseList.add(library.get(subCharSequence));
                            wordInterval7 += 2;
                        } else {
                            break;
                        }
                    }

                    while (i == 0 && position[i + wordInterval9] != 0 || i < position.length - wordPosition && (position[i] != 0 && position[i + wordInterval9] != 0)) {
                        subCharSequence.update(position[i], position[i + wordInterval9]);
                        subCharSequenceCheck.update(position[i], position[i + wordInterval9]);
                        subCharSequence.convertType(SubCharSequence.TYPE_SEPERATOR, SubCharSequence.SEPERATOR_POSITION_2);
                        subCharSequenceCheck.convertType(SubCharSequence.TYPE_SEPERATOR, SubCharSequence.SEPERATOR_POSITION_2);
                        if (library.contains(subCharSequenceCheck)) {
                            phraseList.add(library.get(subCharSequence));
                            wordInterval9 += 2;
                        } else {
                            break;
                        }
                    }


//                    if (i < position.length - 7) {
//                        //两个单词
//                        subCharSequence.update(position[i], position[i + 3]);
//                        if (library.contains(subCharSequence)) {
//                            phraseList.add(library.get(subCharSequence));
//                            //三个单词
//                            subCharSequence.update(position[i], position[i + 5]);
//                            if (library.contains(subCharSequence)) {
//                                phraseList.add(library.get(subCharSequence));
//                                //四个单词
//                                subCharSequence.update(position[i], position[i + 7]);
//                                if (library.contains(subCharSequence)) {
//                                    phraseList.add(library.get(subCharSequence));
//                                }
//                            }
//                        }


//                        subCharSequence.update(position[i], position[i + 7]);
//                        subCharSequenceCheck.update(position[i], position[i + 7]);
//                        subCharSequenceCheck.convertType(SubCharSequence.TYPE_SEPERATOR, SubCharSequence.SEPERATOR_POSITION_2);
//                        if (library.contains(subCharSequenceCheck)) {
//                            phraseList.add(library.get(subCharSequence));
//                        }
                    //    }
                }
            }
        }
        Set set = new LinkedHashSet(wordList.size() + phraseList.size());
        set.addAll(wordList);
        set.addAll(phraseList);
        List newList = new ArrayList(set.size());
        newList.addAll(set);
        return newList;
    }
}
