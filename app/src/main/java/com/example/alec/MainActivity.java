package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2700;

    Animation topAnim, botAnim;
    ImageView img;
    TextView txt, a, lec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        img = findViewById(R.id.imageView1);
        txt = findViewById(R.id.textView);
        a = findViewById(R.id.textViewA);
        lec = findViewById(R.id.textViewLec);

        img.setAnimation(topAnim);
        a.setAnimation(botAnim);
        lec.setAnimation(botAnim);
        txt.setAnimation(botAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                String us_type = sessionManagement.getSessionKey();
                if(us_type.equals("no")){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }

            }
        },SPLASH_SCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        String us_type = sessionManagement.getSessionKey();

        if(us_type.equals("stu")){
            Intent StuHomeActivity = new Intent(MainActivity.this, StuHomeActivity.class);
            StuHomeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(StuHomeActivity);
        }
        if (us_type.equals("lec")){
            Intent LecHomeActivity = new Intent(MainActivity.this, LecHomeActivity.class);
            LecHomeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(LecHomeActivity);
        }

    }
}