package com.example.hw_cargame20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.hw_cargame20.Fragments.ListFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class GameOverActivity extends AppCompatActivity {

    private MaterialTextView gameOverText;
    private MaterialTextView score; // THE SCORE YOU GOT

    private MaterialTextView gameOver_LBL_score;

    private String playerName;

    private AppCompatImageView gameOver_IMG_background;
    private int playerScore;

    private int latiude;
    private int longtidue;
    private MaterialButton getBackToMenu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        findViews();

        Glide
                .with(this)
                .load("https://media.istockphoto.com/id/1359947170/vector/motorcycle-accident-flat-vector-illustration-injured-motorcyclist-sitting-on-the-road-next.jpg?s=612x612&w=0&k=20&c=uSlm8t0w7b7qxr7nOJ03iwjjBG5ajgoDmuBeAu_B0bo=")
                .fitCenter()
                .placeholder(R.drawable.ic_launcher_background)
                .into(gameOver_IMG_background);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        playerScore = extras.getInt("SCORE");
        playerName = extras.getString("PLAYER_NAME");
        if (playerName == null)
            playerName = "default";

        score.setText(playerScore + "");


        getBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToHighScoreActivity(playerName, playerScore);
            }
        });

    }

    public void sendToHighScoreActivity(String name, int score) {

        Intent intent = new Intent(getBaseContext(), HighscoreActivity.class);
        Bundle data = new Bundle();

        data.putString("PLAYER_NAME", name);
        data.putInt("PLAYER_SCORE", score);


        intent.putExtras(data);

        startActivity(intent);
        finish();


    }


    private void findViews() {
        getBackToMenu = findViewById(R.id.gameOver_BTN_goToMainMenu);
        score = findViewById(R.id.gameOver_insert_score_here);
        gameOverText = findViewById(R.id.gameOverText);
        gameOver_LBL_score = findViewById(R.id.gameOver_LBL_score);
        gameOver_IMG_background = findViewById(R.id.gameOver_IMG_background);


    }
}


