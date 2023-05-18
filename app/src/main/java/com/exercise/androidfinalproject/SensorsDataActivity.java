package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SensorsDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensorsdata_layout);

        TextView tvTemperatureValue = findViewById(R.id.tvTemperatureValue);
        TextView tvHumidityValue = findViewById(R.id.tvHumidityValue);
        SeekBar sbLightValue = findViewById(R.id.sbLightValue);
        SeekBar sbTapValue = findViewById(R.id.sbTapValue);
        TextView tvPresenceValue = findViewById(R.id.tvPresenceValue);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Humidity = database.getReference("Humidity");
        DatabaseReference Temperature = database.getReference("Temperature");
        DatabaseReference Light = database.getReference("Light");
        DatabaseReference Tap = database.getReference("Tap");
        DatabaseReference Presence = database.getReference("Presence");


        Humidity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Double humidity = dataSnapshot.getValue(Double.class);
                tvHumidityValue.setText(humidity.toString());
                Log.d(TAG, "Value is: " + humidity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Temperature.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Double temperature = dataSnapshot.getValue(Double.class);
                tvTemperatureValue.setText(temperature.toString());
                Log.d(TAG, "Value is: " + temperature);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        sbLightValue.setMax(255);
        sbLightValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Aquí se lee el valor del SeekBar y se hace lo que se requiera con él
                Log.d("SeekBar", "El valor del SeekBar es: " + progress);
                Light.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Este método se llama cuando se toca el SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Este método se llama cuando se suelta el dedo del SeekBar
            }
        });

        sbTapValue.setMax(3);
        sbTapValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Aquí se lee el valor del SeekBar y se hace lo que se requiera con él
                Log.d("SeekBar", "El valor del SeekBar es: " + progress);
                Tap.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Este método se llama cuando se toca el SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Este método se llama cuando se suelta el dedo del SeekBar
            }
        });

        Presence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                boolean presence = dataSnapshot.getValue(Boolean.class);
                tvPresenceValue.setText(Boolean.toString(presence));
                Log.d(TAG, "Value is: " + presence);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}
