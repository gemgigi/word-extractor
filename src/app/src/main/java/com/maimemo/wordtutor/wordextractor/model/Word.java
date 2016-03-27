package com.maimemo.wordtutor.wordextractor.model;

import java.io.Serializable;

/**
 * Created by jason on 3/21/16.
 */
public class Word implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
