package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LecViewQuizDetails extends AppCompatActivity {

    String qID,qName,cID,cName,User_ID,tID,tName,qtCount;
    TextView quizName, courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_view_quiz_details);

        Intent intent =getIntent();
        qID = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        tID = intent.getStringExtra("tID");
        tName = intent.getStringExtra("tName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");

        quizName = findViewById(R.id.quizName);
        courseName = findViewById(R.id.courseName);

        quizName.setText(qName);
        courseName.setText(cName);


    }

    public void Back(View view){
        Intent LecQuizList = new Intent(this,LecQuizList.class);
        LecQuizList.putExtra("tID",tID);
        LecQuizList.putExtra("tName",tName);
        LecQuizList.putExtra("cID",cID);
        LecQuizList.putExtra("cName",cName);
        LecQuizList.putExtra("UserID",User_ID);
        LecQuizList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(LecQuizList);
    }

    public void PrewQuiz(View view){
        Intent LecViewQuiz = new Intent(getApplicationContext(), LecViewQuiz.class);
        LecViewQuiz.putExtra("qID",qID);
        LecViewQuiz.putExtra("qName",qName);
        LecViewQuiz.putExtra("tID",tID);
        LecViewQuiz.putExtra("tName",tName);
        LecViewQuiz.putExtra("cID",cID);
        LecViewQuiz.putExtra("cName",cName);
        LecViewQuiz.putExtra("UserID",User_ID);
        startActivity(LecViewQuiz);
    }

    public void ScheduleQuiz(View view){
        //Intent intent = new Intent(this,LecAddNewTopic.class);
        //intent.putExtra("cName",cName);
        //intent.putExtra("cID",cID);
        //intent.putExtra("User_ID",User_ID);
        //startActivity(intent);
        finish();
    }

}