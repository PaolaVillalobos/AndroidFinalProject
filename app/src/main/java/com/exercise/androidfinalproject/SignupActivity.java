package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;
import static com.exercise.androidfinalproject.LoginActivity.isEmailValid;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        EditText lName = findViewById(R.id.etName);
        EditText lEmail = findViewById(R.id.etEmail);
        EditText lPassword = findViewById(R.id.etPassword);
        EditText lPassword2 = findViewById(R.id.etPassword2);
        TextView lError = findViewById(R.id.tvError);
        Button lSignUp = findViewById(R.id.btSignUp);

        lSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = lEmail.getText().toString().trim();
                if(isEmailValid(inputEmail)){
                    if (lPassword.getText().toString().length() >= 6) {
                        if (lPassword.getText().toString().equals(lPassword2.getText().toString())) {
                            String name = lName.getText().toString().trim();
                            String email = lEmail.getText().toString().trim();
                            String password = lPassword.getText().toString().trim();

                            createAccount(name, email, password);
                        } else {
                            lError.setText("Error: Passwords mismatch");
                        }
                    } else {
                        lError.setText("Error:Password must be at least 6 characters long.");
                    }
                }else{
                    lError.setText("Incorrect email address");
                }
            }
        });

    }

    private void createAccount(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                            // Update the user's display name in Firebase
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
        }
}
