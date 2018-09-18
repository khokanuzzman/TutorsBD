package com.example.khokan.tutorisbdservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private Button reg_create_account;
    private TextInputLayout reg_display_name,reg_email,reg_password;
    private FirebaseAuth mAuth;
    private Toolbar reg_app_bar;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reg_create_account = findViewById(R.id.reg_create_btn);
        reg_display_name = findViewById(R.id.regi_display_name);
        reg_email = findViewById(R.id.regi_email);
        reg_password = findViewById(R.id.regi_password);
        reg_app_bar = findViewById(R.id.reg_abb_bar);

        progressDialog = new ProgressDialog(this);

        setSupportActionBar(reg_app_bar);
        getSupportActionBar().setTitle("Create A New Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        reg_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display_name = reg_display_name.getEditText().getText().toString();
                String email = reg_email.getEditText().getText().toString();
                String password = reg_password.getEditText().getText().toString();

                if (!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

                    progressDialog.setTitle("Registering User!");
                    progressDialog.setMessage("Please wait while we create your account!");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    registation_user(display_name, email, password);
                }
            }
        });
    }

    private void registation_user(final String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name",display_name);
                    userMap.put("status","Hi! i am using TutorsBDService");
                    userMap.put("image","default");
                    userMap.put("thumb_image","default");

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Intent mainIntent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                Toast.makeText(RegistrationActivity.this, "Register Successfull!", Toast.LENGTH_SHORT).show();
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                            }else
                            {
                                Toast.makeText(RegistrationActivity.this, "check internet connection" +
                                        "password required minimum 6 character! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }else
                    {
                        progressDialog.hide();
                        Toast.makeText(RegistrationActivity.this, "error ....", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }


}
