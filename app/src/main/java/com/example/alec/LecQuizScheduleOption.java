package com.example.alec;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class LecQuizScheduleOption extends AppCompatActivity {

    String qID, qName, qDuHr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lec_quiz_schedule_option);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.3));

        Intent intent = getIntent();
        qID = intent.getStringExtra("qID");
        qName = intent.getStringExtra("qName");
        qDuHr = intent.getStringExtra("qDuHr");
    }

    public void ScheduleLater(View view){
        String quiz_id = qID;
        String type= "Create";

        BackgroundWorkerQuiz backgroundWorkerQuiz = new BackgroundWorkerQuiz(this);
        String result;
        try {
            result = backgroundWorkerQuiz.execute(type, quiz_id).get();

            if(result.equals("Success")){
                Intent LecAddQuizSelectOption = new Intent(this, LecAddQuizSelectOption.class);
                //LecQuizScheduleOption.putExtra("qID",qID);
                LecAddQuizSelectOption.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LecAddQuizSelectOption);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }


    public void ScheduleNow(View view){
        finish();
        Intent LecScheduleQuiz = new Intent(this,LecScheduleQuiz.class);
        LecScheduleQuiz.putExtra("qID",qID);
        LecScheduleQuiz.putExtra("qName",qName);
        LecScheduleQuiz.putExtra("qDuHr",qDuHr);
        LecScheduleQuiz.putExtra("Val","Now");
        startActivity(LecScheduleQuiz);
    }

}