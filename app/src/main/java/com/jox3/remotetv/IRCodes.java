package com.jox3.remotetv;

import java.util.HashMap;
import java.util.Map;

public class IRCodes {

    private static final Map<String, int[]> SAMSUNG = new HashMap<>();
    private static final Map<String, int[]> LG_IR   = new HashMap<>();

    static {
        SAMSUNG.put("POWER",    nec(0xE0E040BFL));
        SAMSUNG.put("VOL_UP",   nec(0xE0E0E01FL));
        SAMSUNG.put("VOL_DOWN", nec(0xE0E0D02FL));
        SAMSUNG.put("MUTE",     nec(0xE0E0F00FL));
        SAMSUNG.put("CH_UP",    nec(0xE0E048B7L));
        SAMSUNG.put("CH_DOWN",  nec(0xE0E008F7L));
        SAMSUNG.put("HOME",     nec(0xE0E09E61L));
        SAMSUNG.put("BACK",     nec(0xE0E01AE5L));
        SAMSUNG.put("UP",       nec(0xE0E006F9L));
        SAMSUNG.put("DOWN",     nec(0xE0E08679L));
        SAMSUNG.put("LEFT",     nec(0xE0E0A659L));
        SAMSUNG.put("RIGHT",    nec(0xE0E046B9L));
        SAMSUNG.put("OK",       nec(0xE0E016E9L));
        SAMSUNG.put("RED",      nec(0xE0E036C9L));
        SAMSUNG.put("GREEN",    nec(0xE0E028D7L));
        SAMSUNG.put("YELLOW",   nec(0xE0E0A857L));
        SAMSUNG.put("BLUE",     nec(0xE0E06897L));
        SAMSUNG.put("1",        nec(0xE0E020DFL));
        SAMSUNG.put("2",        nec(0xE0E0A05FL));
        SAMSUNG.put("3",        nec(0xE0E0609FL));
        SAMSUNG.put("4",        nec(0xE0E010EFL));
        SAMSUNG.put("5",        nec(0xE0E0906FL));
        SAMSUNG.put("6",        nec(0xE0E050AFL));
        SAMSUNG.put("7",        nec(0xE0E030CFL));
        SAMSUNG.put("8",        nec(0xE0E0B04FL));
        SAMSUNG.put("9",        nec(0xE0E0708FL));
        SAMSUNG.put("0",        nec(0xE0E08877L));

        LG_IR.put("POWER",    nec(0x20DF10EFL));
        LG_IR.put("VOL_UP",   nec(0x20DF40BFL));
        LG_IR.put("VOL_DOWN", nec(0x20DFC03FL));
        LG_IR.put("MUTE",     nec(0x20DF906FL));
        LG_IR.put("CH_UP",    nec(0x20DF00FFL));
        LG_IR.put("CH_DOWN",  nec(0x20DF807FL));
        LG_IR.put("HOME",     nec(0x20DFC23DL));
        LG_IR.put("BACK",     nec(0x20DF14EBL));
        LG_IR.put("UP",       nec(0x20DF02FDL));
        LG_IR.put("DOWN",     nec(0x20DF827DL));
        LG_IR.put("LEFT",     nec(0x20DFE01FL));
        LG_IR.put("RIGHT",    nec(0x20DF609FL));
        LG_IR.put("OK",       nec(0x20DF22DDL));
        LG_IR.put("RED",      nec(0x20DF4EB1L));
        LG_IR.put("GREEN",    nec(0x20DF8E71L));
        LG_IR.put("YELLOW",   nec(0x20DFC43BL));
        LG_IR.put("BLUE",     nec(0x20DF8679L));
        LG_IR.put("1",        nec(0x20DF8877L));
        LG_IR.put("2",        nec(0x20DF48B7L));
        LG_IR.put("3",        nec(0x20DFC837L));
        LG_IR.put("4",        nec(0x20DF28D7L));
        LG_IR.put("5",        nec(0x20DFA857L));
        LG_IR.put("6",        nec(0x20DF6897L));
        LG_IR.put("7",        nec(0x20DFE817L));
        LG_IR.put("8",        nec(0x20DF18E7L));
        LG_IR.put("9",        nec(0x20DF9867L));
        LG_IR.put("0",        nec(0x20DF08F7L));
    }

    public static int[] getSamsung(String cmd) { return SAMSUNG.get(cmd); }
    public static int[] getLG(String cmd)      { return LG_IR.get(cmd); }

    private static int[] nec(long code) {
        // Total: 1 header ON + 1 header OFF + 32*(1 ON + 1 OFF) + 1 stop = 67
        int[] pattern = new int[67];
        int i = 0;

        // Header
        pattern[i++] = 9000;  // 9ms burst
        pattern[i++] = 4500;  // 4.5ms space

        // 32 bits MSB first
        for (int bit = 31; bit >= 0; bit--) {
            pattern[i++] = 562; // burst siempre 562µs
            if (((code >> bit) & 1L) == 1L) {
                pattern[i++] = 1688; // 1 = espacio largo
            } else {
                pattern[i++] = 562;  // 0 = espacio corto
            }
        }

        // Stop bit
        pattern[i] = 562;

        return pattern;
    }
}
