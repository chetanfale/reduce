package com.chetan2024.reduce;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.view.*;

import java.util.ArrayList;
import java.util.List;

public class SelectAppsActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectapps);

        LinearLayout appsListLayout = findViewById(R.id.appsListLayout);
        PackageManager packageManager = getPackageManager();

        // Check for permissions before fetching installed applications
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.QUERY_ALL_PACKAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.QUERY_ALL_PACKAGES}, REQUEST_CODE_PERMISSIONS);
        } else {
            fetchInstalledApps(appsListLayout, packageManager);
        }

        // Set up the submit button
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> selectedAppPackages = new ArrayList<>();
                for (int i = 0; i < appsListLayout.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) appsListLayout.getChildAt(i);
                    if (checkBox.isChecked()) {
                        selectedAppPackages.add((String) checkBox.getTag());
                    }
                }

                // Pass selected apps to ConfirmActivity
                Intent intent = new Intent(SelectAppsActivity.this, ConfirmActivity.class);
                intent.putStringArrayListExtra("selectedApps", selectedAppPackages);
                startActivity(intent);
            }
        });
    }

    private void fetchInstalledApps(LinearLayout appsListLayout, PackageManager packageManager) {
        // Fetching installed applications
        List<ApplicationInfo> appsList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        // Clear previous views if any
        appsListLayout.removeAllViews();

        // List of popular apps to include explicitly
        List<String> popularApps = new ArrayList<>();
        popularApps.add("com.google.android.youtube"); // YouTube package name
        popularApps.add("com.google.android.apps.youtube.music"); // YouTube Music package name
        popularApps.add("com.google.android.apps.docs"); // Google Drive package name
        // Add more package names of other popular apps here if needed

        // Loop through the installed apps
        for (ApplicationInfo appInfo : appsList) {
            // Log the app name and package for debugging
            Log.d("AppInfo", "App: " + appInfo.loadLabel(packageManager) + " Package: " + appInfo.packageName);

            // Create a CheckBox for each app
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(appInfo.loadLabel(packageManager)); // Set app name
            checkBox.setTag(appInfo.packageName); // Store the package name as a tag

            // Check if the app is a popular app and include it
            if (popularApps.contains(appInfo.packageName) || (appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                // Add the CheckBox to the layout
                appsListLayout.addView(checkBox);
            } else if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                // Include non-system apps
                appsListLayout.addView(checkBox);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LinearLayout appsListLayout = findViewById(R.id.appsListLayout);
                PackageManager packageManager = getPackageManager();
                fetchInstalledApps(appsListLayout, packageManager);
            } else {
                Toast.makeText(this, "Permission denied. Cannot fetch installed applications.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
