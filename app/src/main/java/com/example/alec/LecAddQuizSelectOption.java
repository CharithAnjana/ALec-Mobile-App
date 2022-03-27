package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LecAddQuizSelectOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_add_quiz_select_option);
    }

    public void Back(View view){
        finish();
    }

    public void NewQuiz(View view){
        Intent LecAddQuizSeltCurs = new Intent(this, LecAddQuizNewSeltCurs.class);
        startActivity(LecAddQuizSeltCurs);
    }

    public void DraftQuiz(View view){
        Intent LecAddQuizSeltCurs = new Intent(this, LecAddQuizDraftSelectCurs.class);
        startActivity(LecAddQuizSeltCurs);

    }
}