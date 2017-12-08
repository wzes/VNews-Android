package com.mobile.vnews;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by xuantang on 12/8/17.
 */

public class ExchangeTest {

    @Test
    public void convert() {
        String str = "{\"word_third\": [\"loves\"], \"word_done\": [\"loved\"], \"word_pl\": " +
                "[\"loves\"], \"word_est\": \"\", \"word_ing\": [\"loving\"], \"word_er\": \"\", " +
                "\"word_past\": [\"loved\"]}";

        // get the data
        try {
            JSONObject jsonObject = new JSONObject(str);
            String wordThird = jsonObject.getString("word_third");
            String wordDone = jsonObject.getString("word_done");
            String wordPl = jsonObject.getString("word_pl");
            String wordIng = jsonObject.getString("word_ing");
            String wordEr = jsonObject.getString("word_er");
            String wordPast = jsonObject.getString("word_past");

            System.out.println(wordDone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
