package com.chetan2024.reduce;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectAppsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppListAdapter appListAdapter;
    private List<ApplicationInfo> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectapps);

        recyclerView = findViewById(R.id.recyclerView);
        Button submitButton = findViewById(R.id.submitButton);

        // Initialize the app list and load installed applications
        appList = loadInstalledApps();
        appListAdapter = new AppListAdapter(appList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(appListAdapter);

        submitButton.setOnClickListener(v -> {
            List<ApplicationInfo> selectedApps = appListAdapter.getSelectedApps();
            if (selectedApps.isEmpty()) {
                Toast.makeText(this, "Please select at least one application.", Toast.LENGTH_SHORT).show();
            } else {
                // Handle the selected applications (e.g., save their package names)
                // Redirect to next activity or confirm selection
                // For now, just show a toast
                Toast.makeText(this, "Selected apps: " + selectedApps.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ApplicationInfo> loadInstalledApps() {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> apps = packageManager.getInstalledApplications(0);
        List<ApplicationInfo> userApps = new ArrayList<>();
        for (ApplicationInfo app : apps) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) { // Exclude system apps
                userApps.add(app);
            }
        }
        return userApps;
    }
}
