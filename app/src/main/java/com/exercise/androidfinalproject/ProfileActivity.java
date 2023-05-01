package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;

import static com.exercise.androidfinalproject.LoginActivity.isEmailValid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        TextView lName = findViewById(R.id.etName);
        Button lChangeData = findViewById(R.id.btChangeData);
        Button lLogOut = findViewById(R.id.btLogOut);
        Button lViewData = findViewById(R.id.btSensors);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            lName.setText(name);

        }

        lChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ChangeDataActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, SensorsDataActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}