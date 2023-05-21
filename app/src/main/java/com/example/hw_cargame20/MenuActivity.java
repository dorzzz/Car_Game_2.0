package com.example.hw_cargame20;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.hw_cargame20.Fragments.ListFragment;
import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    private AppCompatImageView menu_IMG_background;

    private MaterialButton menu_BTN_start;
    private MaterialButton menu_BTN_slow;
    private MaterialButton menu_BTN_fast;
    private MaterialButton menu_BTN_highscore;


    private AppCompatEditText playerNameInput;


    private String name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();


        Glide
                .with(this)
                .load("https://play-lh.googleusercontent.com/WmfwiVhR7YgfZmBLN2aZkC68NjFu80biLtaC5NzkqIL0rJB4ywmUvHOgbhFklfwZWnLL")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(menu_IMG_background);


        menu_BTN_slow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("DELAY_TIME", 3000);
                if (playerNameInput.getText().toString() != null) {
                    name = playerNameInput.getText().toString();
                    extras.putString("PLAYER_NAME", name);
                }
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


        menu_BTN_fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("DELAY_TIME", 1500);
                if (playerNameInput.getText().toString() != null) {
                    name = playerNameInput.getText().toString();
                    extras.putString("PLAYER_NAME", name);
                }
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


        //enable sensors
        menu_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                //Return an boolean expression for sensors .
                Bundle extras = new Bundle();
                extras.putBoolean("START_SENSOR", true);
                extras.putInt("DELAY_TIME", 3000);
                if (playerNameInput.getText().toString() != null) {
                    name = playerNameInput.getText().toString();
                    extras.putString("PLAYER_NAME", name);
                }
                intent.putExtras(extras);
                startActivity(intent);

            }
        });


        menu_BTN_highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HighscoreActivity.class);
                startActivity(intent);

            }
        });


    }

    public void findViews() {
        menu_IMG_background = findViewById(R.id.menu_IMG_background);
        menu_BTN_highscore = findViewById(R.id.menu_BTN_highscores);
        menu_BTN_fast = findViewById(R.id.menu_BTN_fast);
        menu_BTN_slow = findViewById(R.id.menu_BTN_slow);
        menu_BTN_start = findViewById(R.id.menu_BTN_start);
        playerNameInput = findViewById(R.id.menu_editText_mainMenu);
    }


}
