package com.jox3.remotetv;

import java.util.HashMap;
import java.util.Map;

public class IRCodes {

    private static final Map<String, int[]> SAMSUNG = new HashMap<>();
    private static final Map<String, int[]> LG_IR   = new HashMap<>();

    static {
        // Samsung Serie J - protocolo Samsung BN59
        SAMSUNG.put("POWER",    samsung(0xE0E040BF));
        SAMSUNG.put("VOL_UP",   samsung(0xE0E0E01F));
        SAMSUNG.put("VOL_DOWN", samsung(0xE0E0D02F));
        SAMSUNG.put("MUTE",     samsung(0xE0E0F00F));
        SAMSUNG.put("CH_UP",    samsung(0xE0E048B7));
        SAMSUNG.put("CH_DOWN",  samsung(0xE0E008F7));
        SAMSUNG.put("HOME",     samsung(0xE0E09E61));
        SAMSUNG.put("BACK",     samsung(0xE0E01AE5));
        SAMSUNG.put("UP",       samsung(0xE0E006F9));
        SAMSUNG.put("DOWN",     samsung(0xE0E08679));
        SAMSUNG.put("LEFT",     samsung(0xE0E0A659));
        SAMSUNG.put("RIGHT",    samsung(0xE0E046B9));
        SAMSUNG.put("OK",       samsung(0xE0E016E9));
        SAMSUNG.put("RED",      samsung(0xE0E036C9));
        SAMSUNG.put("GREEN",    samsung(0xE0E028D7));
        SAMSUNG.put("YELLOW",   samsung(0xE0E0A857));
        SAMSUNG.put("BLUE",     samsung(0xE0E06897));
        SAMSUNG.put("1",        samsung(0xE0E020DF));
        SAMSUNG.put("2",        samsung(0xE0E0A05F));
        SAMSUNG.put("3",        samsung(0xE0E0609F));
        SAMSUNG.put("4",        samsung(0xE0E010EF));
        SAMSUNG.put("5",        samsung(0xE0E0906F));
        SAMSUNG.put("6",        samsung(0xE0E050AF));
        SAMSUNG.put("7",        samsung(0xE0E030CF));
        SAMSUNG.put("8",        samsung(0xE0E0B04F));
        SAMSUNG.put("9",        samsung(0xE0E0708F));
        SAMSUNG.put("0",        samsung(0xE0E08877));

        LG_IR.put("POWER",    samsung(0x20DF10EF));
        LG_IR.put("VOL_UP",   samsung(0x20DF40BF));
        LG_IR.put("VOL_DOWN", samsung(0x20DFC03F));
        LG_IR.put("MUTE",     samsung(0x20DF906F));
        LG_IR.put("CH_UP",    samsung(0x20DF00FF));
        LG_IR.put("CH_DOWN",  samsung(0x20DF807F));
        LG_IR.put("HOME",     samsung(0x20DFC23D));
        LG_IR.put("BACK",     samsung(0x20DF14EB));
        LG_IR.put("UP",       samsung(0x20DF02FD));
        LG_IR.put("DOWN",     samsung(0x20DF827D));
        LG_IR.put("LEFT",     samsung(0x20DFE01F));
        LG_IR.put("RIGHT",    samsung(0x20DF609F));
        LG_IR.put("OK",       samsung(0x20DF22DD));
        LG_IR.put("RED",      samsung(0x20DF4EB1));
        LG_IR.put("GREEN",    samsung(0x20DF8E71));
        LG_IR.put("YELLOW",   samsung(0x20DFC43B));
        LG_IR.put("BLUE",     samsung(0x20DF8679));
        LG_IR.put("1",        samsung(0x20DF8877));
        LG_IR.put("2",        samsung(0x20DF48B7));
        LG_IR.put("3",        samsung(0x20DFC837));
        LG_IR.put("4",        samsung(0x20DF28D7));
        LG_IR.put("5",        samsung(0x20DFA857));
        LG_IR.put("6",        samsung(0x20DF6897));
        LG_IR.put("7",        samsung(0x20DFE817));
        LG_IR.put("8",        samsung(0x20DF18E7));
        LG_IR.put("9",        samsung(0x20DF9867));
        LG_IR.put("0",        samsung(0x20DF08F7));
    }

    public static int[] getSamsung(String cmd) { return SAMSUNG.get(cmd); }
    public static int[] getLG(String cmd)      { return LG_IR.get(cmd); }

    /**
     * Protocolo Samsung estándar
     * Frecuencia: 38000 Hz
     * Header: 4500 ON + 4500 OFF
     * Bit 1: 560 ON + 1690 OFF
     * Bit 0: 560 ON + 560 OFF
     * Stop: 560 ON
     */
    private static int[] samsung(long code) {
        int[] p = new int[2 + 32 * 2 + 1];
        int i = 0;

        // Header
        p[i++] = 4500;
        p[i++] = 4500;

        // 32 bits MSB first
        for (int bit = 31; bit >= 0; bit--) {
            p[i++] = 560;
            p[i++] = ((code >> bit) & 1L) == 1L ? 1690 : 560;
        }

        // Stop
        p[i] = 560;

        return p;
    }
}
