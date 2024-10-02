package com.chetan2024.reduce;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SelectAppsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectapps);

        LinearLayout appsListLayout = findViewById(R.id.appsListLayout);
        PackageManager packageManager = getPackageManager();

        // Fetching installed applications
        List<ApplicationInfo> appsList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        // Clear previous views if any
        appsListLayout.removeAllViews();

        // Loop through the installed apps
        for (ApplicationInfo appInfo : appsList) {
            // Log the app name and package for debugging
            Log.d("AppInfo", "App: " + appInfo.loadLabel(packageManager) + " Package: " + appInfo.packageName);

            // Create a CheckBox for each app
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(appInfo.loadLabel(packageManager)); // Set app name
            checkBox.setTag(appInfo.packageName); // Store the package name as a tag

            // Add the CheckBox to the layout
            appsListLayout.addView(checkBox);
        }
    }
}
