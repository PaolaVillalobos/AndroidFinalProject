package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.exercise.androidfinalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ChangeDataFragment extends Fragment {

    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.changedata_layout, container, false);

        EditText lName = view.findViewById(R.id.etName);
        EditText lEmail = view.findViewById(R.id.etEmail);
        EditText lPassword = view.findViewById(R.id.etPassword);
        EditText lPassword2 = view.findViewById(R.id.etPassword2);
        TextView tvError = view.findViewById(R.id.tvError);
        Button btChangeData = view.findViewById(R.id.btChangeData);

        tvError.setText("");
        btChangeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvError.setText("");
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
                                        lName.setText("");
                                    } else {
                                        tvError.setText("Error Changing Name");
                                    }
                                }
                            });
                } else {
                    lName.setText("");
                }
                if (!lEmail.getText().toString().trim().isEmpty()) {
                    user.updateEmail(lEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Username updated successfully
                                        tvError.setText("Email Changed");
                                        lEmail.setText("");
                                    } else {
                                        // Failed to update username
                                        tvError.setText("Error Changing Email");
                                    }
                                }
                            });
                } else{
                    lEmail.setText("");
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
                                            lPassword.setText("");
                                            lPassword2.setText("");
                                        } else {
                                            // Failed to update password
                                            tvError.setText("Error Changing Password");
                                        }
                                    }
                                });
                    } else {
                        tvError.setText("Password Mismatch");
                        lPassword.setText("");
                        lPassword2.setText("");
                    }
                }
            }
        });

        return view;
    }


}
