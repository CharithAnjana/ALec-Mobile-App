package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StuSessionPollsShortResults extends AppCompatActivity {

    String qNo,question,userID;
    TextView Question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_session_polls_short_results);

        Intent intent = getIntent();
        qNo = intent.getStringExtra("qNo");
        question = intent.getStringExtra("question");
        userID = intent.getStringExtra("userID");

        Question = findViewById(R.id.shortQuestion);
        Question.setText(question);
    }

    public void Back(View view){
        finish();
    }

    public void Refresh(View view){
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent polls = new Intent(this,StuSessionPollsShortResults.class);
        polls.putExtra("qNo",qNo);
        polls.putExtra("question",question);
        polls.putExtra("userID",userID);
        startActivity(polls);
        overridePendingTransition(0, 0);
        finish();
    }
}