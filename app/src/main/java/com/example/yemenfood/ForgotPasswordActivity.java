package com.example.yemenfood;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        TextView password = findViewById(R.id.boxPassword);
        TextView user = findViewById(R.id.user);
        user.setText("Username or Email : "+getIntent().getStringExtra("user"));
        password.setText("Password : "+getIntent().getStringExtra("password"));
    }
}