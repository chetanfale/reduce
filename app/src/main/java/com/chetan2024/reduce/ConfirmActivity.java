package com.chetan2024.reduce;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ConfirmActivity extends AppCompatActivity {

    private LinearLayout selectedAppsLayout;
    private PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        selectedAppsLayout = findViewById(R.id.selectedAppsLayout);
        packageManager = getPackageManager();

        // Get the selected apps from the intent
        ArrayList<String> selectedApps = getIntent().getStringArrayListExtra("selectedApps");

        // Populate the selected apps
        if (selectedApps != null && !selectedApps.isEmpty()) {
            for (String packageName : selectedApps) {
                try {
                    ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
                    String appName = packageManager.getApplicationLabel(appInfo).toString();

                    // Create an ImageView for the app icon
                    ImageView appIcon = new ImageView(this);
                    appIcon.setImageDrawable(packageManager.getApplicationIcon(appInfo));
                    appIcon.setLayoutParams(new LinearLayout.LayoutParams(100, 100)); // Set size for the icon

                    // Create a TextView for the app name
                    TextView appText = new TextView(this);
                    appText.setText(appName);
                    appText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    // Add the icon and name to the layout
                    LinearLayout appLayout = new LinearLayout(this);
                    appLayout.setOrientation(LinearLayout.HORIZONTAL);
                    appLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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

        // Proceed button logic
        Button proceedButton = findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the proceed action here
                Log.d("ConfirmActivity", "Proceed button clicked");
                // Intent intent = new Intent(ConfirmActivity.this, NextActivity.class);
                // startActivity(intent);
            }
        });
    }
}
