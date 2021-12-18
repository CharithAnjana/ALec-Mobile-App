package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.VerifiedInputEvent;
import android.view.View;

public class LecQuizDeletePop2 extends AppCompatActivity{
    String qID,tID,tName,cID,cName,User_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_delete_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.3));

        Intent intent =getIntent();
        qID = intent.getStringExtra("qID");
        cID = intent.getStringExtra("cID");
        cName = intent.getStringExtra("cName");
    }

    public void Cancel(View view){
        finish();
    }

    public void DeleteConfirm(View view){
        String quiz_ID = qID;
        String type = "DeleteQuiz";

        BackgroundWorkerQuiz backgroundWorkerQuiz = new BackgroundWorkerQuiz(this);
        String result;
        try {
            result = backgroundWorkerQuiz.execute(type, quiz_ID).get();

            if(result.equals("Success")){
                Intent LecViewDraftQuiz = new Intent(this, LecViewDraftQuiz.class);
                LecViewDraftQuiz.putExtra("topicId", "");
                LecViewDraftQuiz.putExtra("courseId", cID);
                LecViewDraftQuiz.putExtra("course", cName);
                LecViewDraftQuiz.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecViewDraftQuiz);
                finish();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
