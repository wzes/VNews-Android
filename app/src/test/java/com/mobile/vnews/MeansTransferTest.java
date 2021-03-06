package com.mobile.vnews;

import org.junit.Test;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuantang on 12/7/17.
 */

public class MeansTransferTest {
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
    private static String phonogramConvert(String source) {
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
    @Test
    public void meansToText() {
        String source = "\"u5ba3u5e03uff08 announceu7684u8fc7u53bbu5f0fu548cu8fc7u53bbu5206u8bcd uff09,u901au77e5,u5e7fu64ad,u58f0u79f0\"";

        //System.out.println(phonogramConvert(source));
        System.out.println(convert(source));
    }

    /**
     *
     * @param unicode
     * @return
     */
    public static String convert(String unicode) {
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

            }

        }

        return string.toString();
    }
}
