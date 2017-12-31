package com.mobile.vnews.util.wordplayer;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by xuantang on 12/29/17.
 */

public class WordPlayer {
    public static void play(String url) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
