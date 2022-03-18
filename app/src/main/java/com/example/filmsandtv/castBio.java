package com.example.filmsandtv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class castBio extends AppCompatActivity {
    TextView bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_bio);
        bio = findViewById(R.id.bio);
        bio.setText(getIntent().getStringExtra("bio"));
    }
}