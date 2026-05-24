package com.jox3.remotetv;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {

    private EditText etSamsungIp, etLgIp, etAndroidTvIp;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        prefs = getSharedPreferences("remote_prefs", MODE_PRIVATE);

        etSamsungIp   = findViewById(R.id.et_samsung_ip);
        etLgIp        = findViewById(R.id.et_lg_ip);
        etAndroidTvIp = findViewById(R.id.et_androidtv_ip);

        etSamsungIp.setText(prefs.getString("samsung_ip", ""));
        etLgIp.setText(prefs.getString("lg_ip", ""));
        etAndroidTvIp.setText(prefs.getString("androidtv_ip", ""));

        findViewById(R.id.btn_save).setOnClickListener(v -> {
            prefs.edit()
                .putString("samsung_ip",   etSamsungIp.getText().toString().trim())
                .putString("lg_ip",        etLgIp.getText().toString().trim())
                .putString("androidtv_ip", etAndroidTvIp.getText().toString().trim())
                .apply();
            Toast.makeText(this, "✓ Guardado", Toast.LENGTH_SHORT).show();
            finish();
        });

        findViewById(R.id.btn_clear_lg_key).setOnClickListener(v -> {
            prefs.edit().remove("lg_client_key").apply();
            Toast.makeText(this, "Clave LG eliminada — empareja de nuevo", Toast.LENGTH_LONG).show();
        });
    }
}
