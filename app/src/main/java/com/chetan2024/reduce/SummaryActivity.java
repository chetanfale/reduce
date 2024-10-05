package com.chetan2024.reduce;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    private TextView summaryTextView;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        summaryTextView = findViewById(R.id.summaryTextView);
        finishButton = findViewById(R.id.finishButton);

        // Set summary message
        summaryTextView.setText("Thank you for setting up Reduce! We've saved your selected apps. Now, let's enable monitoring.");

        // Show Accessibility Service prompt
        showAccessibilityServiceDialog();

        finishButton.setOnClickListener(v -> {
            // Optionally, navigate to home or close the app
            finish();
        });
    }

    /**
     * Displays a dialog prompting the user to enable the Accessibility Service.
     */
    private void showAccessibilityServiceDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Enable Accessibility Service")
                .setMessage("To monitor app usage and send notifications, please enable the Accessibility Service for Reduce.")
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SummaryActivity.this, "Accessibility Service is required for full functionality.", Toast.LENGTH_LONG).show();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
