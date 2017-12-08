package com.mobile.vnews.module.bean;

import java.util.Date;

/**
 * Created by xuantang on 11/27/17.
 */

public class News {
    /**
     * ID          INT AUTO_INCREMENT
     * PRIMARY KEY,
     * title       VARCHAR(255) NOT NULL,
     * author      VARCHAR(50)  NULL,
     * description VARCHAR(255) NULL,
     * image       VARCHAR(255) NULL
     * COMMENT 'url',
     * publishedAt DATETIME     NULL,
     * source      VARCHAR(50)  NULL,
     * content     TEXT         NOT NULL,
     * level       VARCHAR(50)  NULL,
     * type
     */
    private int ID;
    private String title;
    private String author;
    private String description;
    private String image;
    private Date publishedAt;
    private String source;
    private String content;
    private String level;
    private String type;

    private int viewCount;
    private int followCount;
    private int commentCount;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
