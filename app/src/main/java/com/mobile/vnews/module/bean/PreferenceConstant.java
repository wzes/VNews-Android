package com.mobile.vnews.module.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuantang on 12/8/17.
 */

public class PreferenceConstant {
    private static final List<String> preferences = new ArrayList<>();
    private static final Map<String, String> preferencesMap = new HashMap<>();

    static {
        preferences.add("world");
        preferences.add("business");
        preferences.add("lifestyle");
        preferences.add("culture");
        preferences.add("travel");
        preferences.add("sports");
        preferences.add("opinion");
        preferences.add("regional");
        preferences.add("china");
        preferences.add("america");

        preferencesMap.put("world", "世界");
        preferencesMap.put("business", "金融");
        preferencesMap.put("lifestyle", "生活");
        preferencesMap.put("culture", "文化");
        preferencesMap.put("travel", "旅游");
        preferencesMap.put("sports", "运动");
        preferencesMap.put("opinion", "观点");
        preferencesMap.put("regional", "地区");
        preferencesMap.put("china", "中国");
        preferencesMap.put("america", "美国");
    }
}
