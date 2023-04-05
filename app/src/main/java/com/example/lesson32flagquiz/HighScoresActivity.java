package com.example.lesson32flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.lesson32flagquiz.databinding.ActivityHighScoresBinding;

public class HighScoresActivity extends AppCompatActivity {
    private ActivityHighScoresBinding binding;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHighScoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("results", MODE_PRIVATE);
        int score = sharedPreferences.getInt("score", 0);
        binding.text.setText(binding.text.getText().toString() + "\n" + score);
    }
}