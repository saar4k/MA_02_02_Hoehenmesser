package com.example.hoehenmesser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        // Listener für den Luftdrucksensor
        SensorEventListener pressureListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                // Umrechnung von Luftdruck in Höhe mit der Internationalen Höhenformel
                float pressure = event.values[0];
                float altitude = (float) ((288.15/0.0065) * (1.0 - Math.pow(pressure / 1013.25, 0.1903)));
                updateAltitude(altitude);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        // Registrierung des Listeners für den Luftdrucksensor
        sensorManager.registerListener(pressureListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void updateAltitude(float altitude) {
        // Aktualisieren der View mit der berechneten Höhe
        TextView altitudeView = findViewById(R.id.altitude_view);
        altitudeView.setText(String.format(Locale.getDefault(), "%.2f m", altitude));
    }
}