package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class LecQuizQuestionDeletePop extends AppCompatActivity {

    String qtID,quizName,quizID,courseName,qDuHr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_question_delete_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.3));

        Intent intent = getIntent();
        qtID = intent.getStringExtra("qtID");
        quizName = intent.getStringExtra("quizName");
        quizID = intent.getStringExtra("quizID");
        courseName = intent.getStringExtra("courseName");
        qDuHr = intent.getStringExtra("qDuHr");
    }

    public void Cancel(View view){
        finish();
    }

    public void DeleteConfirm(View view){
        String ques_ID = qtID;
        String type = "DeleteQuestion";

        BackgroundWorkerQuiz backgroundWorkerQuiz = new BackgroundWorkerQuiz(this);
        String result;
        try {
            result = backgroundWorkerQuiz.execute(type, ques_ID).get();

            if(result.equals("Success")){
                Intent LecAddQuizQuestion = new Intent(this,LecAddQuizQuestion.class);
                LecAddQuizQuestion.putExtra("qID",quizID);
                LecAddQuizQuestion.putExtra("cName",courseName);
                LecAddQuizQuestion.putExtra("qName",quizName);
                LecAddQuizQuestion.putExtra("qDuration",qDuHr);
                LecAddQuizQuestion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecAddQuizQuestion);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}