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

    @Query("SELECT word, pos.name as pos, voice, pos.means as means " +
            "  FROM words, pos, means " +
            "  WHERE pos.ID = words.ID AND words.ID = means.wordID AND " +
            "  word = :word")
    List<Word> getWordsByName(String word);

    @Query("SELECT word, pos.name as pos, voice, pos.means as means " +
            "  FROM words, pos, means " +
            "  WHERE pos.ID = words.ID AND words.ID = means.wordID AND " +
            "  words.ID =:ID")
    List<Word> getWordsByID(int ID);

}
