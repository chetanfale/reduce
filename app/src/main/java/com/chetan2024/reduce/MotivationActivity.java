package com.chetan2024.reduce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MotivationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motivation);

        // You can customize the motivational text if needed here dynamically
    }

    // Method to handle button click and navigate to Permission Request activity
    public void goToPermReq(View view) {
        Intent intent = new Intent(MotivationActivity.this, PermReqActivity.class);
        startActivity(intent);
    }
}
