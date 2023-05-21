package com.example.hw_cargame20;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.hw_cargame20.Utility.MySP.readListFromPref;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hw_cargame20.Fragments.ListFragment;
import com.example.hw_cargame20.Fragments.MapFragment;
import com.example.hw_cargame20.Interfaces.CallBack_SendClick;
import com.example.hw_cargame20.Models.List;
import com.example.hw_cargame20.Utility.MySP;
import com.example.hw_cargame20.Utility.SignalGenerator;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity {
    private ListFragment listFragment;
    private MapFragment mapFragment;

    private ArrayList<List> list;

    private SignalGenerator sg;

    private MySP mysp;

    private LocationManager locationManager;
    private String playerName;
    private int playerScore;

    private CallBack_SendClick callBack_sendClick = new CallBack_SendClick() {
        @Override
        public void onPlayerClicked() {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        initFragments();
        readFromGameOverActivity();


    }


    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.highscore_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.highscore_FRAME_map, mapFragment).commit();
    }


    private void initFragments() {
        listFragment = new ListFragment();
        mapFragment = new MapFragment();
        beginTransactions();
    }


    private void readFromGameOverActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null)
            sendDataToFragment(bundle.getString("PLAYER_NAME"), bundle.getInt("PLAYER_SCORE"));


    }


    public void sendDataToFragment(String name, int score) {
        Bundle bundle = new Bundle();
        bundle.putString("frag_player_name", name);
        bundle.putInt("frag_player_score", score);
        ListFragment newList = new ListFragment();
        newList.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.highscore_FRAME_list, newList).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.highscore_FRAME_map, mapFragment).commit();

    }


}

