package com.chetan2024.reduce;

import android.accessibilityservice.AccessibilityService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import androidx.core.app.NotificationCompat;

import java.util.HashSet;
import java.util.Set;

public class AppMonitorService extends AccessibilityService {

    private Set<String> restrictedApps;
    private PackageManager packageManager;
    private NotificationManager notificationManager;
    private static final String CHANNEL_ID = "reduce_channel";

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        packageManager = getPackageManager();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();

        // Load restricted apps from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("ReducePrefs", MODE_PRIVATE);
        restrictedApps = prefs.getStringSet("selectedApps", new HashSet<>());
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageName = event.getPackageName() != null ? event.getPackageName().toString() : "";
            if (restrictedApps.contains(packageName)) {
                sendDisciplineNotification(packageName);
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Handle service interruption if necessary
    }

    /**
     * Creates a notification channel for devices running Android O and above.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reduce Notifications";
            String description = "Notifications to discourage app usage";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Sends a notification discouraging the user from using the specified app.
     *
     * @param packageName The package name of the app being discouraged.
     */
    private void sendDisciplineNotification(String packageName) {
        String appName = getAppName(packageName);
        String message = "You've opened " + appName + ". Consider taking a break!";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_reduce) // Ensure this icon exists in your drawable resources
                .setContentTitle("Reduce Alert")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        notificationManager.notify(packageName.hashCode(), builder.build());
    }

    /**
     * Retrieves the app name based on its package name.
     *
     * @param packageName The package name of the app.
     * @return The human-readable app name.
     */
    private String getAppName(String packageName) {
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
            return packageManager.getApplicationLabel(appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown App";
        }
    }
}
