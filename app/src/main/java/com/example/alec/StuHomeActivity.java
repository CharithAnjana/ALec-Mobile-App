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

public class StuHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button BtnAtmptQu,BtnMyCour,BtnMyAct,BtnForum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stu_home);

        drawerLayout = findViewById(R.id.drawer_layout_stu);
        navigationView = findViewById(R.id.nav_bar_stu);
        toolbar = findViewById(R.id.toolbar_stu);
        BtnMyCour = findViewById(R.id.BtnMyCour);
        BtnForum = findViewById(R.id.StuBtnForum1);

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
            case R.id.nav_course:
                Intent StuMyCourses = new Intent(getApplicationContext(), StuMyCourses.class);
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