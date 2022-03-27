package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StuForumReplyView extends AppCompatActivity {

    String fname,reply,uName,date,ques;
    TextView FTopic, textViewDate, textViewUserName, textViewQuestion, textViewReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_forum_reply_view);

        Intent intent =getIntent();
        fname = intent.getStringExtra("fName");
        reply = intent.getStringExtra("reply");
        uName = intent.getStringExtra("uName");
        date = intent.getStringExtra("date");
        ques = intent.getStringExtra("ques");

        FTopic = findViewById(R.id.ForumTopic);
        textViewDate = findViewById(R.id.date);
        textViewUserName = findViewById(R.id.userName);
        textViewQuestion = findViewById(R.id.ForumQuestion);
        textViewReply = findViewById(R.id.ForumQuestionReply);

        FTopic.setText(fname);
        textViewDate.setText(date);
        textViewUserName.setText(uName);
        textViewQuestion.setText(ques);
        textViewReply.setText(reply);
    }

    public void Back(View view){
        finish();
    }
}