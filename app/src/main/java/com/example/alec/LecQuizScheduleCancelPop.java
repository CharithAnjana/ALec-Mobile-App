package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class LecQuizScheduleCancelPop extends AppCompatActivity {

    String qID,qName,cID,cName,User_ID,tID,tName, publishedDate,closeDate,duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_schedule_cancel_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.3));

        Intent intent =getIntent();
        qID = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        tID = intent.getStringExtra("tID");
        tName = intent.getStringExtra("tName");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
        User_ID = intent.getStringExtra("UserID");
        publishedDate = "null";
        closeDate = "null";
        duration = intent.getStringExtra("qDuHr");

    }

    public void Cancel(View view){
        finish();
    }

    public void DeleteConfirm(View view){
        String type= "Schedule";
        String St = "0";    //1 for Schedule and 0 for cancel
        BackgroundWorkerQuiz backgroundWorkerQuiz = new BackgroundWorkerQuiz(this);
        String result;
        try {
            result = backgroundWorkerQuiz.execute(type, qID, publishedDate, closeDate, duration, St).get();

            if(result.equals("Success")){
                Intent LecViewQuizDetails = new Intent(getApplicationContext(), LecViewQuizDetails.class);
                LecViewQuizDetails.putExtra("qID",qID);
                LecViewQuizDetails.putExtra("qName",qName);
                LecViewQuizDetails.putExtra("tID",tID);
                LecViewQuizDetails.putExtra("tName",tName);
                LecViewQuizDetails.putExtra("cID",cID);
                LecViewQuizDetails.putExtra("cName",cName);
                LecViewQuizDetails.putExtra("UserID",User_ID);
                LecViewQuizDetails.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecViewQuizDetails);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}