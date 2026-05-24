package com.jox3.remotetv;

import java.util.HashMap;
import java.util.Map;

public class SamsungKeys {

    private static final Map<String, String> KEYS = new HashMap<>();

    static {
        KEYS.put("POWER",    "KEY_POWER");
        KEYS.put("VOL_UP",   "KEY_VOLUP");
        KEYS.put("VOL_DOWN", "KEY_VOLDOWN");
        KEYS.put("MUTE",     "KEY_MUTE");
        KEYS.put("CH_UP",    "KEY_CHUP");
        KEYS.put("CH_DOWN",  "KEY_CHDOWN");
        KEYS.put("HOME",     "KEY_HOME");
        KEYS.put("BACK",     "KEY_RETURN");
        KEYS.put("UP",       "KEY_UP");
        KEYS.put("DOWN",     "KEY_DOWN");
        KEYS.put("LEFT",     "KEY_LEFT");
        KEYS.put("RIGHT",    "KEY_RIGHT");
        KEYS.put("OK",       "KEY_ENTER");
        KEYS.put("RED",      "KEY_RED");
        KEYS.put("GREEN",    "KEY_GREEN");
        KEYS.put("YELLOW",   "KEY_YELLOW");
        KEYS.put("BLUE",     "KEY_CYAN");
        KEYS.put("1", "KEY_1"); KEYS.put("2", "KEY_2"); KEYS.put("3", "KEY_3");
        KEYS.put("4", "KEY_4"); KEYS.put("5", "KEY_5"); KEYS.put("6", "KEY_6");
        KEYS.put("7", "KEY_7"); KEYS.put("8", "KEY_8"); KEYS.put("9", "KEY_9");
        KEYS.put("0", "KEY_0");
    }

    public static String get(String cmd) { return KEYS.get(cmd); }
}
