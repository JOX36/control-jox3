package com.jox3.remotetv;

import android.util.Base64;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SamsungController {

    private final String ip;
    private final int port;
    private static final String APP_STRING = "JOX3Remote";
    private static final int TIMEOUT = 3000;

    public SamsungController(String ip) {
        this.ip   = ip;
        this.port = 55000;
    }

    public void sendKey(String keyCode) throws Exception {
        try (Socket socket = new Socket()) {
            socket.connect(new java.net.InetSocketAddress(ip, port), TIMEOUT);
            socket.setSoTimeout(TIMEOUT);

            OutputStream out = socket.getOutputStream();

            String encoded = Base64.encodeToString(APP_STRING.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
            String handshake = buildHandshake(encoded);
            out.write(handshake.getBytes(StandardCharsets.ISO_8859_1));
            out.flush();

            Thread.sleep(300);

            String keyPacket = buildKeyPacket(keyCode);
            out.write(keyPacket.getBytes(StandardCharsets.ISO_8859_1));
            out.flush();
        }
    }

    private String buildHandshake(String encoded) {
        String payload = "\u0065\u0000\u0000\u0000" +
                toSamsungStr("127.0.0.1") +
                toSamsungStr(encoded) +
                toSamsungStr(APP_STRING);
        return "\u0000" + toSamsungStr("iphone..iapp.samsung") + payload;
    }

    private String buildKeyPacket(String key) {
        String keyData = "\u0000\u0000\u0000" + toSamsungStr(key);
        return "\u0000" + toSamsungStr("iphone..iapp.samsung") + "\u0001\u0000" + keyData;
    }

    private String toSamsungStr(String s) {
        int len = s.length();
        char lo = (char)(len & 0xFF);
        char hi = (char)((len >> 8) & 0xFF);
        return "" + lo + hi + s;
    }
}
