package com.example.khokan.tutorisbdservice;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Toolbar main_app_bar;
    private ViewPager mViewPager;
    private SectionPagerAdapter mSectionPagerAdapter;

    private TabLayout main_tab_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        mViewPager = findViewById(R.id.main_tab_Pager);
        main_app_bar = findViewById(R.id.main_app_bar);
        setSupportActionBar(main_app_bar);
        getSupportActionBar().setTitle("TutorsBDService");

//        tabs

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionPagerAdapter);
        main_tab_layout = findViewById(R.id.main_tabs);
        main_tab_layout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            sendToStart();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout)
        {
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        if (item.getItemId()==R.id.account_setting)
        {
            Intent mainSettingIntent = new Intent(MainActivity.this, AccountSetting.class);
            startActivity(mainSettingIntent);
        }

        if (item.getItemId()== R.id.all_user)
        {
            Intent mainSettingIntent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(mainSettingIntent);

        }
        return true;
    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();
    }
}
