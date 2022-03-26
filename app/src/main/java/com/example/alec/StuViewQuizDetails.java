package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StuViewQuizDetails extends AppCompatActivity {

    String qID,qName,mks,cName,dur,qAtm;
    TextView quizName, courseName, attemptDate, marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_view_quiz_details);

        Intent intent =getIntent();
        qID = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        dur = intent.getStringExtra("dur");
        qAtm = intent.getStringExtra("qAtm");
        mks = intent.getStringExtra("mks");
        cName = intent.getStringExtra("cName");


        quizName = findViewById(R.id.quizName);
        courseName = findViewById(R.id.courseName);
        attemptDate = findViewById(R.id.createDate);
        marks = findViewById(R.id.noQuestions);

        quizName.setText(qName);
        courseName.setText(cName);
        attemptDate.setText(qAtm);
        marks.setText(mks+"%");
    }

    public void Back(View view){
        finish();
    }

    public void PrewQuiz(View view){
        Intent StuPrewQuiz = new Intent(getApplicationContext(), StuPrewQuiz.class);
        StuPrewQuiz.putExtra("qID",qID);
        StuPrewQuiz.putExtra("qName",qName);
        startActivity(StuPrewQuiz);
    }
}