package com.jox3.remotetv;

import android.content.SharedPreferences;
import android.util.Log;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LGController {

    private static final String TAG = "LGController";
    private final String ip;
    private final SharedPreferences prefs;

    public LGController(String ip, SharedPreferences prefs) {
        this.ip    = ip;
        this.prefs = prefs;
    }

    public void sendCommand(String uri) throws Exception {
        String clientKey = prefs.getString("lg_client_key", "");
        String wsUrl     = "ws://" + ip + ":3000";

        CountDownLatch latch = new CountDownLatch(1);
        String registerMsg = buildRegisterMsg(clientKey);

        WebSocketClient client = new WebSocketClient(new URI(wsUrl)) {
            boolean registered = false;

            @Override
            public void onOpen(ServerHandshake h) {
                send(registerMsg);
            }

            @Override
            public void onMessage(String msg) {
                if (!registered) {
                    if (msg.contains("client-key")) {
                        String key = extractValue(msg, "client-key");
                        if (key != null && !key.isEmpty()) {
                            prefs.edit().putString("lg_client_key", key).apply();
                        }
                    }
                    registered = true;
                    if (uri.startsWith("KEY:")) {
                        send(buildKeyMsg(uri.substring(4)));
                    } else {
                        send(buildCommandMsg(uri));
                    }
                } else {
                    latch.countDown();
                    close();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                latch.countDown();
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "WS error: " + e.getMessage());
                latch.countDown();
            }
        };

        client.connectBlocking(3, TimeUnit.SECONDS);
        latch.await(5, TimeUnit.SECONDS);
    }

    private String buildRegisterMsg(String clientKey) {
        String keyField = clientKey.isEmpty() ? "" :
                ",\"client-key\":\"" + clientKey + "\"";
        return "{\"type\":\"register\",\"id\":\"reg0\",\"payload\":{" +
               "\"forcePairing\":false,\"pairingType\":\"PROMPT\"" +
               keyField + "}}";
    }

    private String buildCommandMsg(String uri) {
        return "{\"type\":\"request\",\"id\":\"cmd1\",\"uri\":\"" + uri + "\"}";
    }

    private String buildKeyMsg(String key) {
        return "{\"type\":\"button\",\"name\":\"" + key + "\"}";
    }

    private String extractValue(String json, String key) {
        try {
            int idx = json.indexOf("\"" + key + "\":");
            if (idx < 0) return null;
            int start = json.indexOf("\"", idx + key.length() + 3) + 1;
            int end   = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) { return null; }
    }
}
