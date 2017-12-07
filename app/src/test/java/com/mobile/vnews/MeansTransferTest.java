package com.mobile.vnews;

import org.junit.Test;

/**
 * Created by xuantang on 12/7/17.
 */

public class MeansTransferTest {
    @Test
    public void meansToText() {
        String source = "\"u62ffuff0cu53d6,u91c7u53d6,u63a5u53d7uff08u793cu7269u7b49uff09,u8017u8d39uff08u65f6u95f4u7b49uff09\"";

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
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }

        return string.toString();
    }
}
