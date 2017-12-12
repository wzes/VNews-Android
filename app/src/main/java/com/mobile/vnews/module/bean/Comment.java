package com.mobile.vnews.module.bean;

import java.security.Timestamp;

/**
 * Created by xuantang on 11/27/17.
 */

public class Comment {

    /**
     * ID        INT AUTO_INCREMENT
     * PRIMARY KEY,
     * fromID    VARCHAR(20)                         NULL,
     * toID      VARCHAR(20)                         NULL,
     * content   TEXT                                NOT NULL,
     * timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     * newsID    INT
     */
    private int ID;
    private int floor;
    private String fromID;
    private String toID;
    private String content;
    private Timestamp timestamp;
    private int newID;

    /*---------------------------------------------------------------*/
    /**
     * after
     */
    private int likeCount;
    private String fromImage;
    private String fromUsername;
    private String toImage;
    private String toUsername;

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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

    public String getToImage() {
        return toImage;
    }

    public void setToImage(String toImage) {
        this.toImage = toImage;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }
    /*---------------------------------------------------------------*/

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getNewID() {
        return newID;
    }

    public void setNewID(int newID) {
        this.newID = newID;
    }
}
