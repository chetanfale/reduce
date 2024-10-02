package com.chetan2024.reduce;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RedirectDWActivity extends AppCompatActivity {

    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redirectdw);

        TextView instructionsText = findViewById(R.id.instructionsText);
        Button goToDigitalWellbeingButton = findViewById(R.id.goToDigitalWellbeingButton);
        proceedButton = findViewById(R.id.proceedButton);

        // Set instructions on how to apply app timers
        instructionsText.setText("To apply app timers using Digital Wellbeing, follow these steps:\n\n" +
                "1. Open the Digital Wellbeing app.\n" +
                "2. Tap on 'Dashboard' to see the list of your apps.\n" +
                "3. Select the app you want to limit.\n" +
                "4. Set a timer for how long you want to use the app each day.\n" +
                "5. Once you reach the timer limit, the app will be paused for the day.\n\n" +
                "By using app timers, you can better manage your screen time and reduce distractions.");

        // Set up button click listener to launch Digital Wellbeing app
        goToDigitalWellbeingButton.setOnClickListener(v -> launchDigitalWellbeingApp());

        // Set up click listener for the Proceed button
        proceedButton.setOnClickListener(v -> {
            // Intent to start SummaryActivity
            Intent intent = new Intent(RedirectDWActivity.this, SummaryActivity.class);
            // Pass the selected apps if needed, for example, from the ConfirmActivity
            ArrayList<String> selectedApps = getIntent().getStringArrayListExtra("selectedApps");
            intent.putStringArrayListExtra("selectedApps", selectedApps); // Pass selected apps to SummaryActivity
            startActivity(intent);
            finish(); // Close RedirectDWActivity
        });
    }

    private void launchDigitalWellbeingApp() {
        Intent intent = new Intent();
        intent.setClassName("com.google.android.apps.wellbeing", "com.google.android.apps.wellbeing.settings.TopLevelSettingsActivity");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("RedirectDWActivity", "Digital Wellbeing app not found", e);
            Toast.makeText(RedirectDWActivity.this, "Digital Wellbeing app not found", Toast.LENGTH_SHORT).show();
        }
    }
}
