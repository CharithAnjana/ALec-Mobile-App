package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StuViewAttemPollsQuesOpen extends AppCompatActivity {

    String qNo,question,qAns;
    TextView ques, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_view_attem_polls_ques_open);

        Intent intent = getIntent();
        qNo = intent.getStringExtra("qNo");
        question = intent.getStringExtra("question");
        qAns = intent.getStringExtra("qAns");

        ques = findViewById(R.id.textViewq);
        answer = findViewById(R.id.openAnswer);
        ques.setText(question);
        answer.setText(qAns);
    }

    public void Back(View view){
        finish();
    }
}