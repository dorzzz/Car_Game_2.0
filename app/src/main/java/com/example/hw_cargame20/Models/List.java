package com.example.hw_cargame20.Models;


import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hw_cargame20.GpsTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class List {


    public int position;

    public String name;
    public int score;


    private GpsTracker gpsTracker;
    public static int counter = 0;
    private GoogleMap googleMap;

    private LatLng latLng;


    public List(String name, int score, LatLng latLng) {
        this.name = name;
        this.score = score;
        this.latLng = latLng;

    }

    public void addMarkerToMap(LatLng latLng) {
        googleMap.addMarker(new MarkerOptions().position(latLng)).setTag(counter++);
    }

//    public void setTagForMap() {
//        LatLng location = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
//        googleMap.addMarker(new MarkerOptions().position(location)).setTag(counter++);
////        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
//
//    }


    public int getPosition() {
        return position;
    }

    public List setPosition(int position) {
        this.position = position;
        return this;
    }


    public String getName() {
        return name;
    }

    public List setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public List setScore(int score) {
        this.score = score;
        return this;
    }

    public LatLng getLatLng() {
        return latLng;
    }


    @Override
    public String toString() {
        return "position : " + position + " name : " + name + " score : " + score;
    }


}
