package com.mobile.vnews.module.parse;

import com.mobile.vnews.module.bean.Word;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuantang on 12/7/17.
 */

public class WordParser {

    /**
     * covert to one word
     * @param words
     * @return
     */
    public Word parserList(List<Word> words) {
        if (words == null) {
            throw new NullPointerException();
        }
        // create
        Word mWord = new Word();
        List<Word> wordList = new ArrayList<>();
        mWord.setID(wordList.get(0).getID());
        mWord.setWord(wordList.get(0).getWord());
        // posList
        List<Word.Pos> posList = new ArrayList<>();
        List<Word.Exchange> exchangeList = new ArrayList<>();
        // init
        for (Word word : wordList) {
            // get pos
            Word.Pos pos = mWord.new Pos();
            pos.setSymbol(word.getPos());
            pos.setName(word.getPosmeans());
            pos.setMeans(WordParser.meansConvert(word.getMeans()));
            posList.add(pos);
            // get voice
            Word.Exchange exchange = mWord.new Exchange();
            // {"word_third": ["loves"], "word_done": ["loved"], "word_pl": ["loves"], "word_est": "",
            // "word_ing": ["loving"], "word_er": "", "word_past": ["loved"]}
            //

        }

        return mWord;
    }

    public List<Word.Exchange> exchangeConvert(Word mWord, String exchange) {
        Word.Exchange tmp = mWord.new Exchange();
        try {
            JSONObject jsonObject = new JSONObject(exchange);
            String wordThird = jsonObject.getString("word_third");
            String wordDone = jsonObject.getString("word_done");
            String wordPl = jsonObject.getString("word_pl");
            String wordIng = jsonObject.getString("word_ing");
            String wordEr = jsonObject.getString("word_er");
            String wordPast = jsonObject.getString("word_past");



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param unicode
     * @return
     */
    public static String meansConvert(String unicode) {
        String[] strings = unicode.replace("\"", "")
                .replace("u", "\\u")
                .split(",");
        StringBuilder sb = new StringBuilder();
        for (String str : strings) {
            String s = unicodeConvertString(str);
            sb.append(s).append(",");
        }
        return sb.toString().substring(0, sb.toString().lastIndexOf(","));
    }


    /**
     * single unicode to string
     */
    private static String unicodeConvertString(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }

        return string.toString();
    }

}
