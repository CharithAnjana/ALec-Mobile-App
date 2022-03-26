package com.example.alec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class StuHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button BtnAtmptQu,BtnMyCour,BtnMyAct,BtnForum,BtnSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stu_home);

        drawerLayout = findViewById(R.id.drawer_layout_stu);
        navigationView = findViewById(R.id.nav_bar_stu);
        toolbar = findViewById(R.id.toolbar_stu);
        BtnMyCour = findViewById(R.id.BtnMyCour);

        BtnAtmptQu = findViewById(R.id.BtnAtmptQu);
        BtnMyAct = findViewById(R.id.BtnMyAct);
        BtnForum = findViewById(R.id.BtnForum);
        BtnSession = findViewById(R.id.BtnSess);

        BtnAtmptQu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StuActiveQuiz = new Intent(StuHomeActivity.this, StuActiveQuiz.class);
                startActivity(StuActiveQuiz);
            }
        });

        //BtnForum = findViewById(R.id.BtnForum);
        BtnMyCour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StuMyCourses = new Intent(StuHomeActivity.this, StuMyCourses.class);
                startActivity(StuMyCourses);
            }
        });
        BtnForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StuForumCourseSelect = new Intent(StuHomeActivity.this,StuForumCourseSelect.class);
                startActivity(StuForumCourseSelect);
            }
        });
        BtnMyAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        BtnSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent StuSessionSelect = new Intent(StuHomeActivity.this,StuSessionSelect.class);
                startActivity(StuSessionSelect);
            }
        });

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_dash);
        startBackGroundProcessNotification();
    }

    @SuppressLint("ShortAlarm") //ignore the warning
    public void startBackGroundProcessNotification() {
        Intent notification = new Intent(this, BackgroundNotification.class);
        notification.setAction("BackgroundProcess");

        //Set Repeated task
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,notification,0);
        AlarmManager alm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alm.setRepeating(AlarmManager.RTC_WAKEUP,0,3000,pendingIntent);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_dash:
                break;
            case R.id.nav_sess:
                Intent StuSessionSelect = new Intent(StuHomeActivity.this,StuSessionSelect.class);
                startActivity(StuSessionSelect);
                break;
            case R.id.nav_Atquiz:
                Intent StuActiveQuiz = new Intent(getApplicationContext(), StuActiveQuiz.class);
                startActivity(StuActiveQuiz);
                break;
            case R.id.nav_course:
                Intent StuMyCourses = new Intent(StuHomeActivity.this, StuMyCourses.class);
                startActivity(StuMyCourses);
                break;
            case R.id.nav_lgout:
                SessionManagement sessionManagement = new SessionManagement(StuHomeActivity.this);
                sessionManagement.removeSession();

                Intent logout = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(logout);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}