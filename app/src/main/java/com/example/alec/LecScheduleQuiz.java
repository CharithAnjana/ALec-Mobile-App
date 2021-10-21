package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LecScheduleQuiz extends AppCompatActivity {

    String qID, qName, qDuHr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_schedule_quiz);

        Intent intent = getIntent();
        qID = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        qDuHr = intent.getStringExtra("qDuHr");


    }

    public void Back(View view){
        finish();
    }
}