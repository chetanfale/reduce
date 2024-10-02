package com.chetan2024.reduce;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ConfirmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        LinearLayout selectedAppsLayout = findViewById(R.id.selectedAppsLayout);
        Button proceedButton = findViewById(R.id.proceedButton);

        // Retrieve selected apps from intent
        ArrayList<String> selectedApps = getIntent().getStringArrayListExtra("SELECTED_APPS");
        PackageManager packageManager = getPackageManager();

        // Loop through selected apps to display their icons and package names
        if (selectedApps != null) {
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
                    appText.setText(appName + " (" + packageName + ")");
                    appText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    // Add the icon and name to the layout
                    LinearLayout appLayout = new LinearLayout(this);
                    appLayout.setOrientation(LinearLayout.HORIZONTAL);
                    appLayout.addView(appIcon);
                    appLayout.addView(appText);
                    selectedAppsLayout.addView(appLayout);

                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("ConfirmActivity", "App not found: " + packageName, e);
                }
            }
        }

        // Set up the proceed button
        proceedButton.setOnClickListener(v -> {
            // Add your logic to proceed further here
            // For example, starting another activity or displaying a message
        });
    }
}
