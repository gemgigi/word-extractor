package com.maimemo.wordtutor.wordextractor.Extractor;

import android.util.Log;

/**
 * Created by jason on 3/23/16.
 */
public class SubCharSequence implements CharSequence {
    private CharSequence source;
    private int start;
    private int end;
    private int length;
    private int tense = 0;
    public static final String SEPORATOR = ".";
    public static final int SEPORATOR_LENGTH = 3;
    public static final int SEPERATOR_POSITION_0 = 0;
    public static final int SEPERATOR_POSITION_1 = 1;
    public static final int SEPERATOR_POSITION_2 = 2;
    public static final int SEPERATOR_POSITION_3 = 3;
    public int seperatorPosition = SEPERATOR_POSITION_0;
    private int appearTime = 0;
    private int differLength = 0;
    private int replaceTime = 0;
    public static final int TYPE_SEPERATOR = 2;

    public SubCharSequence(CharSequence source, int start, int end) {
        this.source = source;
        this.start = start;
        this.end = end;
        this.length = source.length();
    }

    public void update(int newStart, int newEnd) {
        this.start = newStart;
        this.end = newEnd;
        this.seperatorPosition = SEPERATOR_POSITION_0;
        replaceTime = 0;
        appearTime = 0;
        differLength = 0;
    }

    public void convertTense(int tense) {
        this.tense = tense;
    }

    public void convertType(int tense, int position) {
        this.tense = tense;
        this.seperatorPosition = position;
        switch (seperatorPosition) {
            case SEPERATOR_POSITION_1:
            case SEPERATOR_POSITION_2:
            case SEPERATOR_POSITION_3:
                checkSpace();
                break;
        }
    }

    public void checkSpace() {
        int firstPosition = 0;
        int secondPosition = 0;
        int account = 0;
        switch (seperatorPosition) {
            case SEPERATOR_POSITION_1:
                for (int i = 0; i < length(); i++) {
                    if (source.charAt(i) == ' ') {
                        account++;
                        if (account % 2 != 0) {
                            firstPosition = i;
                        } else {
                            secondPosition = i;
                            break;
                        }
                    }
                }
                break;
            case SEPERATOR_POSITION_2:
                for (int i = 0; i < length(); i++) {
                    if (source.charAt(i) == ' ') {
                        account++;
                        if (account == 1) {
                            continue;
                        }
                        if (account % 2 == 0) {
                            firstPosition = i;
                        } else {
                            secondPosition = i;
                            break;
                        }
                    }
                }
                break;
            case SEPERATOR_POSITION_3:
                for (int i = 0; i < length(); i++) {
                    if (source.charAt(i) == ' ') {
                        account++;
                        if (account >= 3) {
                            if (account % 2 != 0) {
                                firstPosition = i;
                            } else {
                                secondPosition = i;
                                break;
                            }
                        }
                    }
                }
                break;
        }
        differLength = secondPosition - firstPosition - 3 - 1;

    }

    @Override
    public int length() {
        if (tense == TYPE_SEPERATOR) {
            return end - start - differLength;
        }
        return end - start;
    }

    @Override
    public char charAt(int index) {
        switch (seperatorPosition) {
            case SEPERATOR_POSITION_0:
                return source.charAt(start + index);
            case SEPERATOR_POSITION_1:

                if (appearTime == 2 && replaceTime < SEPORATOR_LENGTH) {
                    replaceTime++;
                    return SEPORATOR.charAt(0);
                }
                if (replaceTime == SEPORATOR_LENGTH) {
                    return source.charAt(start + index + differLength);
                } else {
                    if (source.charAt(start + index) == ' ') {
                        appearTime++;
                        if (appearTime == 2 && replaceTime < SEPORATOR_LENGTH) {
                            replaceTime++;
                            return SEPORATOR.charAt(0);
                        } else {
                            return source.charAt(start + index);
                        }
                    } else if (appearTime > 0 && appearTime % 2 != 0) {
                        replaceTime++;
                        return SEPORATOR.charAt(0);
                    }
                }
                break;
            case SEPERATOR_POSITION_2:

                if (appearTime == 3 && replaceTime < SEPORATOR_LENGTH) {
                    replaceTime++;
                    return SEPORATOR.charAt(0);
                }
                if (replaceTime == SEPORATOR_LENGTH) {
                    return source.charAt(start + index + differLength);
                } else {
                    if (source.charAt(start + index) == ' ') {
                        appearTime++;
                        if (appearTime == 3 && replaceTime < SEPORATOR_LENGTH) {
                            replaceTime++;
                            return SEPORATOR.charAt(0);
                        } else {
                            return source.charAt(start + index);
                        }
                    } else if (appearTime > 1 && appearTime % 2 == 0) {
                        replaceTime++;
                        return SEPORATOR.charAt(0);
                    }
                }
                break;
            case SEPERATOR_POSITION_3:
                if (appearTime == 4 && replaceTime < SEPORATOR_LENGTH) {
                    replaceTime++;
                    return SEPORATOR.charAt(0);
                }
                if (replaceTime == SEPORATOR_LENGTH) {
                    return source.charAt(start + index + differLength);
                } else {
                    if (source.charAt(start + index) == ' ') {
                        appearTime++;
                        if (appearTime == 4 && replaceTime < SEPORATOR_LENGTH) {
                            replaceTime++;
                            return SEPORATOR.charAt(0);
                        } else {
                            return source.charAt(start + index);
                        }
                    } else if (appearTime > 2 && appearTime % 2 != 0) {
                        replaceTime++;
                        return SEPORATOR.charAt(0);
                    }
                }
                break;

        }
        return source.charAt(start + index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        length = length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                hash = 31 * hash + Character.toLowerCase(charAt(i));
            }
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public String toString() {
        int length = length();
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = charAt(i);
        }
        return new String(chars);
    }
}
