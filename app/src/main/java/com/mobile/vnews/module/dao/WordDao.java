package com.mobile.vnews.module.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;


import com.mobile.vnews.module.bean.Word;

import java.util.List;

/**
 * Created by xuantang on 12/6/17.
 */

@Dao
public interface WordDao {

    @Query("SELECT words.ID as id, word, pos.name as pos, voice, means.means as means, " +
            "pos.means as posmeans, exchange " +
            "FROM words, pos, means " +
            "WHERE word = :word AND pos.ID = means.posID AND words.ID = means.wordID")
    List<Word> getWordsByName(String word);

    @Query("SELECT words.ID as id, word, pos.name as pos, voice, means.means as means, " +
            "pos.means as posmeans , exchange " +
            "FROM words, pos, means " +
            "WHERE words.ID = :ID AND pos.ID = means.posID AND words.ID = means.wordID")
    List<Word> getWordsByID(int ID);

}
