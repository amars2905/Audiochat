package com.flower.myaudiomessanger.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flower.myaudiomessanger.R;
import com.flower.myaudiomessanger.utility.AppPreference;
import com.flower.myaudiomessanger.utility.Constant;

public class SplashActivity extends AppCompatActivity {
    Button login, register;
    TextView chat_title_tv;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        chat_title_tv = findViewById(R.id.chat_title_tv);

        Log.e("Value",AppPreference.getStringPreference(SplashActivity.this, Constant.userId));
        if (!AppPreference.getStringPreference(SplashActivity.this, Constant.userId).isEmpty()){
            startActivity(new Intent(SplashActivity.this, ChatActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
            }
        });

    }
}