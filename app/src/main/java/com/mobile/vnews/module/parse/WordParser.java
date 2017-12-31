package com.mobile.vnews.module.parse;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mobile.vnews.module.bean.Word;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;

/**
 * Created by xuantang on 12/7/17.
 */

public class WordParser {
    private static final String TAG = WordParser.class.getSimpleName();
    // phonogramConvert MAP
    private static HashMap<String, String> map = new HashMap<>();
    static {
        map.put("u00e6", "æ");
        map.put("u00f0", "ð");
        map.put("u014b", "ŋ");
        map.put("u0251", "ɑ");
        map.put("u0252", "ɒ");
        map.put("u0254", "ɔ");
        map.put("u0259", "ə");
        map.put("u025b", "ɛ");
        map.put("u025c", "ɜ");
        map.put("u0261", "ɡ");
        map.put("u026a", "ɪ");
        map.put("u0283", "ʃ");
        map.put("u028a", "ʊ");
        map.put("u028c", "ʌ");
        map.put("u0292", "ʒ");
        map.put("u02c8", "ˈ");
        map.put("u02cc", "ˌ");
        map.put("u02d0", "ː");
        map.put("u03b8", "θ");
    }

    /**
     *
     * @param source
     * @return
     */
    private static String phonogramTransfer(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        for (Map.Entry entry : map.entrySet()) {
            int count;
            int pos = 0;
            while ((count = source.indexOf((String) entry.getKey(), pos)) != -1) {
                pos = count + 4;
                source = source.replace(entry.getKey().toString(),
                        entry.getValue().toString());
            }
        }
        return "[" + source + "]";
    }

    /**
     * covert to one word
     * @param wordList
     * @return
     */
    public Word parse(@NonNull List<Word> wordList) {
        if (wordList == null) {
            throw new NullPointerException();
        }
        // create
        Word mWord = new Word();
        mWord.setId(wordList.get(0).getId());
        mWord.setWord(wordList.get(0).getWord());

        // get posList
        List<Word.Pos> posList = posConvert(wordList.get(0), wordList);
        mWord.setPosList(posList);
        // get exchanges
        List<Word.Exchange> exchangeList = exchangeConvert(wordList.get(0),
                wordList.get(0).getExchange());
        mWord.setExchanges(exchangeList);
        // get ph
        List<Word.Voice> voiceList = voiceConvert(wordList.get(0),
                wordList.get(0).getVoice());
        mWord.setVoiceList(voiceList);

        return mWord;
    }

    /**
     * {"ph_en": "lu028cv",
     * "ph_am": "lu028cv",
     * "ph_en_mp3": "http://res.iciba.com/resource/amp3/oxford/0/4f/5b/4f5bbc0f19c33e5f1a0b6b974b4eacce.mp3",
     * "ph_am_mp3": "http://res.iciba.com/resource/amp3/1/0/b5/c0/b5c0b187fe309af0f4d35982fd961d7e.mp3",
     * "ph_other": "",
     * "ph_tts_mp3": "http://res-tts.iciba.com/b/5/c/b5c0b187fe309af0f4d35982fd961d7e.mp3"}
     * @param mWord
     * @param voice
     * @return
     */
    private List<Word.Voice> voiceConvert(Word mWord, String voice) {
        if (mWord == null) {
            throw new NullPointerException();
        }
        List<Word.Voice> voiceList = new ArrayList<>();

        try {
            // parser
            Log.i(TAG, "voiceConvert: " + voice);
            JSONObject jsonObject = new JSONObject(voice);
            String phEn = jsonObject.getString("ph_en");
            String phAm = jsonObject.getString("ph_am");
            String phEnMp3 = jsonObject.getString("ph_en_mp3");
            String phAmMp3 = jsonObject.getString("ph_am_mp3");
            String mPhEn = null;
            String mPhAm = null;
            if (!TextUtils.isEmpty(phEn)) {
                mPhEn = phonogramTransfer(phEn);
            }
            if (!TextUtils.isEmpty(phAm)) {
                mPhAm = phonogramTransfer(phAm);
            }
            voiceList.add(mWord.new Voice("美式", mPhAm, phAmMp3));
            voiceList.add(mWord.new Voice("英式", mPhEn, phEnMp3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return voiceList;
    }

    /**
     * Parser p of speech
     * @param mWord
     * @param wordList
     * @return
     */
    private List<Word.Pos> posConvert(Word mWord, List<Word> wordList) {
        if (mWord == null) {
            throw new NullPointerException();
        }
        List<Word.Pos> posList = new ArrayList<>();
        for (Word word : wordList) {
            // get pos
            try {
                Word.Pos pos = mWord.new Pos(word.getPos(), word.getPosmeans(),
                        WordParser.meansConvert(word.getMeans()));
                posList.add(pos);
                Log.i(TAG, "posConvert: " + JSON.toJSONString(pos));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return posList;
    }

    /**
     *
     * @param mWord
     * @param exchange
     * @return
     */
    private static List<Word.Exchange> exchangeConvert(Word mWord, String exchange) {
        if (mWord == null) {
            throw new NullPointerException();
        }
        List<Word.Exchange> exchangeList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(exchange);
            // get the data
            String wordThird = jsonObject.getString("word_third");
            String wordDone = jsonObject.getString("word_done");
            String wordPl = jsonObject.getString("word_pl");
            String wordIng = jsonObject.getString("word_ing");
            String wordEr = jsonObject.getString("word_er");
            String wordPast = jsonObject.getString("word_past");
            // 过去式
            if (!TextUtils.isEmpty(wordPast)) {
                exchangeList.add(mWord.new Exchange("过去式", wordPast));
            }
            // 复数
            if (!TextUtils.isEmpty(wordPl)) {
                exchangeList.add(mWord.new Exchange("复数", wordPl));
            }
            // 现在分词
            if (!TextUtils.isEmpty(wordIng)) {
                exchangeList.add(mWord.new Exchange("现在分词", wordIng));
            }
            // 过去分词
            if (!TextUtils.isEmpty(wordDone)) {
                exchangeList.add(mWord.new Exchange("过去分词", wordDone));
            }
            // 第三人称单数
            if (!TextUtils.isEmpty(wordThird)) {
                exchangeList.add(mWord.new Exchange("第三人称单数", wordThird));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exchangeList;
    }

    /**
     *
     * @param unicode
     * @return
     */
    private static String meansConvert(String unicode) {
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
            try {
                int data = Integer.parseInt(hex[i], 16);
                string.append((char) data);
            } catch (Exception e) {
                Log.i(TAG, "unicodeConvertString: ");
            }
        }

        return string.toString();
    }

}
