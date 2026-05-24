package com.jox3.remotetv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "JOX3Remote";
    private static final String PREFS = "remote_prefs";

    private String currentMode = "IR";

    private ConsumerIrManager irManager;
    private boolean hasIR = false;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private SharedPreferences prefs;

    private String samsungIp = "";
    private String lgIp = "";
    private String androidTvIp = "";

    private TextView tvMode;
    private TextView tvStatus;
    private LinearLayout btnPower, btnVolUp, btnVolDown, btnMute;
    private LinearLayout btnChUp, btnChDown, btnHome, btnBack;
    private LinearLayout btnUp, btnDown, btnLeft, btnRight, btnOk;
    private LinearLayout btnRed, btnGreen, btnYellow, btnBlue;
    private LinearLayout btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    private RadioGroup rgMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        loadConfig();
        initIR();
        initViews();
        bindButtons();
        updateModeUI();
    }

    private void loadConfig() {
        samsungIp   = prefs.getString("samsung_ip", "");
        lgIp        = prefs.getString("lg_ip", "");
        androidTvIp = prefs.getString("androidtv_ip", "");
        currentMode = prefs.getString("last_mode", "IR");
    }

    private void initIR() {
        irManager = (ConsumerIrManager) getSystemService(Context.CONSUMER_IR_SERVICE);
        hasIR = (irManager != null && irManager.hasIrEmitter());
    }

    private void initViews() {
        tvMode   = findViewById(R.id.tv_mode);
        tvStatus = findViewById(R.id.tv_status);
        rgMode   = findViewById(R.id.rg_mode);

        btnPower   = findViewById(R.id.btn_power);
        btnVolUp   = findViewById(R.id.btn_vol_up);
        btnVolDown = findViewById(R.id.btn_vol_down);
        btnMute    = findViewById(R.id.btn_mute);
        btnChUp    = findViewById(R.id.btn_ch_up);
        btnChDown  = findViewById(R.id.btn_ch_down);
        btnHome    = findViewById(R.id.btn_home);
        btnBack    = findViewById(R.id.btn_back);
        btnUp      = findViewById(R.id.btn_up);
        btnDown    = findViewById(R.id.btn_down);
        btnLeft    = findViewById(R.id.btn_left);
        btnRight   = findViewById(R.id.btn_right);
        btnOk      = findViewById(R.id.btn_ok);
        btnRed     = findViewById(R.id.btn_red);
        btnGreen   = findViewById(R.id.btn_green);
        btnYellow  = findViewById(R.id.btn_yellow);
        btnBlue    = findViewById(R.id.btn_blue);
        btn1 = findViewById(R.id.btn_1); btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3); btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5); btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7); btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9); btn0 = findViewById(R.id.btn_0);

        rgMode.setOnCheckedChangeListener((group, checkedId) -> {
            if      (checkedId == R.id.rb_ir)        currentMode = "IR";
            else if (checkedId == R.id.rb_samsung)   currentMode = "SAMSUNG";
            else if (checkedId == R.id.rb_lg)        currentMode = "LG";
            else if (checkedId == R.id.rb_androidtv) currentMode = "ANDROIDTV";
            prefs.edit().putString("last_mode", currentMode).apply();
            updateModeUI();
        });

        findViewById(R.id.btn_setup).setOnClickListener(v ->
            startActivity(new Intent(this, SetupActivity.class)));
    }

    private void updateModeUI() {
        tvMode.setText("Modo: " + currentMode);
        boolean irOk = hasIR || !currentMode.equals("IR");
        tvStatus.setText(irOk ? "✓ Listo" : "⚠ Sin IR blaster");
        tvStatus.setTextColor(irOk ? 0xFF4CAF50 : 0xFFFF9800);
    }

    private void bindButtons() {
        btnPower.setOnClickListener(v -> sendCommand("POWER"));
        btnVolUp.setOnClickListener(v -> sendCommand("VOL_UP"));
        btnVolDown.setOnClickListener(v -> sendCommand("VOL_DOWN"));
        btnMute.setOnClickListener(v -> sendCommand("MUTE"));
        btnChUp.setOnClickListener(v -> sendCommand("CH_UP"));
        btnChDown.setOnClickListener(v -> sendCommand("CH_DOWN"));
        btnHome.setOnClickListener(v -> sendCommand("HOME"));
        btnBack.setOnClickListener(v -> sendCommand("BACK"));
        btnUp.setOnClickListener(v -> sendCommand("UP"));
        btnDown.setOnClickListener(v -> sendCommand("DOWN"));
        btnLeft.setOnClickListener(v -> sendCommand("LEFT"));
        btnRight.setOnClickListener(v -> sendCommand("RIGHT"));
        btnOk.setOnClickListener(v -> sendCommand("OK"));
        btnRed.setOnClickListener(v -> sendCommand("RED"));
        btnGreen.setOnClickListener(v -> sendCommand("GREEN"));
        btnYellow.setOnClickListener(v -> sendCommand("YELLOW"));
        btnBlue.setOnClickListener(v -> sendCommand("BLUE"));
        btn1.setOnClickListener(v -> sendCommand("1")); btn2.setOnClickListener(v -> sendCommand("2"));
        btn3.setOnClickListener(v -> sendCommand("3")); btn4.setOnClickListener(v -> sendCommand("4"));
        btn5.setOnClickListener(v -> sendCommand("5")); btn6.setOnClickListener(v -> sendCommand("6"));
        btn7.setOnClickListener(v -> sendCommand("7")); btn8.setOnClickListener(v -> sendCommand("8"));
        btn9.setOnClickListener(v -> sendCommand("9")); btn0.setOnClickListener(v -> sendCommand("0"));
    }

    private void sendCommand(String cmd) {
        feedback(cmd);
        switch (currentMode) {
            case "IR":        sendIR(cmd);        break;
            case "SAMSUNG":   sendSamsung(cmd);   break;
            case "LG":        sendLG(cmd);        break;
            case "ANDROIDTV": sendAndroidTV(cmd); break;
        }
    }

    private void feedback(String cmd) {
        tvStatus.setText("▶ " + cmd);
        tvStatus.setTextColor(0xFF03DAC6);
        mainHandler.postDelayed(() -> tvStatus.setText("✓ Listo"), 800);
    }

    private void sendIR(String cmd) {
        if (!hasIR) { toast("Sin IR blaster"); return; }
        int[] pattern = IRCodes.getSamsung(cmd);
        if (pattern == null) { toast("Código IR no disponible"); return; }
        try {
            irManager.transmit(38000, pattern);
        } catch (Exception e) {
            toast("Error IR: " + e.getMessage());
        }
    }

    private void sendSamsung(String cmd) {
        if (samsungIp.isEmpty()) { toast("Configura IP de Samsung"); return; }
        String keyCode = SamsungKeys.get(cmd);
        if (keyCode == null) return;
        executor.execute(() -> {
            try {
                SamsungController sc = new SamsungController(samsungIp);
                sc.sendKey(keyCode);
            } catch (Exception e) {
                mainHandler.post(() -> toast("Samsung: " + e.getMessage()));
            }
        });
    }

    private void sendLG(String cmd) {
        if (lgIp.isEmpty()) { toast("Configura IP de LG"); return; }
        String lgUri = LGKeys.get(cmd);
        if (lgUri == null) return;
        executor.execute(() -> {
            try {
                LGController lg = new LGController(lgIp, prefs);
                lg.sendCommand(lgUri);
            } catch (Exception e) {
                mainHandler.post(() -> toast("LG: " + e.getMessage()));
            }
        });
    }

    private void sendAndroidTV(String cmd) {
        if (androidTvIp.isEmpty()) { toast("Configura IP del TV Box"); return; }
        String adbCmd = AndroidTVKeys.get(cmd);
        if (adbCmd == null) return;
        executor.execute(() -> {
            try {
                AndroidTVController atv = new AndroidTVController(androidTvIp);
                atv.sendKey(adbCmd);
            } catch (Exception e) {
                mainHandler.post(() -> toast("AndroidTV: " + e.getMessage()));
            }
        });
    }

    private void toast(String msg) {
        mainHandler.post(() -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadConfig();
        updateModeUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
