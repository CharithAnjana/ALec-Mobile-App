package com.example.alec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class LecHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button BtnAskQu,BtnMyCour,BtnForum,BtnSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lec_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch0 = new NotificationChannel("0", "Notification0",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel ch1 = new NotificationChannel("1", "Notification1",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel ch2 = new NotificationChannel("2", "Notification2",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(ch0);
            manager.createNotificationChannel(ch1);
            manager.createNotificationChannel(ch2);
        }

        drawerLayout = findViewById(R.id.drawer_layout_lec);
        navigationView = findViewById(R.id.nav_bar_lec);
        toolbar = findViewById(R.id.toolbar_lec);
        BtnMyCour = findViewById(R.id.BtnMyCour);
        BtnAskQu = findViewById(R.id.BtnAskQu);
        BtnForum = findViewById(R.id.BtnForum);
        BtnSession = findViewById(R.id.BtnSession);


        BtnMyCour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent LecMyCourses = new Intent(LecHomeActivity.this, LecMyCourses.class);
                startActivity(LecMyCourses);
            }
        });

        BtnAskQu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent LecAddQuizSelectOption = new Intent(LecHomeActivity.this, LecAddQuizSelectOption.class);
                startActivity(LecAddQuizSelectOption);
            }
        });

        BtnForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent LecForumCourseSelect = new Intent(LecHomeActivity.this, LecForumCourseSelect.class);
                startActivity(LecForumCourseSelect);
            }
        });

        BtnSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent LecSessions = new Intent(LecHomeActivity.this, LecSessions.class);
                startActivity(LecSessions);
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

    //@SuppressLint("ShortAlarm") //ignore the warning
    public void startBackGroundProcessNotification() {
        Intent intent = new Intent(this, BackgroundNotification.class);
        intent.setAction("BackgroundProcess");

        //Set Repeated task
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        AlarmManager alm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alm.setRepeating(AlarmManager.RTC_WAKEUP,0,60000,pendingIntent);

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
                Intent LecSessions = new Intent(LecHomeActivity.this, LecSessions.class);
                startActivity(LecSessions);
                break;
            case R.id.nav_ques:
                Intent LecAddQuizSelectOption = new Intent(LecHomeActivity.this, LecAddQuizSelectOption.class);
                startActivity(LecAddQuizSelectOption);
                break;
            case R.id.nav_course:
                Intent LecMyCourses = new Intent(LecHomeActivity.this, LecMyCourses.class);
                startActivity(LecMyCourses);
                break;
            case R.id.nav_forum:
                Intent LecForumCourseSelect = new Intent(LecHomeActivity.this, LecForumCourseSelect.class);
                startActivity(LecForumCourseSelect);
                break;
            case R.id.nav_lgout:
                SessionManagement sessionManagement = new SessionManagement(LecHomeActivity.this);
                sessionManagement.removeSession();

                Intent Logout = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(Logout);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}