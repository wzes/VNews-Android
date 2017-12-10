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
