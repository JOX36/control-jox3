package com.jox3.remotetv;

import java.util.HashMap;
import java.util.Map;

public class AndroidTVKeys {

    private static final Map<String, String> KEYS = new HashMap<>();

    static {
        KEYS.put("POWER",    "26");
        KEYS.put("VOL_UP",   "24");
        KEYS.put("VOL_DOWN", "25");
        KEYS.put("MUTE",     "164");
        KEYS.put("CH_UP",    "166");
        KEYS.put("CH_DOWN",  "167");
        KEYS.put("HOME",     "3");
        KEYS.put("BACK",     "4");
        KEYS.put("UP",       "19");
        KEYS.put("DOWN",     "20");
        KEYS.put("LEFT",     "21");
        KEYS.put("RIGHT",    "22");
        KEYS.put("OK",       "23");
        KEYS.put("RED",      "183");
        KEYS.put("GREEN",    "184");
        KEYS.put("YELLOW",   "185");
        KEYS.put("BLUE",     "186");
        KEYS.put("1","8");  KEYS.put("2","9");  KEYS.put("3","10");
        KEYS.put("4","11"); KEYS.put("5","12"); KEYS.put("6","13");
        KEYS.put("7","14"); KEYS.put("8","15"); KEYS.put("9","16");
        KEYS.put("0","7");
    }

    public static String get(String cmd) { return KEYS.get(cmd); }
}
