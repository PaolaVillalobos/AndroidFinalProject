package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SensorsDataFragment extends Fragment {

    private TextView tvTemperatureValue, tvHumidityValue, tvPresenceValue;
    private SeekBar sbLightValue, sbTapValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sensorsdata_layout, container, false);

        tvTemperatureValue = view.findViewById(R.id.tvTemperatureValue);
        tvHumidityValue = view.findViewById(R.id.tvHumidityValue);
        sbLightValue = view.findViewById(R.id.sbLightValue);
        sbTapValue = view.findViewById(R.id.sbTapValue);
        tvPresenceValue = view.findViewById(R.id.tvPresenceValue);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference humidityRef = database.getReference("Humidity");
        DatabaseReference temperatureRef = database.getReference("Temperature");
        DatabaseReference lightRef = database.getReference("Light");
        DatabaseReference tapRef = database.getReference("Tap");
        DatabaseReference presenceRef = database.getReference("Presence");

        humidityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double humidity = dataSnapshot.getValue(Double.class);
                tvHumidityValue.setText(humidity.toString());
                Log.d(TAG, "Value is: " + humidity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        temperatureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double temperature = dataSnapshot.getValue(Double.class);
                tvTemperatureValue.setText(temperature.toString());
                Log.d(TAG, "Value is: " + temperature);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        sbLightValue.setMax(255);
        sbLightValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("SeekBar", "El valor del SeekBar es: " + progress);
                lightRef.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sbTapValue.setMax(3);
        sbTapValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("SeekBar", "El valor del SeekBar es: " + progress);
                tapRef.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        presenceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean presence = dataSnapshot.getValue(Boolean.class);
                tvPresenceValue.setText(Boolean.toString(presence));
                Log.d(TAG, "Value is: " + presence);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return view;
    }
}

