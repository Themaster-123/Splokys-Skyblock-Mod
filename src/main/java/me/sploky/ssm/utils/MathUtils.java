package me.sploky.ssm.utils;

import java.util.Dictionary;
import java.util.HashMap;

public class MathUtils {
    private static final HashMap<Character, Integer> romanToValue = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);

    }};

    public static int romanToNumber(String str) {
        int value = 0;

        for (int i = 0; i < str.length(); i++) {
            int charValue = romanToValue.get(str.charAt(i));

            if (i + 1 == str.length() || charValue >= romanToValue.get(str.charAt(i + 1))) {
                value += charValue;
            } else {
                value -= charValue;
            }
        }

        return value;
    }
}
