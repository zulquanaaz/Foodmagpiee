package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//Profile Activity calss extend from CompactActivity from appcompact.
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
