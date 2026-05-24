package com.jox3.remotetv;

import android.util.Log;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class AndroidTVController {

    private static final String TAG = "AndroidTVCtrl";
    private final String ip;
    private final int timeout = 4000;

    public AndroidTVController(String ip) {
        this.ip = ip;
    }

    public void sendKey(String keyCode) throws Exception {
        try {
            sendViaRemoteProtocol(keyCode);
            return;
        } catch (Exception e) {
            Log.w(TAG, "Remote protocol falló: " + e.getMessage());
        }
        sendViaHttp(keyCode);
    }

    private void sendViaRemoteProtocol(String keyCode) throws Exception {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, 6466), timeout);
            socket.setSoTimeout(timeout);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            int kc = Integer.parseInt(keyCode);

            out.writeByte(0x01);
            out.writeInt(kc);
            out.writeByte(0x00);
            out.flush();
            Thread.sleep(80);

            out.writeByte(0x01);
            out.writeInt(kc);
            out.writeByte(0x01);
            out.flush();
        }
    }

    private void sendViaHttp(String keyCode) throws Exception {
        String url = "http://" + ip + ":7171/keyevent?code=" + keyCode;
        java.net.URL u = new java.net.URL(url);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) u.openConnection();
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setRequestMethod("GET");
        conn.getResponseCode();
        conn.disconnect();
    }
}
