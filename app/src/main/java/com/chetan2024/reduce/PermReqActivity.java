package com.chetan2024.reduce;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.chetan2024.reduce.SelectAppsActivity;

public class PermReqActivity extends AppCompatActivity {

    private static final int USAGE_ACCESS_PERMISSION_REQUEST_CODE = 1;
    private Button permissionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permreq);

        // Check if Usage Access permission is granted
        if (isUsageAccessGranted()) {
            // If permission is already granted, redirect to SelectAppsActivity
            redirectToSelectAppsActivity();
        }

        permissionButton = findViewById(R.id.requestPermissionButton);
        permissionButton.setOnClickListener(v -> {
            // Redirect to Usage Access settings
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        });
    }

    private boolean isUsageAccessGranted() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private void redirectToSelectAppsActivity() {
        Intent intent = new Intent(PermReqActivity.this, SelectAppsActivity.class);
        startActivity(intent);
        finish(); // Close PermReqActivity so user can't go back to it
    }
}
