package com.mobile.vnews.module.bean;


import android.arch.persistence.room.Ignore;

import com.mobile.vnews.module.parse.WordParser;

import java.util.List;

/**
 * Created by xuantang on 11/27/17.
 */

public class Word {
    @Ignore
    public static final int VOIVE_EN = 0;
    @Ignore
    public static final int VOIVE_AM = 1;

    private String word;
    private String pos;
    private String voice;
    private String means;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getMeans() {
        return WordParser.meansConvert(means);
    }

    public void setMeans(String means) {
        this.means = means;
    }



    @Ignore
    private List<Voice> voiceList;

    @Ignore
    private List<Pos> posList;

    /**
     * voice of word
     *
     */

    private class Voice {
        private int type;
        private String phonogram;
        private String voiceUrl;
    }

    /**
     * pos of speech
     */
    private class Pos {
        private String name;
        private String means;
    }

}
