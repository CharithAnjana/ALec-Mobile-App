package com.example.alec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
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
    Button BtnAskQu,BtnMyCour,BtnReview,BtnForum,BtnSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lec_home);

        drawerLayout = findViewById(R.id.drawer_layout_lec);
        navigationView = findViewById(R.id.nav_bar_lec);
        toolbar = findViewById(R.id.toolbar_lec);
        BtnMyCour = findViewById(R.id.BtnMyCour);
        BtnAskQu = findViewById(R.id.BtnAskQu);
        BtnForum = findViewById(R.id.BtnForum);
        BtnSession = findViewById(R.id.BtnSession);
        BtnReview = findViewById(R.id.BtnReview);

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

                //Intent LecMyCourses = new Intent(LecHomeActivity.this, LecMyCourses.class);
                //startActivity(LecMyCourses);
            }
        });

        BtnSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent LecMyCourses = new Intent(LecHomeActivity.this, LecMyCourses.class);
                //startActivity(LecMyCourses);
            }
        });

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_dash);
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
                break;
            case R.id.nav_ques:
                Intent LecAddQuizSelectOption = new Intent(LecHomeActivity.this, LecAddQuizSelectOption.class);
                startActivity(LecAddQuizSelectOption);
                break;
            case R.id.nav_course:
                Intent LecMyCourses = new Intent(LecHomeActivity.this, LecMyCourses.class);
                startActivity(LecMyCourses);
                break;
            case R.id.nav_review:
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

            case R.id.nav_Profilel:
                Intent UserDeatails = new Intent(getApplicationContext(), UserDeatails.class);
                startActivity(UserDeatails);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}