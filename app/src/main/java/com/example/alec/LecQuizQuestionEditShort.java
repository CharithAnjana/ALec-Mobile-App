package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LecQuizQuestionEditShort extends AppCompatActivity {

    String qtID, question, quizName;
    EditText quest;
    TextView quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_question_edit_short);

        Intent intent = getIntent();
        qtID = intent.getStringExtra("qtID");
        question = intent.getStringExtra("question");
        quizName = intent.getStringExtra("quizName");

        quiz = findViewById(R.id.QuizName);
        quiz.setText(quizName);
        quest = findViewById(R.id.editTextTextMultiLine);
        quest.setText(question);
    }

    public void Back(View view){
        finish();
    }

    public void Cancel(View view){
       finish();
    }

    public void Save(View view){

    }

    public void DeleteShort(View view){
        Intent LecQuizQuestionDeletePop = new Intent(this, com.example.alec.LecQuizQuestionDeletePop.class);

        finish();
    }
}