package com.mobile.vnews.module.bean;

import android.arch.persistence.room.Entity;

/**
 * Created by xuantang on 12/6/17.
 */
@Entity(tableName = "means", primaryKeys = { "wordID", "posID" })
public class Means {
    private int wordID;
    private int posID;
    private String means;

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    public int getPosID() {
        return posID;
    }

    public void setPosID(int posID) {
        this.posID = posID;
    }

    public String getMeans() {
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }
}
