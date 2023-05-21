package com.example.hw_cargame20;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.hw_cargame20.Interfaces.SensorCallBack;

public class SenManager {
    private Sensor sensor;
    private SensorManager sensorManager;

    private SensorCallBack sensorCallBack;

    private int moveCounterX = 0;


    private int moveCounterY = 0;

    private long timestamp = 0;
    private SensorEventListener sensorEventListener;

    public SenManager(Context context, SensorCallBack sensorCallBack) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorCallBack = sensorCallBack;
        initEventListener();
        start();


    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float z = event.values[2];
                moveStepSensor(x, z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //pass
            }
        };
    }

    private void moveStepSensor(float x, float z) {
        if (x > 6.0) {
            sensorCallBack.moveX();
            stop();

        }
        if (x < -6.0) {
            sensorCallBack.moveY();
            stop();

        }

//        if (z > 6.0) {
//            sensorCallBack.moveZ();
//            stop();
//        }
//        if (z < -6.0) {
//            sensorCallBack.moveW();
//            stop();
//        }
    }


    public int getMoveCounterX() {
        return moveCounterX;
    }

    public int getMoveCounterY() {
        return moveCounterY;
    }


    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }


}
