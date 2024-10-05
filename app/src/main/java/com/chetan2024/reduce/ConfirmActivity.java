package com.chetan2024.reduce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;

public class ConfirmActivity extends AppCompatActivity {

    private LinearLayout selectedAppsLayout;
    private PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        selectedAppsLayout = findViewById(R.id.selectedAppsLayout);
        packageManager = getPackageManager();

        ArrayList<String> selectedApps = getIntent().getStringArrayListExtra("selectedApps");

        if (selectedApps != null && !selectedApps.isEmpty()) {
            for (String packageName : selectedApps) {
                try {
                    ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
                    String appName = packageManager.getApplicationLabel(appInfo).toString();

                    ImageView appIcon = new ImageView(this);
                    appIcon.setImageDrawable(packageManager.getApplicationIcon(appInfo));
                    appIcon.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

                    TextView appText = new TextView(this);
                    appText.setText(appName);
                    appText.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    LinearLayout appLayout = new LinearLayout(this);
                    appLayout.setOrientation(LinearLayout.HORIZONTAL);
                    appLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    appLayout.addView(appIcon);
                    appLayout.addView(appText);
                    selectedAppsLayout.addView(appLayout);

                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("ConfirmActivity", "App not found: " + packageName, e);
                }
            }
        } else {
            Log.w("ConfirmActivity", "No apps selected.");
        }

        Button proceedButton = findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(v -> {
            Log.d("ConfirmActivity", "Proceed button clicked");

            // Store selected apps in SharedPreferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ConfirmActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putStringSet("selectedApps", new HashSet<>(selectedApps));
            editor.apply();

            // Proceed to next activity
            startActivity(new Intent(ConfirmActivity.this, RedirectDWActivity.class));
        });
    }
}
