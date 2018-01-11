package com.mobile.vnews.module.bean;

import android.arch.persistence.room.Entity;

/**
 * Created by xuantang on 11/27/17.
 */
@Entity(tableName = "message", primaryKeys = { "id"})
public class Message {
    /**
     * ID        INT AUTO_INCREMENT
     * PRIMARY KEY,
     * newsID    INT                                 NULL,
     * fromID    VARCHAR(20)                         NULL,
     * toID      VARCHAR(20)                         NULL,
     * content   TEXT                                NOT NULL,
     * timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
     */
    private int id;
    private String newsID;
    private String fromID;
    private String toID;
    private String content;
    private long timestamp;
    private String userID;

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String floor;
    /**
     *  add after
     */
    private String title;
    private String toUsername;
    private String fromImage;
    private String fromUsername;

//    /**
//     * Relation user_image for submitting
//     */
//    private String relationID;
//
//    public String getRelationID() {
//        return relationID;
//    }
//
//    public void setRelationID(String relationID) {
//        this.relationID = relationID;
//    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFromImage() {
        return fromImage;
    }

    public void setFromImage(String fromImage) {
        this.fromImage = fromImage;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    /* --------------------------------------------- */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
