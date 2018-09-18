package com.example.khokan.tutorisbdservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button registar_btn,already_account_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        registar_btn = findViewById(R.id.need_account_btn);
        already_account_btn = findViewById(R.id.already_account_btn);

        already_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        sendToLogin();
            }
        });
        registar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendToRegister();

            }
        });
    }

    private void sendToRegister() {
        Intent loginIntent = new Intent(StartActivity.this, RegistrationActivity.class);
        startActivity(loginIntent);
    }


    private void sendToLogin() {
        Intent regi_intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(regi_intent);
    }


}
