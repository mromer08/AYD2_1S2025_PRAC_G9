package com.usac.ayd2.musicplayer.utils;

public class StringUtil {

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String capitalizeWords(String str) {
        if (str == null || str.isEmpty())
            return str;
        String[] words = str.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(capitalize(word)).append(" ");
        }
        return sb.toString().trim();
    }

}
