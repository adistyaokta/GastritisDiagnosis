package com.sistempakar.gastritisdiagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final int SPLASH_SCREEN_TIMEOUT = 3000; // 3 seconds delay

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the splash activity
            }
        }, SPLASH_SCREEN_TIMEOUT);

    }
}