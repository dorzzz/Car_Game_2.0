package com.example.hw_cargame20;

import android.app.Application;

import com.example.hw_cargame20.Fragments.ListFragment;
import com.example.hw_cargame20.Utility.MySP;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySP.init(this);

    }


}
