package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LecQuizQuestionEditMcq extends AppCompatActivity {

    String quizID,quizName,qtID, question, courseName,qDuHr,chName1,chPoint1;
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
        quizID = intent.getStringExtra("quizID");
        courseName = intent.getStringExtra("courseName");
        qDuHr = intent.getStringExtra("qDuHr");
        chName1 = intent.getStringExtra("chName1");
        chPoint1 = intent.getStringExtra("chPoint1");

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

    public void DeleteMCQ(View view){
        Intent LecQuizQuestionDeletePop = new Intent(this, LecQuizQuestionDeletePop.class);
        LecQuizQuestionDeletePop.putExtra("qtID",qtID);
        LecQuizQuestionDeletePop.putExtra("quizID",quizID);
        LecQuizQuestionDeletePop.putExtra("courseName",courseName);
        LecQuizQuestionDeletePop.putExtra("quizName",quizName);
        LecQuizQuestionDeletePop.putExtra("qDuHr",qDuHr);
        startActivity(LecQuizQuestionDeletePop);
        //finish();
    }
}