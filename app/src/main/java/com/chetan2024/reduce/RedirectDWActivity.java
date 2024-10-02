package com.chetan2024.reduce;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RedirectDWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redirectdw);

        TextView instructionsText = findViewById(R.id.instructionsText);
        Button goToDigitalWellbeingButton = findViewById(R.id.goToDigitalWellbeingButton);

        // Set instructions on how to apply app timers
        instructionsText.setText("To apply app timers using Digital Wellbeing, follow these steps:\n\n" +
                "1. Open the Digital Wellbeing app.\n" +
                "2. Tap on 'Dashboard' to see the list of your apps.\n" +
                "3. Select the app you want to limit.\n" +
                "4. Set a timer for how long you want to use the app each day.\n" +
                "5. Once you reach the timer limit, the app will be paused for the day.\n\n" +
                "By using app timers, you can better manage your screen time and reduce distractions.");

        // Set an OnClickListener for the button
        goToDigitalWellbeingButton.setOnClickListener(v -> {
            Intent launchIntent = new Intent();
            launchIntent.setAction(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            launchIntent.setPackage("com.google.android.apps.wellbeing");

            try {
                startActivity(launchIntent);
            } catch (ActivityNotFoundException e) {
                // If the app is not installed, show a toast or handle the error gracefully
                e.printStackTrace(); // Print the stack trace for debugging
                // Optionally, redirect to settings or show a message
            }

            // If it doesn't work, try using a specific activity
            try {
                Intent specificIntent = new Intent();
                specificIntent.setAction(Intent.ACTION_VIEW);
                specificIntent.setClassName("com.google.android.apps.wellbeing", "com.google.android.apps.wellbeing.home.HomeActivity");
                startActivity(specificIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace(); // Handle exception if the activity is not found
            }
        });
    }
}
