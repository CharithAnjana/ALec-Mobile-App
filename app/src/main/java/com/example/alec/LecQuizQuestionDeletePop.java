package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class LecQuizQuestionDeletePop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_question_delete_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.3));
    }

    public void Cancel(View view){
        finish();
    }

    public void DeleteConfirm(View view){
        //String quiz_ID = qID;
        //String type = "DeleteQuiz";

        //BackgroundWorkerQuiz backgroundWorkerQuiz = new BackgroundWorkerQuiz(this);
        //String result;
        //try {
            //result = backgroundWorkerQuiz.execute(type, quiz_ID).get();

            //if(result.equals("Success")){
                //Intent LecQuizList = new Intent(this,LecQuizList.class);
                //LecQuizList.putExtra("tID",tID);
                //LecQuizList.putExtra("tName",tName);
                //LecQuizList.putExtra("cID",cID);
                //LecQuizList.putExtra("cName",cName);
                //LecQuizList.putExtra("UserID",User_ID);
                //LecQuizList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(LecQuizList);
                //finish();
            //}
        //} catch (Exception e) {
            //e.printStackTrace();
        //}
    }
}