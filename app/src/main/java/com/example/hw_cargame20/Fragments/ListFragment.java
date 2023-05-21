package com.example.hw_cargame20.Fragments;

import static com.example.hw_cargame20.Utility.MySP.readListFromPref;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw_cargame20.Adapters.ListAdapater;
import com.example.hw_cargame20.GpsTracker;
import com.example.hw_cargame20.Interfaces.CallBack_SendClick;
import com.example.hw_cargame20.Interfaces.OnItemClickListener;
import com.example.hw_cargame20.MenuActivity;
import com.example.hw_cargame20.Models.List;
import com.example.hw_cargame20.R;
import com.example.hw_cargame20.Utility.MySP;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListFragment extends Fragment implements OnItemClickListener {

    private CallBack_SendClick callBack_sendClick;

    private OnItemClickListener onItemClickListener;

    private GoogleMap gMap;

    private GpsTracker gpsTracker;

    private RecyclerView recyclerView;
    private MaterialButton backToMainMenuBTN;
    private ArrayList<List> list;

    private String playerName;
    private int playerScore;
    private GoogleMap googleMap;

    private Marker marker;


    public void OnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        Context context = inflater.getContext();

        findViews(view);


        backToMainMenuBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle loadBundle = getArguments();

        list = readListFromPref(getActivity());

        // if list = null then its a fresh start
        if (list == null)
            list = new ArrayList<>();

        if (loadBundle != null) {
            loadDataFromScoreActivity(loadBundle);
            saveData();
        }


        recyclerView = view.findViewById(R.id.highscore_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAdapater listAdapater = new ListAdapater(getContext(), list, this);
        recyclerView.setAdapter(listAdapater);
        listAdapater.notifyDataSetChanged();


    }

    public void loadDataFromScoreActivity(Bundle loadBundle) {
        playerName = loadBundle.getString("frag_player_name");
        playerScore = loadBundle.getInt("frag_player_score");
        dataInit(playerName, playerScore);
    }


    private void dataInit(String name, int score) {
        gpsTracker = new GpsTracker(getContext());
        double latiude = gpsTracker.getLatitude();
        double longtiude = gpsTracker.getLongitude();
        LatLng location = new LatLng(latiude, latiude);

        list.add(createNewList(name, score, location));


        //descending sort
        sortList(list);
        insertPositionOnSortedList(list);
    }

    public void saveData() {
        MySP.writeListInSP(getActivity(), list);
    }

    public List createNewList(String name, int score, LatLng location) {

        return new List(name, score, location);

    }


    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.highscore_recyclerView);
        backToMainMenuBTN = view.findViewById(R.id.list_BTN_goBack);
    }

    public void sortList(ArrayList<List> list) {

        Collections.sort(list, new Comparator<List>() {
            @Override
            public int compare(List o1, List o2) {
                return Integer.valueOf(o2.getScore()).compareTo(o1.getScore());
            }
        });

    }

    public void insertPositionOnSortedList(ArrayList<List> list) {
        int pos = 1;
        for (List array : list) {
            array.setPosition(pos);
            pos++;
        }


    }


    @Override
    public void onItemClick(int position) {
        Log.d("On Item Clicked ", list.get(position).toString());
//        list.get(position).showOnMap();

    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }


}