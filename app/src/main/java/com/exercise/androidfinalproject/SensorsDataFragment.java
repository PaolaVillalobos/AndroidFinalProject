package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class SensorsDataFragment extends Fragment {

    private TextView tvTemperatureValue, tvHumidityValue, tvPresenceValue;
    private SeekBar sbLightValue, sbTapValue;
    private NotificationHelper notificationHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sensorsdata_layout, container, false);

        notificationHelper = new NotificationHelper();
        notificationHelper.createNotificationChannel(getContext());

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
                if(presence){
                    showNotification();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return view;
    }



    public class NotificationHelper {

        private static final String CHANNEL_ID = "my_channel_id";

        public void createNotificationChannel(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "My Channel";
                String description = "Notification Channel";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showNotification() {
        String title = "Fall Detected";
        String message = "A fall was detected";

        // Obtener el diseño de la notificación
        RemoteViews notificationLayout = new RemoteViews(getContext().getPackageName(), R.layout.notification_layout);
        notificationLayout.setTextViewText(R.id.notification_title, title);
        notificationLayout.setTextViewText(R.id.notification_message, message);

        // Configurar la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NotificationHelper.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sensor)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(1, builder.build());
    }



}

