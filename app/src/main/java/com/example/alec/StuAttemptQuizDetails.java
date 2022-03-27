package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StuAttemptQuizDetails extends AppCompatActivity {

    String qId,qName,qPubTime,qClsTime,qDur,userID;
    TextView quizName, courseName, publishedDate, closeDate, duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_attempt_quiz_details);

        Intent intent = getIntent();
        qId = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        qPubTime = intent.getStringExtra("pubDate");
        qClsTime = intent.getStringExtra("clsDate");
        qDur = intent.getStringExtra("dur");
        userID = intent.getStringExtra("userID");

        quizName = findViewById(R.id.quizName);
        courseName = findViewById(R.id.courseName);
        publishedDate = findViewById(R.id.publishDate);
        closeDate = findViewById(R.id.closeDate);
        duration = findViewById(R.id.duration);

        quizName.setText(qName);
        courseName.setText("Test DB");
        publishedDate.setText(qPubTime);
        closeDate.setText(qClsTime);
        duration.setText(qDur);
    }

    public void Back(View view) {
        finish();
    }

    public void AttemptQuiz(View view) {
        finish();
    }
}