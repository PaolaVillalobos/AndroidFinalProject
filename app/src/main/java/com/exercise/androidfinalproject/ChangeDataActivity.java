package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ChangeDataActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changedata_layout);

        EditText lName = findViewById(R.id.etName);
        EditText lEmail = findViewById(R.id.etEmail);
        EditText lPassword = findViewById(R.id.etPassword);
        EditText lPassword2 = findViewById(R.id.etPassword2);
        TextView tvError = findViewById(R.id.tvError);
        Button btChangeData = findViewById(R.id.btChangeData);

        btChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = auth.getCurrentUser();
                // Update the user's display name in Firebase
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()


                        .setDisplayName(lName.getText().toString().trim())
                        .build();
                if (!lName.getText().toString().trim().isEmpty()) {
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                        tvError.setText("Name Changed");
                                    } else {
                                        tvError.setText("Error Changing Name");
                                    }
                                }
                            });
                }
                if (!lEmail.getText().toString().trim().isEmpty()) {
                    user.updateEmail(lEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Username updated successfully
                                        tvError.setText("Email Changed");
                                    } else {
                                        // Failed to update username
                                        tvError.setText("Error Changing Email");
                                    }
                                }
                            });
                }
                if (!lPassword.getText().toString().trim().isEmpty()) {
                    if (lPassword.getText().toString().equals(lPassword2.getText().toString())) {
                        user.updatePassword(lPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Password updated successfully
                                            tvError.setText("Password Changed");
                                        } else {
                                            // Failed to update password
                                            tvError.setText("Error Changing Password");
                                        }
                                    }
                                });
                    } else {
                        tvError.setText("Password Mismatch");
                    }
                }
            }
        });

    }
}
