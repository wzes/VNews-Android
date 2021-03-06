package com.mobile.vnews.module.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mobile.vnews.module.bean.Means;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.Pos;
import com.mobile.vnews.module.bean.User;
import com.mobile.vnews.module.bean.WordCollect;
import com.mobile.vnews.module.bean.Words;
import com.mobile.vnews.module.dao.MessageDao;
import com.mobile.vnews.module.dao.WordDao;

@Database( entities = { Means.class, Words.class, Pos.class, Message.class, WordCollect.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public static AppDatabase getDatabase(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "word.db").build();
        }
        return sInstance;
    }

    public static void onDestroy() {
        sInstance = null;
    }

    public abstract WordDao getWordDao();
    public abstract MessageDao getMessageDao();
}
