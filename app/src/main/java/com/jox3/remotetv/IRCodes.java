package com.jox3.remotetv;

import java.util.HashMap;
import java.util.Map;

public class IRCodes {

    private static final Map<String, int[]> SAMSUNG = new HashMap<>();
    private static final Map<String, int[]> LG_IR   = new HashMap<>();

    static {
        SAMSUNG.put("POWER",    nec(0xE0E040BF));
        SAMSUNG.put("VOL_UP",   nec(0xE0E0E01F));
        SAMSUNG.put("VOL_DOWN", nec(0xE0E0D02F));
        SAMSUNG.put("MUTE",     nec(0xE0E0F00F));
        SAMSUNG.put("CH_UP",    nec(0xE0E048B7));
        SAMSUNG.put("CH_DOWN",  nec(0xE0E008F7));
        SAMSUNG.put("HOME",     nec(0xE0E09E61));
        SAMSUNG.put("BACK",     nec(0xE0E01AE5));
        SAMSUNG.put("UP",       nec(0xE0E006F9));
        SAMSUNG.put("DOWN",     nec(0xE0E08679));
        SAMSUNG.put("LEFT",     nec(0xE0E0A659));
        SAMSUNG.put("RIGHT",    nec(0xE0E046B9));
        SAMSUNG.put("OK",       nec(0xE0E016E9));
        SAMSUNG.put("RED",      nec(0xE0E036C9));
        SAMSUNG.put("GREEN",    nec(0xE0E028D7));
        SAMSUNG.put("YELLOW",   nec(0xE0E0A857));
        SAMSUNG.put("BLUE",     nec(0xE0E06897));
        SAMSUNG.put("1",        nec(0xE0E020DF));
        SAMSUNG.put("2",        nec(0xE0E0A05F));
        SAMSUNG.put("3",        nec(0xE0E0609F));
        SAMSUNG.put("4",        nec(0xE0E010EF));
        SAMSUNG.put("5",        nec(0xE0E0906F));
        SAMSUNG.put("6",        nec(0xE0E050AF));
        SAMSUNG.put("7",        nec(0xE0E030CF));
        SAMSUNG.put("8",        nec(0xE0E0B04F));
        SAMSUNG.put("9",        nec(0xE0E0708F));
        SAMSUNG.put("0",        nec(0xE0E08877));

        LG_IR.put("POWER",    nec(0x20DF10EF));
        LG_IR.put("VOL_UP",   nec(0x20DF40BF));
        LG_IR.put("VOL_DOWN", nec(0x20DFC03F));
        LG_IR.put("MUTE",     nec(0x20DF906F));
        LG_IR.put("CH_UP",    nec(0x20DF00FF));
        LG_IR.put("CH_DOWN",  nec(0x20DF807F));
        LG_IR.put("HOME",     nec(0x20DFC23D));
        LG_IR.put("BACK",     nec(0x20DF14EB));
        LG_IR.put("UP",       nec(0x20DF02FD));
        LG_IR.put("DOWN",     nec(0x20DF827D));
        LG_IR.put("LEFT",     nec(0x20DFE01F));
        LG_IR.put("RIGHT",    nec(0x20DF609F));
        LG_IR.put("OK",       nec(0x20DF22DD));
        LG_IR.put("RED",      nec(0x20DF4EB1));
        LG_IR.put("GREEN",    nec(0x20DF8E71));
        LG_IR.put("YELLOW",   nec(0x20DFC43B));
        LG_IR.put("BLUE",     nec(0x20DF8679));
        LG_IR.put("1",        nec(0x20DF8877));
        LG_IR.put("2",        nec(0x20DF48B7));
        LG_IR.put("3",        nec(0x20DFC837));
        LG_IR.put("4",        nec(0x20DF28D7));
        LG_IR.put("5",        nec(0x20DFA857));
        LG_IR.put("6",        nec(0x20DF6897));
        LG_IR.put("7",        nec(0x20DFE817));
        LG_IR.put("8",        nec(0x20DF18E7));
        LG_IR.put("9",        nec(0x20DF9867));
        LG_IR.put("0",        nec(0x20DF08F7));
    }

    public static int[] getSamsung(String cmd) { return SAMSUNG.get(cmd); }
    public static int[] getLG(String cmd)      { return LG_IR.get(cmd); }

    private static int[] nec(long code) {
        int[] pattern = new int[2 + 32 * 2 + 2];
        int i = 0;
        pattern[i++] = 9000;
        pattern[i++] = 4500;
        for (int bit = 31; bit >= 0; bit--) {
            pattern[i++] = 562;
            pattern[i++] = ((code >> bit) & 1) == 1 ? 1687 : 562;
        }
        pattern[i] = 562;
        return pattern;
    }
}
