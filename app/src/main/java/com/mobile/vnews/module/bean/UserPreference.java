package com.mobile.vnews.module.bean;

import java.util.List;

/**
 * Created by xuantang on 11/27/17.
 */

public class UserPreference {

    private String ID;
    private List<String> preferences;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
}
