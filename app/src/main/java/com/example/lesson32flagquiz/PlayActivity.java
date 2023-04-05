package com.example.lesson32flagquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lesson32flagquiz.Models.FlagInfo;
import com.example.lesson32flagquiz.databinding.ActivityPlayBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityPlayBinding binding;
    private List<FlagInfo> flagInfoList;
    private List<String> variantList;
    private FlagInfo flagInfo;
    private LinearLayout.LayoutParams layoutParams;
    private int index = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        loadFlags();
        startGame();
    }

    private void startGame() {
        if (index < 14) {
            setFlag();
            makeVariantList();
            setVariantButtons();
        } else {
            pauseMode();
            gameOver();
        }
    }

    private void setVariantButtons() {
        for (int i = 0; i < variantList.size() / 2; i++) {
            Button button = new Button(this);
            button.setLayoutParams(layoutParams);
            button.setText(variantList.get(i));
            button.setBackgroundResource(R.drawable.button_background);
            button.setTextColor(Color.WHITE);
            button.setOnClickListener(this);
            binding.bar1.addView(button);
        }

        for (int i = variantList.size() / 2; i < variantList.size(); i++) {
            Button button = new Button(this);
            button.setLayoutParams(layoutParams);
            button.setText(variantList.get(i));
            button.setBackgroundResource(R.drawable.button_background);
            button.setTextColor(Color.WHITE);
            button.setOnClickListener(this);
            binding.bar2.addView(button);
        }
    }

    private void makeVariantList() {
        if (index < flagInfoList.size()) {
            String flagName = flagInfoList.get(index).getFlagName();
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            variantList = new ArrayList<>();
            // index -> [0;25] oraliqda
            for (int i = 0; i < flagName.length(); i++) {
                variantList.add(String.valueOf(flagName.charAt(i)));
            }
            for (int i = 0; i < 10 - flagName.length(); i++) {
                variantList.add(String.valueOf(alphabet.charAt((int) (Math.random() * 26))));
            }
            Collections.shuffle(variantList);
        }
    }

    private void setFlag() {
        binding.flagsCount.setText((index + 1) + "/" + 14);
        flagInfo = flagInfoList.get(index);
        binding.image.setImageResource(flagInfo.getFlagImage());
        binding.helper.setText("0/" + flagInfo.getFlagName().length());
    }

    private void gameOver() {
        SharedPreferences sharedPreferences = getSharedPreferences("results", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int oldScore = sharedPreferences.getInt("score", 0);
        if (oldScore < score) {
            editor.putInt("score", this.score);
            editor.commit();
        }
        binding.replay.setOnClickListener(view -> {
            gameMode();
            index = 0;
            score = 0;
            updateScore();
            startGame();
        });
        binding.exit.setOnClickListener(view -> {
            finish();
        });
    }

    private void gameMode() {
        // all invisible components changes to visible
        binding.answerBar.setVisibility(View.VISIBLE);
        binding.bar1.setVisibility(View.VISIBLE);
        binding.bar2.setVisibility(View.VISIBLE);
        binding.helper.setVisibility(View.VISIBLE);
        binding.image.setVisibility(View.VISIBLE);
        binding.score.setVisibility(View.VISIBLE);
        binding.coin.setVisibility(View.VISIBLE);
        binding.flagsCount.setVisibility(View.VISIBLE);
        binding.flag.setVisibility(View.VISIBLE);

        // all visible components changes to invisible
        binding.gameOverText.setVisibility(View.INVISIBLE);
        binding.resultText.setVisibility(View.INVISIBLE);
        binding.replay.setVisibility(View.INVISIBLE);
        binding.exit.setVisibility(View.INVISIBLE);
    }

    private void pauseMode() {
        // all visible components changes to invisible
        binding.answerBar.setVisibility(View.INVISIBLE);
        binding.bar1.setVisibility(View.INVISIBLE);
        binding.bar2.setVisibility(View.INVISIBLE);
        binding.helper.setVisibility(View.INVISIBLE);
        binding.image.setVisibility(View.INVISIBLE);
        binding.score.setVisibility(View.INVISIBLE);
        binding.coin.setVisibility(View.INVISIBLE);
        binding.flagsCount.setVisibility(View.INVISIBLE);
        binding.flag.setVisibility(View.INVISIBLE);

        // all invisible components changes to visible
        binding.gameOverText.setVisibility(View.VISIBLE);
        binding.resultText.setText(binding.resultText.getText() + "  " + score);
        binding.resultText.setVisibility(View.VISIBLE);
        binding.replay.setVisibility(View.VISIBLE);
        binding.exit.setVisibility(View.VISIBLE);

    }

    private void loadFlags() {
        flagInfoList = new ArrayList<>();
        flagInfoList.add(new FlagInfo(R.drawable.ic_argentina, "Argentina"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_belgium, "Belgium"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_brazil, "Brazil"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_canada, "Canada"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_egypt, "Egypt"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_france, "France"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_germany, "Germany"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_india, "India"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_italy, "Italy"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_saudi_arabia, "SaudiArabia"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_spain, "Spain"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_ukraine, "Ukraine"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_usa, "USA"));
        flagInfoList.add(new FlagInfo(R.drawable.ic_uzbekistan, "Uzbekistan"));
        Collections.shuffle(flagInfoList);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String letter = button.getText().toString();
        button.setVisibility(View.INVISIBLE);
        AppCompatButton newButton = new AppCompatButton(this);
        newButton.setBackgroundResource(R.drawable.button_background);
        newButton.setTextColor(Color.WHITE);
        newButton.setLayoutParams(layoutParams);
        newButton.setText(letter);
        binding.answerBar.addView(newButton);
        newButton.setOnClickListener(view1 -> {
            binding.answerBar.removeView(newButton);
            button.setVisibility(View.VISIBLE);
            int childCount = binding.answerBar.getChildCount();
            binding.helper.setText(childCount + "/" + flagInfo.getFlagName().length());
        });

        int childCount = binding.answerBar.getChildCount();
        binding.helper.setText(childCount + "/" + flagInfo.getFlagName().length());

        if (flagInfo.getFlagName().length() == childCount) {
            StringBuilder empty = new StringBuilder();
            for (int i = 0; i < childCount; i++) {
                Button button1 = (Button) binding.answerBar.getChildAt(i);
                empty.append(button1.getText().toString());
            }
            if (flagInfo.getFlagName().equalsIgnoreCase(empty.toString())) {
                score += 50;
                Toast.makeText(this, "Correct ✅", Toast.LENGTH_SHORT).show();
            } else {
                score -= 50;
                Toast.makeText(this, "Incorrect ❌", Toast.LENGTH_SHORT).show();
            }
            updateScore();
            binding.answerBar.removeAllViews();
            binding.bar1.removeAllViews();
            binding.bar2.removeAllViews();
            index++;
            startGame();
        }
    }

    private void updateScore() {
        binding.score.setText(String.valueOf(score));
    }
}