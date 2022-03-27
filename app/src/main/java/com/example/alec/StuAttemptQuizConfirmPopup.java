package com.example.alec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StuAttemptQuizConfirmPopup extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private CountDownTimer countDownTimer;
    private long timeLeft, initTime;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    String qId, qDur;
    Long ih, im, is, lh, lm, ls, h, m, s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_attempt_quiz_confirm_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.4));

        Intent intent = getIntent();
        qId = intent.getStringExtra("qID");
        qDur = intent.getStringExtra("qDur");


        //---To Calculate Time Left-----
        String[] TimeDur = qDur.split(":");
        h = Long.parseLong(TimeDur[0]);
        m = Long.parseLong(TimeDur[1]);
        s = Long.parseLong(TimeDur[2]);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date today = new Date();
        Date last = new Date();
        String Now = timeFormat.format(today);
        try {
            today = timeFormat.parse(Now);
            last = timeFormat.parse(lh + ":" + lm + ":" + ls);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] NowTime = Now.split(":");
        ih = Long.parseLong(NowTime[0]);
        im = Long.parseLong(NowTime[1]);
        is = Long.parseLong(NowTime[2]);

        initTime = ((lh - ih) * 3600 + (lm - im) * 60 + (ls - is)) * 1000;
        timeLeft = initTime;

        //--------------------------------------


    }

    public void No(View view){
        finish();
    }

    public void AtmpConfirm(View view){
        String topic_ID = "tID";
        String type = "DeleteTopic";

        BackgroundWorkerCourseTopic backgroundWorkerCourseTopic = new BackgroundWorkerCourseTopic(this);
        String result;
        try {
            result = backgroundWorkerCourseTopic.execute(type, topic_ID).get();

            if(result.equals("Success")){
                Intent StuQuizAttemptResult = new Intent(this,StuQuizAttemptResult.class);
                StuQuizAttemptResult.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(StuQuizAttemptResult);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}