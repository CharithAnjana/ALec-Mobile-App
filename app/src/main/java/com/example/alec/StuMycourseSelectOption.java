package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StuMycourseSelectOption extends AppCompatActivity {

    String cID,cName,User_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_mycourse_select_option);

        Intent intent =getIntent();
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");
    }

    public void Back(View view){
        finish();
    }

    public void Quiz(View view){
        Intent StuCourseTopics = new Intent(this, StuCourseTopics.class);
        StuCourseTopics.putExtra("cID",cID);
        StuCourseTopics.putExtra("cName",cName);
        StuCourseTopics.putExtra("UserID",User_ID);
        startActivity(StuCourseTopics);
    }

    public void Poll(View view){
        Intent StuViewAttemPolls = new Intent(this, StuViewAttemPolls.class);
        StuViewAttemPolls.putExtra("cID",cID);
        StuViewAttemPolls.putExtra("cName",cName);
        StuViewAttemPolls.putExtra("UserID",User_ID);
        startActivity(StuViewAttemPolls);

    }
}