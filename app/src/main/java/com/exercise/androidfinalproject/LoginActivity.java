package com.exercise.androidfinalproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        EditText lEmail = findViewById(R.id.etEmail);
        EditText lPassword = findViewById(R.id.etPassword);
        TextView lLogin = findViewById(R.id.tvLogin);
        TextView lFeedback = findViewById(R.id.tvFeedback);
        TextView lSignup = findViewById(R.id.tvSignup);

        auth = FirebaseAuth.getInstance();

        // check if email and password are saved in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");



        if (!email.isEmpty() && !password.isEmpty()) {
            signIn(email, password);
            return;
        }

        lLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = lEmail.getText().toString().trim();
                if(isEmailValid(inputText)){
                    if(lPassword.getText().toString().length() >= 6) {

                        String emailUser = lEmail.getText().toString().trim();
                        String passwordUser = lPassword.getText().toString().trim();

                        signIn( emailUser, passwordUser);

                    }else {
                        lFeedback.setText("Invalid Password. Try Again");
                        lFeedback.setTextColor(Color.RED);
                    }
                }
            }
        });


        lSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);
                    finish();
                }
        });

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void signIn(String email, String password) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            // save email and password in SharedPreferences
                            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                            //intent.putExtra("key_email", etEmail.getText().toString());
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

}