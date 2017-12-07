package com.mobile.vnews.module.parse;

/**
 * Created by xuantang on 12/7/17.
 */

public class WordParser {

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
