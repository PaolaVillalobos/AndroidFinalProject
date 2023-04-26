package com.exercise.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        EditText email = findViewById(R.id.etEmail);
        EditText password = findViewById(R.id.etPassword);
        TextView login = findViewById(R.id.tvLogin);
        TextView forgotPassword = findViewById(R.id.tvForgotPassword);
        TextView feedback = findViewById(R.id.tvFeedback);

        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES",MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = email.getText().toString().trim();
                if(isEmailValid(inputText)){
                    if(password.getText().toString().length() >= 6) {
                        if(password.getText().toString().equals(sharedPreferences.getString("password"+email.getText().toString(),"123456"))){
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.putExtra("key_email", email.getText().toString());
                            startActivity(intent);
                            finish();
                        }else{
                            feedback.setText("Invalid Password. Try Again");
                            feedback.setTextColor(Color.RED);
                        }
                    }else{
                        feedback.setText("Invalid Password. Try Again");
                        feedback.setTextColor(Color.RED);
                    }

                }else{
                    feedback.setText("Invalid Password. Try Again");
                    feedback.setTextColor(Color.RED);
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}