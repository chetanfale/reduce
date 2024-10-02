package com.chetan2024.reduce;

import android.app.AppOpsManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PermReqActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_USAGE_ACCESS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permreq);

        Button requestPermissionButton = findViewById(R.id.requestPermissionButton);
        requestPermissionButton.setOnClickListener(v -> {
            // Redirect to Usage Access Settings
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivityForResult(intent, REQUEST_CODE_USAGE_ACCESS);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_USAGE_ACCESS) {
            // Check if the permission was granted
            if (isUsageAccessGranted()) {
                // Permission granted, redirect to SelectAppsActivity
                Intent intent = new Intent(PermReqActivity.this, SelectAppsActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            } else {
                // Permission not granted, show a message or handle accordingly
                // You can show a Toast message or an alert dialog
            }
        }
    }

    private boolean isUsageAccessGranted() {
        // Logic to check if usage access is granted
        AppOpsManager appOps = (AppOpsManager) getSystemService(APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }
}
