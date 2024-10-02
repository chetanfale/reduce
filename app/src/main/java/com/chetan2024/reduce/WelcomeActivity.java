package com.chetan2024.reduce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        Button proceedButton = findViewById(R.id.proceed_button);

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Motivational Activity when Proceed is clicked
                Intent intent = new Intent(WelcomeActivity.this, MotivationActivity.class);
                startActivity(intent);
            }
        });
    }
}
