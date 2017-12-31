package com.mobile.vnews;

/**
 * Created by xuantang on 12/30/17.
 */

public class WordLemmaTest {
    public static String get(String word) {
        if (word.endsWith("s")) {
            return word.substring(0, word.length() - 1);
        }
        if (word.endsWith("ing")) {
            if (word.toCharArray()[word.indexOf("ing") - 2] ==
                    word.toCharArray()[word.indexOf("ing") - 1] ) {
                return word.substring(0, word.length() - 4);
            }
            return word.substring(0, word.length() - 3);
        }
        if (word.endsWith("ced")) {
            return word.substring(0, word.length() - 1);
        }
        if (word.endsWith("red")) {
            return word.substring(0, word.length() - 1);
        }
        if (word.endsWith("ted")) {
            return word.substring(0, word.length() - 2);
        }
        if (word.endsWith("ed")) {
            return word.substring(0, word.length() - 2);
        }
        return word;
    }

    public static void main(String[] args) {
        System.out.println(get("boys"));
        System.out.println(get("shopping"));
    }
}
