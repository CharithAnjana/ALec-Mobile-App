package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class LecQuizScheduleOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_schedule_option);
    }

    public void ScheduleLater(View view){
        finish();
    }
    public void ScheduleNow(View view){

    }

}