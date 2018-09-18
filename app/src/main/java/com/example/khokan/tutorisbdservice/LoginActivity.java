package com.example.khokan.tutorisbdservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button login_user_btn;
    private TextInputLayout login_email,login_password;
    private ProgressDialog login_proggress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_user_btn = findViewById(R.id.login_btn);
        login_proggress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        login_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginEmail = login_email.getEditText().getText().toString();
                String loginPassword = login_password.getEditText().getText().toString();

                if (!TextUtils.isEmpty(loginEmail) || TextUtils.isEmpty(loginPassword))
                {
                    login_proggress.setTitle("Loggin In");
                    login_proggress.setMessage("Please wait while we check your credentials.");
                    login_proggress.setCanceledOnTouchOutside(false);
                    login_proggress.show();
                    loginUser(loginEmail,loginPassword);
                }
            }
        });
    }

    private void loginUser(String loginEmail, String loginPassword) {
        mAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    login_proggress.dismiss();
                    sendToMain();
                }else
                    {
                        login_proggress.hide();
                        Toast.makeText(LoginActivity.this, "Something wrong....", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
