package com.example.khokan.tutorisbdservice;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {
    private Toolbar status_appBar;
    private TextInputLayout mStatus;
    private Button mSavebtn;
//    Firebase
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;

//    Progress Dialouge
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        status_appBar = findViewById(R.id.status_appBar);
        setSupportActionBar(status_appBar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);


        mStatus = findViewById(R.id.status_input);
        mSavebtn = findViewById(R.id.save_changes_btn);

        String status_value = getIntent().getStringExtra("status_value");
        mStatus.getEditText().setText(status_value);

        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progress dialouge
                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please Wait While we saving changes...");
                mProgress.show();

                String status = mStatus.getEditText().getText().toString();
                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }else
                        {
                            Toast.makeText(StatusActivity.this, "Something error to save changes!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}
