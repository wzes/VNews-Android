package com.mobile.vnews.module.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.bean.WordCollect;

import java.util.List;

/**
 * Created by xuantang on 12/6/17.
 */

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message WHERE userID = :userID ORDER BY timestamp DESC")
    List<Message> getMessage(String userID);

    @Query("SELECT count(*) FROM message WHERE userID = :userID")
    int getSize(String userID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMessage(Message message);

    @Delete
    void removeMessage(Message... message);
}