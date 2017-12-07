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

    private String ID;
    private String word;
    private String pos;
    private String voice;
    private String means;
    private String exchange;
    private String posmeans;
    public String getPosmeans() {
        return posmeans;
    }

    public void setPosmeans(String posmeans) {
        this.posmeans = posmeans;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }



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
        return means;
    }

    public void setMeans(String means) {
        this.means = means;
    }

    public void setExchanges(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    @Ignore
    private List<Voice> voiceList;

    @Ignore
    private List<Pos> posList;

    @Ignore
    private List<Exchange> exchanges;

    public List<Voice> getVoiceList() {
        return voiceList;
    }

    public void setVoiceList(List<Voice> voiceList) {
        this.voiceList = voiceList;
    }

    public List<Pos> getPosList() {
        return posList;
    }

    public void setPosList(List<Pos> posList) {
        this.posList = posList;
    }

    public List<Exchange> getExchanges() {
        return exchanges;
    }

    public class Exchange {
        private String name;
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * voice of word
     *
     */

    public class Voice {

        private int type;
        private String phonogram;
        private String voiceUrl;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPhonogram() {
            return phonogram;
        }

        public void setPhonogram(String phonogram) {
            this.phonogram = phonogram;
        }

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public void setVoiceUrl(String voiceUrl) {
            this.voiceUrl = voiceUrl;
        }
    }

    /**
     * pos of speech
     */
    public class Pos {

        private String symbol;
        private String name;
        private String means;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMeans() {
            return means;
        }

        public void setMeans(String means) {
            this.means = means;
        }
    }

}
