package com.example.hw_cargame20;


import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.hw_cargame20.Fragments.ListFragment;
import com.example.hw_cargame20.Fragments.MapFragment;
import com.example.hw_cargame20.Interfaces.SensorCallBack;
import com.example.hw_cargame20.Logic.GameManager;
import com.example.hw_cargame20.Utility.SignalGenerator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private AppCompatImageView main_IMG_background;

//    private AppCompatImageView main_IMG_startScreen;

    private MaterialTextView main_LBL_odometer;

    private ShapeableImageView[] main_IMG_hearts;

    private ShapeableImageView[] main_IMG_obstacles;

    private ShapeableImageView[] main_IMG_motorcycle;

    private ShapeableImageView[] main_IMG_explosive;

    private FloatingActionButton[] floatingActionButtons;


    private final ShapeableImageView[][] board = new ShapeableImageView[4][5];

    private SenManager sensorManager;

    private Random randomNumber;

    private MediaPlayer mediaPlayer;
    private int menuValue;
    private String playerName;
    private GameManager gm;

    private MapFragment mapFragment;

    private SignalGenerator signalGenerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        menuValue = extras.getInt("DELAY_TIME");

        if (extras.getString("PLAYER_NAME") != null)
            playerName = extras.getString("PLAYER_NAME");

        if (extras.getBoolean("START_SENSOR") == true) {
            floatingActionButtons[0].setVisibility(View.INVISIBLE);
            floatingActionButtons[1].setVisibility(View.INVISIBLE);
        }


        SignalGenerator.init(this);


        gm = new GameManager(main_IMG_hearts.length);


        randomNumber = new Random();


        signalGenerator = new SignalGenerator(this);

        //first launch of game
        initBoard();
        refreshPlayer();
        contentBoard(menuValue);
        startOdometer();


    }

    private void initSensorManager() {
        sensorManager = new SenManager(this, new SensorCallBack() {

            int cell = playerLocationFinder();

            @Override
            public void moveX() {

                if (cell == 4) {
                    return;
                }
                if (cell == 3) {
                    board[3][4].setVisibility(View.VISIBLE);
                    board[3][3].setVisibility(View.INVISIBLE);
                    return;
                }

                if (cell == 2) {
                    board[3][3].setVisibility(View.VISIBLE);
                    board[3][2].setVisibility(View.INVISIBLE);

                }
                if (cell == 1) {
                    board[3][2].setVisibility(View.VISIBLE);
                    board[3][1].setVisibility(View.INVISIBLE);

                }

                if (cell == 0) {
                    board[3][1].setVisibility(View.VISIBLE);
                    board[3][0].setVisibility(View.INVISIBLE);

                }

//

            }

            @Override
            public void moveY() {
                if (cell == 0) {
                    return;
                }

                if (cell == 1) {
                    board[3][0].setVisibility(View.VISIBLE);
                    board[3][1].setVisibility(View.INVISIBLE);
                    return;


                }

                if (cell == 2) {
                    board[3][1].setVisibility(View.VISIBLE);
                    board[3][2].setVisibility(View.INVISIBLE);
                    return;
                }

                if (cell == 3) {
                    board[3][2].setVisibility(View.VISIBLE);
                    board[3][3].setVisibility(View.INVISIBLE);
                    return;
                }

                if (cell == 4) {
                    board[3][3].setVisibility(View.VISIBLE);
                    board[3][4].setVisibility(View.INVISIBLE);
                }

            }

//            @Override
//            public void moveZ() {
//                contentBoard(3000);
//
//
//            }
//
//            @Override
//            public void moveW() {
//                contentBoard(1500);
//            }
        });


    }


    int start = 0;
    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            start++;

            handler.postDelayed(this, 1000); //do it again in a second.
            updateOdometer(start);


        }


    };


    private void updateOdometer(int start) {
        main_LBL_odometer.setText(String.format("%d", start));
        start++;
    }

    private void stopOdometer() {
        handler.removeCallbacksAndMessages(null);

    }

    public void startOdometer() {
        handler.postDelayed(runnable, 1000);
    }


    public void randomStart() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (isFinishing())
                    handler.removeCallbacks(this);
                else {

                    int rand = 0;
                    rand = randomNumber.nextInt(5);
                    board[0][rand].setVisibility(View.VISIBLE);

                    handler.postDelayed(this, 1000);
                }

            }
        };


        handler.postDelayed(runnable, 1000);
    }

    public void moveColumn1(int menuValue) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int index = 1;

            @Override
            public void run() {

                if (isFinishing())
                    handler.removeCallbacks(this);

                else {


                    if (index == 3 && board[3][0].getVisibility() == View.VISIBLE && board[2][0].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[0].setVisibility(View.VISIBLE);
                        //get hit , minus heart
                        gm.getHit();
                        playAudio_crash();

                        SignalGenerator.getInstance().vibrate();
                        SignalGenerator.getInstance().toast("Ouch", Toast.LENGTH_SHORT);

                        board[3][0].setVisibility(View.INVISIBLE);
                        return;
                    }

                    if (index != 3 && main_IMG_explosive[0].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[0].setVisibility(View.INVISIBLE);
                        board[3][0].setVisibility(View.VISIBLE);
                        return;
                    }

                    if (index == 3) {
                        board[2][0].setVisibility(View.INVISIBLE);
                        index = 1;
                        return;
                    }

                    board[index][0].setVisibility(View.VISIBLE);
                    board[index - 1][0].setVisibility(View.INVISIBLE);
                    index++;


                    handler.postDelayed(this, menuValue);
                }

            }
        };

        handler.postDelayed(runnable, menuValue);

    }

    public void moveColumn2(int menuValue) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            int index = 1;

            @Override
            public void run() {
                if (isFinishing())
                    handler.removeCallbacks(this);

                else {


                    if (index == 3 && board[3][1].getVisibility() == View.VISIBLE && board[2][1].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[1].setVisibility(View.VISIBLE);
                        //get hit , minus heart
                        gm.getHit();
                        playAudio_crash();


                        SignalGenerator.getInstance().vibrate();
                        SignalGenerator.getInstance().toast("Ouch", Toast.LENGTH_SHORT);

                        board[3][1].setVisibility(View.INVISIBLE);
                        return;
                    }

                    if (index != 3 && main_IMG_explosive[1].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[1].setVisibility(View.INVISIBLE);
                        board[3][1].setVisibility(View.VISIBLE);
                        return;
                    }

                    if (index == 3) {
                        board[2][1].setVisibility(View.INVISIBLE);
                        index = 1;
                        return;
                    }

                    board[index][1].setVisibility(View.VISIBLE);
                    board[index - 1][1].setVisibility(View.INVISIBLE);
                    index++;

                    handler.postDelayed(this, menuValue + 1300);
                }
            }
        };


        handler.postDelayed(runnable, menuValue + 1000);


    }

    public void moveColumn3(int menuValue) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int index = 1;

            @Override
            public void run() {

                if (isFinishing())
                    handler.removeCallbacks(this);

                else {


                    if (index == 3 && board[3][2].getVisibility() == View.VISIBLE && board[2][2].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[2].setVisibility(View.VISIBLE);
                        //get hit , minus heart
                        gm.getHit();
                        playAudio_crash();

                        SignalGenerator.getInstance().vibrate();
                        SignalGenerator.getInstance().toast("Ouch", Toast.LENGTH_SHORT);

                        board[3][2].setVisibility(View.INVISIBLE);
                        return;
                    }

                    if (index != 3 && main_IMG_explosive[2].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[2].setVisibility(View.INVISIBLE);
                        board[3][2].setVisibility(View.VISIBLE);
                        return;
                    }

                    if (index == 3) {
                        board[2][2].setVisibility(View.INVISIBLE);
                        index = 1;
                        return;
                    }

                    board[index][2].setVisibility(View.VISIBLE);
                    board[index - 1][2].setVisibility(View.INVISIBLE);
                    index++;


                    handler.postDelayed(this, menuValue + 1000);
                }

            }
        };

        handler.postDelayed(runnable, menuValue);

    }

    private void moveColumn4(int menuValue) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int index = 1;

            @Override
            public void run() {

                if (isFinishing())
                    handler.removeCallbacks(this);

                else {


                    if (index == 3 && board[3][3].getVisibility() == View.VISIBLE && board[2][3].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[3].setVisibility(View.VISIBLE);
                        //get hit , minus heart
                        gm.getHit();
                        playAudio_crash();

                        SignalGenerator.getInstance().vibrate();
                        SignalGenerator.getInstance().toast("Ouch", Toast.LENGTH_SHORT);

                        board[3][3].setVisibility(View.INVISIBLE);
                        return;
                    }

                    if (index != 3 && main_IMG_explosive[3].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[3].setVisibility(View.INVISIBLE);
                        board[3][3].setVisibility(View.VISIBLE);
                        return;
                    }

                    if (index == 3) {
                        board[2][3].setVisibility(View.INVISIBLE);
                        index = 1;
                        return;
                    }

                    board[index][3].setVisibility(View.VISIBLE);
                    board[index - 1][3].setVisibility(View.INVISIBLE);
                    index++;

                    handler.postDelayed(this, menuValue);
                }

            }
        };

        handler.postDelayed(runnable, menuValue);

    }


    private void moveColumn5(int menuValue) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int index = 1;

            @Override
            public void run() {

                if (isFinishing())
                    handler.removeCallbacks(this);

                else {


                    if (index == 3 && board[3][4].getVisibility() == View.VISIBLE && board[2][4].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[4].setVisibility(View.VISIBLE);
                        //get hit , minus heart
                        gm.getHit();
                        playAudio_crash();

                        SignalGenerator.getInstance().vibrate();
                        SignalGenerator.getInstance().toast("Ouch", Toast.LENGTH_SHORT);

                        board[3][4].setVisibility(View.INVISIBLE);
                        return;
                    }

                    if (index != 3 && main_IMG_explosive[4].getVisibility() == View.VISIBLE) {
                        main_IMG_explosive[4].setVisibility(View.INVISIBLE);
                        board[3][4].setVisibility(View.VISIBLE);
                        return;
                    }
                                
                    if (index == 3) {
                        board[2][4].setVisibility(View.INVISIBLE);
                        index = 1;
                        return;
                    }


                    board[index][4].setVisibility(View.VISIBLE);
                    board[index - 1][4].setVisibility(View.INVISIBLE);
                    index++;

                    handler.postDelayed(this, menuValue + 3000);
                }
            }


        };


        handler.postDelayed(runnable, menuValue + 1500);


    }


    public void obstaclesMovement(int menuValue) {

        randomStart();

        if (board[0][0].getVisibility() == View.VISIBLE)
            moveColumn1(menuValue);


        if (board[0][1].getVisibility() == View.VISIBLE)
            moveColumn2(menuValue);


        if (board[0][2].getVisibility() == View.VISIBLE)
            moveColumn3(menuValue);


        if (board[0][3].getVisibility() == View.VISIBLE)
            moveColumn4(menuValue);

        if (board[0][4].getVisibility() == View.VISIBLE)
            moveColumn5(menuValue);

    }


    public void playerMovementListener(int cell) {
        for (FloatingActionButton fab : floatingActionButtons) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (fab == findViewById(R.id.fab_BTN_left)) {
                        if (cell == 0) {
                            board[3][1].setVisibility(View.VISIBLE);
                            board[3][0].setVisibility(View.INVISIBLE);
                            return;
                        }

                        if (cell == 1) {
                            board[3][2].setVisibility(View.VISIBLE);
                            board[3][1].setVisibility(View.INVISIBLE);
                            return;


                        }

                        if (cell == 2) {
                            board[3][3].setVisibility(View.VISIBLE);
                            board[3][2].setVisibility(View.INVISIBLE);
                            return;
                        }

                        if (cell == 3) {
                            board[3][4].setVisibility(View.VISIBLE);
                            board[3][3].setVisibility(View.INVISIBLE);
                            return;
                        }

                        if (cell == 4) {

                        }
                    }
                    if (fab == findViewById(R.id.fab_BTN_right)) {
                        if (cell == 4) {
                            board[3][3].setVisibility(View.VISIBLE);
                            board[3][4].setVisibility(View.INVISIBLE);
                            return;
                        }
                        if (cell == 3) {
                            board[3][2].setVisibility(View.VISIBLE);
                            board[3][3].setVisibility(View.INVISIBLE);
                            return;
                        }

                        if (cell == 2) {
                            board[3][1].setVisibility(View.VISIBLE);
                            board[3][2].setVisibility(View.INVISIBLE);

                        }
                        if (cell == 1) {
                            board[3][0].setVisibility(View.VISIBLE);
                            board[3][1].setVisibility(View.INVISIBLE);
                        }
                        if (cell == 0) {
                        }
                    }
                }
            });
        }
    }

    public int playerLocationFinder() {

        int row = 3;

        int index = 0;


        for (int i = row; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getVisibility() == View.VISIBLE) {
                    index = j;
                }
            }
        }

        return index;

    }


    public void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);

        main_LBL_odometer = findViewById(R.id.main_LBL_odometer);

        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};


        main_IMG_obstacles = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_obstacle1),
                findViewById(R.id.main_IMG_obstacle2),
                findViewById(R.id.main_IMG_obstacle3),
                findViewById(R.id.main_IMG_obstacle4),
                findViewById(R.id.main_IMG_obstacle5),
                findViewById(R.id.main_IMG_obstacle6),
                findViewById(R.id.main_IMG_obstacle7),
                findViewById(R.id.main_IMG_obstacle8),
                findViewById(R.id.main_IMG_obstacle9),
                findViewById(R.id.main_IMG_obstacle10),
                findViewById(R.id.main_IMG_obstacle11),
                findViewById(R.id.main_IMG_obstacle12),
                findViewById(R.id.main_IMG_obstacle13),
                findViewById(R.id.main_IMG_obstacle14),
                findViewById(R.id.main_IMG_obstacle15)};

        floatingActionButtons = new FloatingActionButton[]{
                findViewById(R.id.fab_BTN_left),
                findViewById(R.id.fab_BTN_right)};

        main_IMG_motorcycle = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_motorcycle1),
                findViewById(R.id.main_IMG_motorcycle2),
                findViewById(R.id.main_IMG_motorcycle3),
                findViewById(R.id.main_IMG_motorcycle4),
                findViewById(R.id.main_IMG_motorcycle5)};

        main_IMG_explosive = new ShapeableImageView[]{
                findViewById(R.id.explosion1),
                findViewById(R.id.explosion2),
                findViewById(R.id.explosion3),
                findViewById(R.id.explosion4),
                findViewById(R.id.explosion5)};


    }


    public void initBoard() {
        int k = 0;

        //init cars (all invisible)
        for (int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = main_IMG_obstacles[k];
                k++;
            }
        }

        //init motorcycle
        board[3][0] = main_IMG_motorcycle[0];
        board[3][1] = main_IMG_motorcycle[1];
        board[3][2] = main_IMG_motorcycle[2];
        board[3][3] = main_IMG_motorcycle[3];
        board[3][4] = main_IMG_motorcycle[4];


    }

    private void initFragments() {
        mapFragment = new MapFragment();
//        listFragment = new ListFragment();
    }

    public void refreshUI() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        if (gm.isGameEnded()) {
            playAudio_gameOver();
            stopOdometer();
            sendNameAndScoreToGameOverActivity(getPlayerScore());


        }

        if (gm.getHitPoint() >= 1 && gm.getHitPoint() < 4) {
            main_IMG_hearts[main_IMG_hearts.length - gm.getHitPoint()].setVisibility(View.INVISIBLE);
        }
    }

    private int getPlayerScore() {
        return Integer.valueOf(main_LBL_odometer.getText().toString());
    }


    public void refreshBoard(int menuValue) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isFinishing())
                    handler.removeCallbacks(this);
                else {
                    obstaclesMovement(menuValue);
                    contentBoard(menuValue);

                    handler.postDelayed(this, 5000);
                }
            }

        };


        handler.postDelayed(runnable, 8000);

    }

    public void refreshPlayer() {

        Handler handler = new Handler();
        Intent sensorIntent = getIntent();
        Bundle extras = sensorIntent.getExtras();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (isFinishing())
                    handler.removeCallbacks(this);
                else {

                    int cell = playerLocationFinder();

                    if (extras.getBoolean("START_SENSOR") == true) {
                        initSensorManager();

                    } else
                        playerMovementListener(cell);
                    refreshUI();

                    handler.postDelayed(this, 0500);
                }
            }

        };


        handler.postDelayed(runnable, 0500);
    }


    private void contentBoard(int menuValue) {
        refreshBoard(menuValue);
    }


    private void sendNameAndScoreToGameOverActivity(int score) {
        //from MainMenu
        Intent menuIntent = getIntent();
        Bundle extras = menuIntent.getExtras();
        playerName = extras.getString("PLAYER_NAME");

        //ToGameOverActivity
        Intent intent = new Intent(getBaseContext(), GameOverActivity.class);
        intent.putExtra("SCORE", score);
        if (playerName != null)
            intent.putExtra("PLAYER_NAME", playerName);
        startActivity(intent);
        finish();

    }


    public void playAudio_crash() {
        mediaPlayer = MediaPlayer.create(this, R.raw.crashsnd);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();
    }

    public void playAudio_gameOver() {
        mediaPlayer = MediaPlayer.create(this, R.raw.gameoversnd);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();
    }


}





