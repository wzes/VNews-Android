package com.mobile.vnews;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mobile.vnews.module.dao.WordDao;
import com.mobile.vnews.module.database.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 * Created by xuantang on 12/6/17.
 */
@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private WordDao mWordDao;
    private AppDatabase mDb;
    private Context context;

    private static final String TAG = SimpleEntityReadWriteTest.class.getSimpleName();

    @Before
    public void createDb() {
        context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mWordDao = mDb.getWordDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testDB() throws Exception {
        System.out.println("--------------------------------");
        List<WordDao.Word> words = mWordDao.getWordsByName("take");
        System.out.println(words.size());
        for (WordDao.Word word : words) {
            Log.i(TAG, "onCreate: " + words.size() + "------------" + word.getMeans());
            System.out.println(word.getMeans());
        }
    }
}
