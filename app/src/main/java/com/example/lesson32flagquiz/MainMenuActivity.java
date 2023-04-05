package com.example.lesson32flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lesson32flagquiz.databinding.ActivityMainMenuBinding;

public class MainMenuActivity extends AppCompatActivity {
    private ActivityMainMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.startPlay.setOnClickListener(view -> {
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
        });
        binding.highScores.setOnClickListener(view -> {
            Intent intent = new Intent(this, HighScoresActivity.class);
            startActivity(intent);
        });
        binding.exit.setOnClickListener(view -> {
            finishAffinity();
        });
    }
}