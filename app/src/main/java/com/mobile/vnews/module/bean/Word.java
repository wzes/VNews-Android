package com.mobile.vnews.module.bean;


import android.arch.persistence.room.Ignore;

import com.mobile.vnews.module.parse.WordParser;

import java.util.List;

/**
 * Created by xuantang on 11/27/17.
 */

public class Word {

    private int id;
    private String word;
    private String pos;
    private String voice;
    private String means;
    private String exchange;
    private String posmeans;

    public Word() {
    }

    public String getPosmeans() {
        return posmeans;
    }

    public void setPosmeans(String posmeans) {
        this.posmeans = posmeans;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        public Exchange(String name, String content) {
            this.name = name;
            this.content = content;
        }


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

        private String name;
        private String phonogram;
        private String voiceUrl;

        public Voice(String name, String phonogram, String voiceUrl) {
            this.name = name;
            this.phonogram = phonogram;
            this.voiceUrl = voiceUrl;
        }

        public String getType() {
            return name;
        }

        public void setType(String type) {
            this.name = type;
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

        public Pos(String symbol, String name, String means) {
            this.symbol = symbol;
            this.name = name;
            this.means = means;
        }

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
