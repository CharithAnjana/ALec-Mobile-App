package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LecQuizQuestionEditShort extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_question_edit_short);
    }

    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
       finish();
    }

    public void Save(View view){

    }
}