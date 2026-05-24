package com.jox3.remotetv;

import java.util.HashMap;
import java.util.Map;

public class LGKeys {

    private static final Map<String, String> KEYS = new HashMap<>();

    static {
        KEYS.put("POWER",    "ssap://system/turnOff");
        KEYS.put("VOL_UP",   "ssap://audio/volumeUp");
        KEYS.put("VOL_DOWN", "ssap://audio/volumeDown");
        KEYS.put("MUTE",     "ssap://audio/setMute");
        KEYS.put("CH_UP",    "ssap://tv/channelUp");
        KEYS.put("CH_DOWN",  "ssap://tv/channelDown");
        KEYS.put("HOME",     "ssap://system.launcher/open?id=com.webos.app.home");
        KEYS.put("UP",       "KEY:UP");
        KEYS.put("DOWN",     "KEY:DOWN");
        KEYS.put("LEFT",     "KEY:LEFT");
        KEYS.put("RIGHT",    "KEY:RIGHT");
        KEYS.put("OK",       "KEY:ENTER");
        KEYS.put("BACK",     "KEY:BACK");
        KEYS.put("RED",      "KEY:RED");
        KEYS.put("GREEN",    "KEY:GREEN");
        KEYS.put("YELLOW",   "KEY:YELLOW");
        KEYS.put("BLUE",     "KEY:BLUE");
        KEYS.put("1","KEY:1"); KEYS.put("2","KEY:2"); KEYS.put("3","KEY:3");
        KEYS.put("4","KEY:4"); KEYS.put("5","KEY:5"); KEYS.put("6","KEY:6");
        KEYS.put("7","KEY:7"); KEYS.put("8","KEY:8"); KEYS.put("9","KEY:9");
        KEYS.put("0","KEY:0");
    }

    public static String get(String cmd) { return KEYS.get(cmd); }
}
