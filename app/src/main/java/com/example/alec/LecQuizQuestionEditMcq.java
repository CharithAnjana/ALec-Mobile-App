package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LecQuizQuestionEditMcq extends AppCompatActivity {

    String qtID, question, qtType, quizName;
    TextView quiz;
    EditText ques, ans1, ans2, ans3, ans4, ans5;
    CheckBox type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_question_edit_mcq);

        Intent intent = getIntent();
        qtID = intent.getStringExtra("qtID");
        question = intent.getStringExtra("question");
        quizName = intent.getStringExtra("quizName");

        quiz = findViewById(R.id.QuizName);
        quiz.setText(quizName);
        ques = findViewById(R.id.editTextTextMultiLine);
        ques.setText(question);
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