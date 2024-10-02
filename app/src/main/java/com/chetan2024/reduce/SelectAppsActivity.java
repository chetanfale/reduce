package com.chetan2024.reduce;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SelectAppsActivity extends AppCompatActivity {

    private List<String> appList; // List of application names
    private List<String> selectedApps; // List of selected applications
    private ListView appsListView; // ListView for displaying apps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectapps); // Make sure to use the correct XML layout file

        appsListView = findViewById(R.id.appsListView);
        appList = new ArrayList<>();
        selectedApps = new ArrayList<>();

        // Load installed applications
        loadInstalledApplications();
    }

    private void loadInstalledApplications() {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo : apps) {
            // Exclude system apps if needed
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appList.add(appInfo.loadLabel(packageManager).toString());
            }
        }

        // Create a custom adapter to display the apps in a checkbox list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, appList);
        appsListView.setAdapter(adapter);
        appsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Handle checkbox selection
        appsListView.setOnItemClickListener((parent, view, position, id) -> {
            CheckBox checkBox = (CheckBox) view.findViewById(android.R.id.checkbox);
            if (checkBox.isChecked()) {
                selectedApps.add(appList.get(position));
            } else {
                selectedApps.remove(appList.get(position));
            }
        });
    }
}
