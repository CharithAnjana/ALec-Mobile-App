package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StuSessionPollsMCQResults extends AppCompatActivity {

    String qNo,question,userID;
    TextView Question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_session_polls_mcqresults);

        Intent intent = getIntent();
        qNo = intent.getStringExtra("qNo");
        question = intent.getStringExtra("question");
        userID = intent.getStringExtra("userID");

        Question = findViewById(R.id.mcqQuestion);
        Question.setText(question);
    }

    public void Back(View view){
        finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent polls = new Intent(this,StuSessionPollsMCQResults.class);
        polls.putExtra("qNo",qNo);
        polls.putExtra("question",question);
        polls.putExtra("userID",userID);
        startActivity(polls);
        overridePendingTransition(0, 0);
        finish();
    }
}