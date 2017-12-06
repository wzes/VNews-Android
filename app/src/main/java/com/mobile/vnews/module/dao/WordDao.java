package com.mobile.vnews.module.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import java.util.List;

/**
 * Created by xuantang on 12/6/17.
 */

@Dao
public interface WordDao {

    @Query("SELECT word, pos.name as pos, voice, pos.means as means " +
            "  FROM words, pos, means " +
            "  WHERE pos.ID = words.ID AND words.ID = means.wordID AND " +
            "  word = :word")
    List<Word> getWordsByName(String word);

    class Word {
        public static final int VOIVE_EN = 0;
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
            return means;
        }

        public void setMeans(String means) {
            this.means = means;
        }

    }
}
